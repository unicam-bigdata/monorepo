package com.bigdata.backend.dto;

import com.bigdata.backend.utils.Filter;
import com.bigdata.backend.utils.Pagination;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class NodesRequest {
    @NotBlank(message = "nodeName must be specified.")
    private String nodeName;
    private Filter[][] filter;
    private Pagination pagination;

    public String getNodeName() {
        return nodeName;
    }

    public Filter[][] getFilter() {
        return filter;
    }

    public Pagination getPagination() {
        return pagination;
    }
}
