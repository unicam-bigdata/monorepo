package com.bigdata.backend.repositories;

import com.bigdata.backend.dto.NodesCountRequest;
import com.bigdata.backend.dto.NodesRequest;
import com.bigdata.backend.dto.RelatedNodesRequest;
import com.bigdata.backend.enums.ColumnDataType;
import com.bigdata.backend.enums.RelationshipDirection;
import com.bigdata.backend.models.Column;
import com.bigdata.backend.models.ImportConfig;
import com.bigdata.backend.models.Relationship;
import com.bigdata.backend.utils.Filter;
import com.bigdata.backend.utils.Pagination;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CypherQueryBuilder {
    public static String PUBLIC_BACKEND_DOMAIN;

    private static String generateItemProperty(Column column) {
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

    private static String generateItemProperty(Column column, String sameEntityId) {
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

    public static Map<String, Object> generateImportQuery(List<ImportConfig> importConfigs) {
        Map<String, Object> map = new HashMap<>();
        boolean columnsToLoadExist = false;
        boolean relationshipsToLoadExist = false;
        String loadCsvString = "LOAD CSV WITH HEADERS FROM '"+PUBLIC_BACKEND_DOMAIN+"cached-csv-file' AS row\n";

        StringBuilder constraintQueryBuilder = new StringBuilder();
        StringBuilder loadNodesQueryBuilder = new StringBuilder();
        StringBuilder loadRelationshipsQueryBuilder = new StringBuilder();

        loadNodesQueryBuilder.append(loadCsvString);
        loadNodesQueryBuilder.append("CALL {\n");
        loadNodesQueryBuilder.append("WITH row\n");

        loadRelationshipsQueryBuilder.append(loadCsvString);
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
                    String property = generateItemProperty(columns[i]);
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
                    String primaryNodeProperty = generateItemProperty(importConfig.getKey());
                    loadRelationshipsQueryBuilder.append(primaryNodeProperty);
                    loadRelationshipsQueryBuilder.append("}), ");

                    boolean sameNodeLabel = importConfig.getName().equals(relationships[i].getEntityName());
                    String secondNodeVariable = sameNodeLabel ? importConfig.getName().toLowerCase() + "Second" : relationships[i].getEntityName().toLowerCase();
                    String secondNodeCommandInitial = "(" + secondNodeVariable + ":" + relationships[i].getEntityName() + " {";
                    loadRelationshipsQueryBuilder.append(secondNodeCommandInitial);
                    String secondNodeProperty = sameNodeLabel ? generateItemProperty(relationships[i].getForeignEntityKey(), importConfig.getKey().getName()) : generateItemProperty(relationships[i].getForeignEntityKey());
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
                        property += generateItemProperty(relationshipColumns[j]);
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

    private static String generateWhereClause(String variable, Filter[][] filter) {
        StringBuilder condition = new StringBuilder();

        for (int i = 0; i < filter.length; i++) {
            for (int j = 0; j < filter[i].length; j++) {
                if (j == 0) {
                    condition.append("( ");
                }
                String filterValue = filter[i][j].getValue();
                ColumnDataType dataType = filter[i][j].getDataType();
                String leftHand = variable + "." + filter[i][j].getPropertyName() + " ";
                String rightHand = " ";
                condition.append(leftHand);
                condition.append(filter[i][j].getFilterOperator().getSymbol());

                if (dataType == ColumnDataType.INTEGER || dataType == ColumnDataType.FLOAT) {
                    rightHand += filterValue;
                } else if (dataType == ColumnDataType.DATE) {
                    rightHand += "date('" + filterValue + "')";
                } else if (dataType == ColumnDataType.DATETIME) {
                    rightHand += "datetime('" + filterValue + "')";

                } else {
                    rightHand += "'" + filterValue + "'";
                }

                condition.append(rightHand);

                if (j != 0 && j != filter.length - 1) {
                    condition.append(" AND ");
                } else if (j == filter.length - 1) {
                    condition.append(") ");
                }
            }

            if (i != 0 && i != filter.length - 1) {
                condition.append(" OR ");
            }
        }
        return condition.toString();
    }

    public static String generateNodesQuery(NodesRequest nodesRequest) {
        StringBuilder query = new StringBuilder("Match (n:");
        query.append(nodesRequest.getNodeName());
        query.append(") ");
        if (nodesRequest.getFilter() != null) {
            query.append("WHERE ");
            String condition = generateWhereClause("n", nodesRequest.getFilter());
            query.append(condition);
        }

        query.append("RETURN n");

        if (nodesRequest.getPagination() != null) {
            Pagination config = nodesRequest.getPagination();
            String pagination = " SKIP " + config.getSkip();
            pagination += " LIMIT " + config.getLimit();
            query.append(pagination);
        }


        return query.toString();
    }

    public static String generateNodesCountQuery(NodesCountRequest nodesCountRequest) {
        StringBuilder query = new StringBuilder("Match (n:");
        query.append(nodesCountRequest.getNodeName());
        query.append(") ");
        if (nodesCountRequest.getFilter() != null) {
            query.append("WHERE ");
            String condition = generateWhereClause("n", nodesCountRequest.getFilter());
            query.append(condition);
        }
        query.append("RETURN COUNT(n) as count");

        return query.toString();
    }

    public static String generateRelatedNodesQuery(RelatedNodesRequest relatedNodesRequest) {
        StringBuilder query = new StringBuilder("Match (n:");
        String relationName = relatedNodesRequest.getRelationship();
        Filter[][] relationFilter = relatedNodesRequest.getRelationshipFilter();
        Pagination pagination = relatedNodesRequest.getPagination();

        query.append(relatedNodesRequest.getNodeLabel());
        query.append(")-[r");
        if (relationName != null) {
            String relation = ":" + relationName;
            query.append(relation);
        }
        query.append("]-(relatedNode) WHERE ");

        Filter[][] nodeFilterArray = {{relatedNodesRequest.getNodeFilter()}};
        query.append(generateWhereClause("n", nodeFilterArray));
        if (relationFilter != null) {
            query.append("AND ");
            String condition = generateWhereClause("r", relationFilter);
            query.append(condition);
        }

        query.append("RETURN relatedNode,r");

        if (pagination != null) {
            String paginationString = " SKIP " + pagination.getSkip();
            paginationString += " LIMIT " + pagination.getLimit();
            query.append(paginationString);
        }


        return query.toString();
    }

}
