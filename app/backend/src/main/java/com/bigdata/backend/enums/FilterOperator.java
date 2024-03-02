package com.bigdata.backend.enums;

public enum FilterOperator {

    EQUALS("="),
    NOT_EQUALS("!="),
    LESS_THAN("<"),
    GREATER_THAN(">"),
    LESS_THAN_OR_EQUAL_TO("<="),
    GREATER_THAN_OR_EQUAL_TO(">="),
    CONTAINS("CONTAINS");

    private final String symbol;

    FilterOperator(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}
