package com.bigdata.backend.utils;

import com.bigdata.backend.enums.ColumnDataType;
import com.bigdata.backend.enums.FilterOperator;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "It denotes a filter condition. It is mostly used in a 2 dimensional array form: Filter[][]. The inner elements are combined using AND whereas the outer elements are combined using OR.")
public class Filter {
    @Schema(description = "The property name in the data store.")
    private String propertyName;
    @Schema(description = "The comparison operator.")
    private FilterOperator filterOperator;
    @Schema(description = "The value on the right hand side of the filter operator.")
    private String value;
    @Schema(description = "The datatype of the property. It is useful to compare different data types such as text, date, number and datetime.")
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
