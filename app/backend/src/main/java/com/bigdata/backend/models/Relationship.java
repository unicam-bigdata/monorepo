package com.bigdata.backend.models;

import com.bigdata.backend.enums.RelationshipDirection;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Configuration of the foreign node and relationship properties.")
public class Relationship {
    @Schema(description = "The node label to which a node is related to.", example = "Subject")
    private String entityName;

    @Schema(description = "The column name in the csv that serves as the foreign-key column.")
    private Column foreignEntityKey;

    @Schema(description = "The relationship label that two related nodes has.", example = "ENROLLED_IN")
    private String relationShipLabel;

    @Schema(description = "The relationship direction from the node specified to the foreign node.")
    private RelationshipDirection direction;

    @Schema(description = "The relationship properties that a relationship has.")
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
