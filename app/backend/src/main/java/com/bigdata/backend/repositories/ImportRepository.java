package com.bigdata.backend.repositories;

import com.bigdata.backend.enums.ColumnDataType;
import com.bigdata.backend.enums.RelationshipDirection;
import com.bigdata.backend.models.Column;
import com.bigdata.backend.models.ImportConfig;
import com.bigdata.backend.models.Relationship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.neo4j.driver.Query;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ImportRepository {
    private final Neo4JDriver neo4JDriver;

    @Autowired
    public ImportRepository(Neo4JDriver neo4jDriver,Environment environment) {

        this.neo4JDriver = neo4jDriver;
        CypherQueryBuilder.PUBLIC_BACKEND_DOMAIN = environment.getProperty("backend.public_domain");
    }

    public void loadData(List<ImportConfig> importConfigs) {

        try (var session = this.neo4JDriver.getDriver().session()) {
            Map<String, Object> importQueryStringMap = CypherQueryBuilder.generateImportQuery(importConfigs);

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
