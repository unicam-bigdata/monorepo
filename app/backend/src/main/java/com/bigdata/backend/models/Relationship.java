package com.bigdata.backend.models;

import com.bigdata.backend.enums.RelationshipDirection;

public class Relationship {
    private String entityName;
    private Column foreignEntityKey;
    private String relationShipLabel;
    private RelationshipDirection direction;
    private Column[] relationshipProperties;

    public Relationship() {
    }

    public Relationship(String entityName, Column foreignEntityKey, String relationShipLabel,
                        RelationshipDirection direction, Column[] relationshipProperties) {
        this.entityName = entityName;
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

    public Column getForeignEntityKey() {
        return foreignEntityKey;
    }

    public void setForeignEntityKey(Column foreignEntityKey) {
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

    public Column[] getRelationshipProperties() {
        return relationshipProperties;
    }

    public void setRelationshipProperties(Column[] relationshipProperties) {
        this.relationshipProperties = relationshipProperties;
    }
    
}
