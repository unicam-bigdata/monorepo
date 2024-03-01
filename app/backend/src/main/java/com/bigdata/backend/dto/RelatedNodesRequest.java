package com.bigdata.backend.dto;

import com.bigdata.backend.utils.Filter;
import com.bigdata.backend.utils.Pagination;

public class RelatedNodesRequest {
    private String nodeLabel;
    private Filter nodeFilter;
    private String relationship;

    private Filter[][] relationshipFilter;
    private Pagination pagination;

    public String getNodeLabel() {
        return nodeLabel;
    }

    public Filter getNodeFilter() {
        return nodeFilter;
    }

    public String getRelationship() {
        return relationship;
    }

    public Filter[][] getRelationshipFilter() {
        return relationshipFilter;
    }

    public Pagination getPagination() {
        return pagination;
    }
}
