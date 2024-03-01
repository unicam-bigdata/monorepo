package com.bigdata.backend.dto;

public class NodesCountResponse {
    private int count;

    public NodesCountResponse() {
    }

    public NodesCountResponse(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
