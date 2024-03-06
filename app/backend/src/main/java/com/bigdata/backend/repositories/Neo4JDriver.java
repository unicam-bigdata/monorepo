package com.bigdata.backend.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;

@Component
public class Neo4JDriver {

    private final Driver driver;


    @Autowired
    public Neo4JDriver(Environment environment) {
        final String NEO4J_URI = environment.getProperty("backend.neo4j.uri");
        final String NEO4J_USERNAME = environment.getProperty("backend.neo4j.username");
        final String NEO4J_PASSWORD = environment.getProperty("backend.neo4j.password");

        try {
            this.driver = GraphDatabase.driver(NEO4J_URI, AuthTokens.basic(NEO4J_USERNAME, NEO4J_PASSWORD));
        } catch (Exception exception) {
            throw new RuntimeException("Error initializing Neo4j driver", exception);
        }
    }

    public Driver getDriver() {
        return this.driver;
    }
}
