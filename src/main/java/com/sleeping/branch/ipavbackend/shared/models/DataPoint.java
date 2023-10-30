package com.sleeping.branch.ipavbackend.shared.models;

import lombok.Data;

@Data
public class DataPoint {
    private Double x;
    private Double y;

    public DataPoint(Double x, Double y) {
        this.x = x;
        this.y = y;
    }
}