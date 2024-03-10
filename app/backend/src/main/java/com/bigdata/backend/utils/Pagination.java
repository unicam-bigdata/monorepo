package com.bigdata.backend.utils;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "You can specify the skip and limit properties in the pagination object to limit the amount of data returned.")
public class Pagination {
    private int skip;
    private int limit;

    public int getSkip() {
        return skip;
    }

    public int getLimit() {
        return limit;
    }
}
