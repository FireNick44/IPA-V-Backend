package com.sleeping.branch.ipavbackend.shared.models;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DashedLineChart {
    private List<Series> series;

    public DashedLineChart() {
        this.series = new ArrayList<>();
    }

    public void addSeries(String name, List<DataPoint> data) {
        series.add(new Series(name, data));
    }

    @Data
    public static class Series {
        private String name;
        private List<DataPoint> data;

        public Series(String name, List<DataPoint> data) {
            this.name = name;
            this.data = data;
        }
    }
}

