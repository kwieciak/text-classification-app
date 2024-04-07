package org.example.project1.metric.metrics;

import org.example.project1.measure.GeneralizedNgramMeasure;
import org.example.project1.metric.Metric;

import java.util.List;

public class ChebyshevMetric implements Metric {
    @Override
    public double calculateDistance(List<Object> vector1, List<Object> vector2) {
        if (vector1.size() == vector2.size()) {
            double max_diff = 0;
            double temp_diff = 0;
            for (int i = 0; i < vector1.size(); i++) {
                if (vector1.get(i) != null || vector2.get(i) != null) {
                    if (vector1.get(i) instanceof String && vector2.get(i) instanceof String) {
                        double measure = GeneralizedNgramMeasure.calculateMetric((String) vector1.get(i), (String) vector2.get(i));
                        temp_diff = Math.abs(measure);
                    } else if (vector1.get(i) instanceof Number && vector2.get(i) instanceof Number) {
                        double value1 = ((Number) vector1.get(i)).doubleValue();
                        double value2 = ((Number) vector2.get(i)).doubleValue();
                        temp_diff = Math.abs(value1 - value2);
                    }
                    if (temp_diff > max_diff) {
                        max_diff = temp_diff;
                    }
                }
            }
            return max_diff;
        } else {
            throw new IllegalArgumentException("Vectors must have the same size");
        }
    }
}