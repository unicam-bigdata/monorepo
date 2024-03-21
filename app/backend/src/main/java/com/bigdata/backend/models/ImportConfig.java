package com.bigdata.backend.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Schema(description = "ImportConfig model")
public class ImportConfig {
    @Schema(description = "Node label that will be created in the graph database.", example = "Person")
    @NotBlank(message = "name must be specified.")
    private String name;

    @Schema(description = "The columns in the csv file that will be part of the node properties. It is an array of Column object. Check the Column schema for more information.", example = "[{\"name\":\"personId\",\"dataType\":\"STRING\"},{\"name\":\"fullName\",\"dataType\":\"STRING\"}]")
    private Column[] columns;

    @NotNull(message = "key must be defined in order to have an identifier for a node.")
    @Schema(description = "The column that will serve as the primary key.")
    private Column key;

    @NotNull(message = "labelKey must be defined in order to have a label identifier for a node.")
    @Schema(description = "The labelKey denotes the property that will serve as the main label of a node.")
    private String labelKey;

    @Schema(description = "Contains array of relationship configurations that the node label specified has. This property is used to define the column in the csv that will serve as a reference to the related node.")
    private Relationship[] relationships;

    public ImportConfig() {
    }

    public ImportConfig(String name, Column[] columns, Column key, Relationship[] relationships, String labelKey) {
        this.name = name;
        this.columns = columns;
        this.key = key;
        this.relationships = relationships;
        this.labelKey = labelKey;
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

    public String getLabelKey() {
        return labelKey;
    }

    public void setLabelKey(String labelKey) {
        this.labelKey = labelKey;
    }

}