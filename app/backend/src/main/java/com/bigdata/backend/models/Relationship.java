package com.bigdata.backend.models;

import com.bigdata.backend.enums.RelationshipDirection;

public class Relationship {
    private String entityName;
    private String primaryEntityKey;
    private String foreignEntityKey;
    private String relationShipLabel;
    private RelationshipDirection direction;
    private Column relationshipProperties;

    public Relationship(String entityName, String primaryEntityKey, String foreignEntityKey, String relationShipLabel,
            RelationshipDirection direction, Column relationshipProperties) {
        this.entityName = entityName;
        this.primaryEntityKey = primaryEntityKey;
        this.foreignEntityKey = foreignEntityKey;
        this.relationShipLabel = relationShipLabel;
        this.direction = direction;
        this.relationshipProperties = relationshipProperties;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getPrimaryEntityKey() {
        return primaryEntityKey;
    }

    public void setPrimaryEntityKey(String primaryEntityKey) {
        this.primaryEntityKey = primaryEntityKey;
    }

    public String getForeignEntityKey() {
        return foreignEntityKey;
    }

    public void setForeignEntityKey(String foreignEntityKey) {
        this.foreignEntityKey = foreignEntityKey;
    }

    public String getRelationShipLabel() {
        return relationShipLabel;
    }

    public void setRelationShipLabel(String relationShipLabel) {
        this.relationShipLabel = relationShipLabel;
    }

    public RelationshipDirection getDirection() {
        return direction;
    }

    public void setDirection(RelationshipDirection direction) {
        this.direction = direction;
    }

    public Column getRelationshipProperties() {
        return relationshipProperties;
    }

    public void setRelationshipProperties(Column relationshipProperties) {
        this.relationshipProperties = relationshipProperties;
    }
    
}
