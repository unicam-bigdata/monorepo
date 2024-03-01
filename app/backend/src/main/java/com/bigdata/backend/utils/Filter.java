package com.bigdata.backend.utils;

import com.bigdata.backend.enums.ColumnDataType;
import com.bigdata.backend.enums.FilterOperator;

public class Filter {
    private String propertyName;
    private FilterOperator filterOperator;
    private String value;
    private ColumnDataType dataType;

    public String getPropertyName() {
        return propertyName;
    }

    public FilterOperator getFilterOperator() {
        return filterOperator;
    }

    public String getValue() {
        return value;
    }

    public ColumnDataType getDataType() {
        return dataType;
    }
}
