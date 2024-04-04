package org.example.project1.metric.metrics;

import org.example.project1.metric.Metric;

import java.util.List;

public class ChebyshevMetric implements Metric {
    @Override
    public double calculateDistance(List<Double> vector1, List<Double> vector2) {
        if (vector1.size() == vector2.size()) {
            double max_diff = 0;
            for (int i = 0; i < vector1.size(); i++) {
                double temp_diff = Math.abs(vector1.get(i) - vector2.get(i));
                if (temp_diff > max_diff) {
                    max_diff = temp_diff;
                }
            }
            return max_diff;
        } else {
            throw new IllegalArgumentException("Vectors must have the same size");
        }
    }
}
