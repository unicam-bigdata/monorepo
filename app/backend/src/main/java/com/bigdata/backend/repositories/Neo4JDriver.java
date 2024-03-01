package com.bigdata.backend.repositories;

import org.springframework.stereotype.Component;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;

@Component
public class Neo4JDriver {
    private final Driver driver;
    
    public Neo4JDriver() {
        try{
            this.driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "golf-happy-picasso-bonus-baron-6004"));
        }catch (Exception exception){
            throw new RuntimeException("Error initializing Neo4j driver", exception);
        }
    }

    public Driver getDriver(){
        return this.driver;
    }
}
