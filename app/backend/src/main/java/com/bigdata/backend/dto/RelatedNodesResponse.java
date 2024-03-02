package com.bigdata.backend.dto;

import java.util.Map;

public class RelatedNodesResponse {
    private NodeResponse node;
    private RelationshipResponse relationship;

    public NodeResponse getNode() {
        return node;
    }

    public void setNode(NodeResponse node) {
        this.node = node;
    }

    public RelationshipResponse getRelationship() {
        return relationship;
    }

    public void setRelationship(RelationshipResponse relationship) {
        this.relationship = relationship;
    }
}
