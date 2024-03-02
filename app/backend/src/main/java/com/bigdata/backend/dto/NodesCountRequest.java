package com.bigdata.backend.dto;

import com.bigdata.backend.utils.Filter;

public class NodesCountRequest {
    private String nodeName;
    private Filter[][] filter;

    public String getNodeName() {
        return nodeName;
    }

    public Filter[][] getFilter() {
        return filter;
    }
}
