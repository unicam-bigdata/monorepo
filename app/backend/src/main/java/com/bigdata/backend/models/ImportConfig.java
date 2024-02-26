package com.bigdata.backend.models;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "ImportConfig model")
public class ImportConfig {
    @Schema(description = "Node label that will be created in the graph database", example = "Person")
    private String name;
    @Schema(description = "The columns in the csv file that will be part of the node properties. It is an array of Column object. Check the Column schema for more information.", example = "[{\"name\":\"id\",\"dataType\":\"STRING\"},{\"name\":\"fullName\",\"dataType\":\"STRING\"}]")
    private Column[] columns;
    private Column key;
    private Relationship[] relationships;

    public ImportConfig() {
    }

    public ImportConfig(String name, Column[] columns, Column key, Relationship[] relationships) {
        this.name = name;
        this.columns = columns;
        this.key = key;
        this.relationships = relationships;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public Column[] getColumns() {
        return columns;
    }


    public void setColumns(Column[] columns) {
        this.columns = columns;
    }


    public Column getKey() {
        return key;
    }


    public void setKey(Column key) {
        this.key = key;
    }


    public Relationship[] getRelationships() {
        return relationships;
    }


    public void setRelationships(Relationship[] relationships) {
        this.relationships = relationships;
    }    
    
}