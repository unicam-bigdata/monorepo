package com.bigdata.backend.repositories;

import com.bigdata.backend.enums.ColumnDataType;
import com.bigdata.backend.enums.RelationshipDirection;
import com.bigdata.backend.models.Column;
import com.bigdata.backend.models.ImportConfig;
import com.bigdata.backend.models.Relationship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.neo4j.driver.Query;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ImportRepository {
    private final Neo4JDriver neo4JDriver;


    @Autowired
    public ImportRepository(Neo4JDriver neo4jDriver) {

        this.neo4JDriver = neo4jDriver;
    }

    private String generateItemProperty(Column column) {
        String property = column.getName() + ":";
        if (column.getDataType() == ColumnDataType.INTEGER) {
            property += "toInteger(row." + column.getName() + ")";
        } else if (column.getDataType() == ColumnDataType.FLOAT) {
            property += "toFloat(row." + column.getName() + ")";
        } else if (column.getDataType() == ColumnDataType.DATE) {
            property += "date(row." + column.getName() + ")";
        } else if (column.getDataType() == ColumnDataType.DATETIME) {
            property += "datetime(row." + column.getName() + ")";
        } else {
            property += "row." + column.getName();
        }

        return property;
    }

    private String generateItemProperty(Column column, String sameEntityId) {
        String property = sameEntityId + ":";
        if (column.getDataType() == ColumnDataType.INTEGER) {
            property += "toInteger(row." + column.getName() + ")";
        } else if (column.getDataType() == ColumnDataType.FLOAT) {
            property += "toFloat(row." + column.getName() + ")";
        } else if (column.getDataType() == ColumnDataType.DATE) {
            property += "date(row." + column.getName() + ")";
        } else if (column.getDataType() == ColumnDataType.DATETIME) {
            property += "datetime(row." + column.getName() + ")";
        } else {
            property += "row." + column.getName();
        }

        return property;
    }


    private Map<String, Object> generateImportQuery(List<ImportConfig> importConfigs) {
        Map<String, Object> map = new HashMap<>();
        boolean columnsToLoadExist = false;
        boolean relationshipsToLoadExist = false;

        StringBuilder constraintQueryBuilder = new StringBuilder();
        StringBuilder loadNodesQueryBuilder = new StringBuilder();
        StringBuilder loadRelationshipsQueryBuilder = new StringBuilder();

        loadNodesQueryBuilder.append("LOAD CSV WITH HEADERS FROM 'http://10.0.20.235:8080/cached-csv-file' AS row\n");
        loadNodesQueryBuilder.append("CALL {\n");
        loadNodesQueryBuilder.append("WITH row\n");

        loadRelationshipsQueryBuilder.append("LOAD CSV WITH HEADERS FROM 'http://10.0.20.235:8080/cached-csv-file' AS row\n");
        loadRelationshipsQueryBuilder.append("CALL {\n");
        loadRelationshipsQueryBuilder.append("WITH row\n");

        //Build the query
        for (ImportConfig importConfig : importConfigs) {
            //generate constraints query
            constraintQueryBuilder.append("CREATE CONSTRAINT ");

            String constraintName = importConfig.getKey().getName() + "Constraint IF NOT EXISTS ";
            constraintQueryBuilder.append(constraintName);

            String forEntityName = "FOR (node:" + importConfig.getName() + ") ";
            constraintQueryBuilder.append(forEntityName);

            String uniqueProperty = "node." + importConfig.getKey().getName();
            String requireUnique = "REQUIRE " + uniqueProperty + " IS UNIQUE\n";
            constraintQueryBuilder.append(requireUnique);

            //generate loadNodes query
            Column[] columns = importConfig.getColumns();
            if (columns != null && columns.length != 0) {
                columnsToLoadExist = true;
                String mergeCommandInitial = "MERGE (" + importConfig.getName().toLowerCase() + ":" + importConfig.getName() + " {";
                loadNodesQueryBuilder.append(mergeCommandInitial);
                for (int i = 0; i < columns.length; i++) {
                    String property = this.generateItemProperty(columns[i]);
                    if (i == columns.length - 1) {
                        property += "})\n";
                    } else {
                        property += ",";
                    }
                    loadNodesQueryBuilder.append(property);
                }
                loadNodesQueryBuilder.append("} IN TRANSACTIONS OF 10 ROWS\n");
            }

            //generate loadRelationships query
            Relationship[] relationships = importConfig.getRelationships();
            if (relationships != null && relationships.length != 0) {
                relationshipsToLoadExist = true;
                String primaryNodeVariable = importConfig.getName().toLowerCase();
                for (int i = 0; i < relationships.length; i++) {
                    String matchCommandInitial = "MATCH (" + primaryNodeVariable + ":" + importConfig.getName() + " {";
                    loadRelationshipsQueryBuilder.append(matchCommandInitial);
                    String primaryNodeProperty = this.generateItemProperty(importConfig.getKey());
                    loadRelationshipsQueryBuilder.append(primaryNodeProperty);
                    loadRelationshipsQueryBuilder.append("}), ");

                    boolean sameNodeLabel = importConfig.getName().equals(relationships[i].getEntityName());
                    String secondNodeVariable = sameNodeLabel ? importConfig.getName().toLowerCase()+"Second" : relationships[i].getEntityName().toLowerCase();
                    String secondNodeCommandInitial = "(" + secondNodeVariable + ":" + relationships[i].getEntityName() + " {";
                    loadRelationshipsQueryBuilder.append(secondNodeCommandInitial);
                    String secondNodeProperty = sameNodeLabel ? this.generateItemProperty(relationships[i].getForeignEntityKey(), importConfig.getKey().getName()) : this.generateItemProperty(relationships[i].getForeignEntityKey());
                    loadRelationshipsQueryBuilder.append(secondNodeProperty);
                    loadRelationshipsQueryBuilder.append("})\n");

                    String relationShipQuery = "MERGE (" + primaryNodeVariable + ")";

                    String relationDefinition = "[:" + relationships[i].getRelationShipLabel();
                    Column[] relationshipColumns = relationships[i].getRelationshipProperties();
                    for (int j = 0; j < relationshipColumns.length; j++) {
                        String property = "";
                        if (j == 0) {
                            property += "{";
                        }
                        property += this.generateItemProperty(relationshipColumns[j]);
                        if (j == relationshipColumns.length - 1) {
                            property += "}";
                        } else {
                            property += ",";
                        }
                        relationDefinition += property;
                    }
                    if (relationships[i].getDirection() == RelationshipDirection.TO) {
                        relationShipQuery += "-" + relationDefinition + "]->" + "(" + secondNodeVariable + ")\n";
                    } else if (relationships[i].getDirection() == RelationshipDirection.FROM) {
                        relationShipQuery += "<-" + relationDefinition + "]-" + "(" + secondNodeVariable + ")\n";
                    } else if (relationships[i].getDirection() == RelationshipDirection.BIDIRECTIONAL) {
                        relationShipQuery += "-" + relationDefinition + "]-" + "(" + secondNodeVariable + ")\n";
                    }
                    loadRelationshipsQueryBuilder.append(relationShipQuery);
                }
                loadRelationshipsQueryBuilder.append("} IN TRANSACTIONS OF 10 ROWS\n");
            }
        }

        if (!columnsToLoadExist) {
            loadNodesQueryBuilder.setLength(0);
        }

        if (!relationshipsToLoadExist) {
            loadRelationshipsQueryBuilder.setLength(0);
        }

        map.put("constraintQuery", constraintQueryBuilder.toString());
        map.put("loadNodesQuery", loadNodesQueryBuilder.toString());
        map.put("loadRelationshipsQuery", loadRelationshipsQueryBuilder.toString());
        return map;
    }

    public void loadData(List<ImportConfig> importConfigs) {

        try (var session = this.neo4JDriver.getDriver().session()) {
            Map<String, Object> importQueryStringMap = this.generateImportQuery(importConfigs);

            // Add the unique constraints for the primary keys
            var query = new Query(importQueryStringMap.get("constraintQuery").toString());
            session.run(query);
            System.out.println("Constraints for all nodes configured successfully.");

            //Load the data nodes after constraints have been defined
            if (!importQueryStringMap.get("loadNodesQuery").toString().isEmpty()) {
                query = new Query(importQueryStringMap.get("loadNodesQuery").toString());
                session.run(query);
                System.out.println("Data nodes loaded successfully successfully.");
            }

            //Load the data relationships after data nodes have been loaded
            if (!importQueryStringMap.get("loadRelationshipsQuery").toString().isEmpty()) {
                query = new Query(importQueryStringMap.get("loadRelationshipsQuery").toString());
                session.run(query);
                System.out.println("Data relationships loaded successfully successfully.");
            }

        } catch (Exception e) {
            // Exception handling code
            System.out.println("An exception occurred: " + e.getMessage());
        }
    }

    public void clearDatabase() {

        try (var session = this.neo4JDriver.getDriver().session()) {
            // Add the unique constraints for the primary keys
            var query = new Query("MATCH (n) DETACH DELETE n");
            session.run(query);
            System.out.println("Database cleared successfully.");


        } catch (Exception e) {
            // Exception handling code
            System.out.println("An exception occurred: " + e.getMessage());
        }
    }

}
