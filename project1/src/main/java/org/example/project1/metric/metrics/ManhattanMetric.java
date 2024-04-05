package org.example.project1.metric.metrics;

import org.example.project1.metric.Metric;

import java.util.List;

public class ManhattanMetric implements Metric {
    @Override
    public double calculateDistance(List<Double> vector1, List<Double> vector2) {
        if (vector1.size() == vector2.size()) {
            double sum_diff = 0;
            for (int i = 0; i < vector1.size(); i++) {
                sum_diff += Math.abs(vector1.get(i) - vector2.get(i));
            }
            return sum_diff;
        } else {
            throw new IllegalArgumentException("Vectors must have the same size");
        }
    }
}
