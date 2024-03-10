package com.bigdata.backend.dto;

import com.bigdata.backend.utils.Filter;
import jakarta.validation.constraints.NotBlank;

public class NodesCountRequest {
    @NotBlank(message = "nodeName must be specified.")
    private String nodeName;
    private Filter[][] filter;

    public String getNodeName() {
        return nodeName;
    }

    public Filter[][] getFilter() {
        return filter;
    }
}
