package com.bigdata.backend.dto;

import java.util.List;

public class MetaDataResponse {
    private List<String> data;

    public MetaDataResponse(List<String> data) {
        this.data = data;
    }

    public List<String> getData() {
        return data;
    }

}
