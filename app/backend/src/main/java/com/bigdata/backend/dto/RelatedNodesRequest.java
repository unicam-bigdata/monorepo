package com.bigdata.backend.dto;

import com.bigdata.backend.utils.Filter;
import com.bigdata.backend.utils.Pagination;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class RelatedNodesRequest {
    @NotBlank(message = "nodeLabel must be specified.")
    @Schema(description = "The node label of the node that you are trying to fetch its related nodes.")
    private String nodeLabel;
    @NotNull(message = "nodeFilter must be defined in order to retrieve related nodes.")
    @Schema(description = "The filter configuration of the node that you are trying to fetch its related nodes. It is better if you filter by id to get the specific node.")
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
