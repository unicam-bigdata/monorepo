package com.bigdata.backend.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.neo4j.driver.Query;

@Component
public class ImportRepository {
    private final Neo4JDriver neo4JDriver;
    
    @Autowired
    public ImportRepository(Neo4JDriver neo4jDriver){
        this.neo4JDriver = neo4jDriver;
    }

    public void loadCsv(){

        try (var session = this.neo4JDriver.getDriver().session()) {
            var greeting = session.executeWrite(tx -> {
                var query = new Query("MATCH (n) RETURN n");
                var result = tx.run(query);
                while (result.hasNext()) {
                    var record = result.next();
                    // Assuming the result contains a single column named "n"
                    var node = record.get("n").asNode();
                    System.out.println(node.asMap());
                }
                return result.single().get(0).asString();
            });
        } catch (Exception e) {
            // Exception handling code
            System.out.println("An exception occurred: " + e.getMessage());
        }
    }

}
