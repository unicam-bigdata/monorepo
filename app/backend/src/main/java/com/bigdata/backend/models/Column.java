package com.bigdata.backend.models;

import com.bigdata.backend.enums.ColumnDataType;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "This is used to specify the column name or property and datatype.")
public class Column {
    @Schema(description = "The name of the column or property.")
    private String name;
    @Schema(description = "The data type of the property.")
    private ColumnDataType dataType;

    public Column() {
    }

    public Column(String name, ColumnDataType dataType) {
        this.name = name;
        this.dataType = dataType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ColumnDataType getDataType() {
        return dataType;
    }

    public void setDataType(ColumnDataType dataType) {
        this.dataType = dataType;
    }
    
    
    
}