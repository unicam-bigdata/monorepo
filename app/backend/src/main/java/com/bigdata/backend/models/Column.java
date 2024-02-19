package com.bigdata.backend.models;

import com.bigdata.backend.enums.ColumnDataType;


class Column {
    private String name;
    private ColumnDataType dataType;

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