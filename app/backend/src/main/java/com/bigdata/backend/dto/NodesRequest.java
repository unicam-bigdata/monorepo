package com.bigdata.backend.dto;

import com.bigdata.backend.utils.Filter;
import com.bigdata.backend.utils.Pagination;

public class NodesRequest {
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
