package org.example.project1.metric.metrics;

import org.example.project1.metric.Metric;

import java.util.List;


public class WeightedMetric implements Metric {
    private Metric metric;
    private double weight;

    public WeightedMetric(Metric metric, double weight) {
        this.metric = metric;
        this.weight = weight;
    }

    @Override
    public double calculateDistance(List<Double> vector1, List<Double> vector2) {
        return metric.calculateDistance(vector1, vector2) * weight;
    }
}
