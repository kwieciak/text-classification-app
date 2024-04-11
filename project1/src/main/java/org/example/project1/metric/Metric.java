package org.example.project1.metric;

import org.example.project1.measure.GeneralizedNgramMeasure;

import java.util.List;
import java.util.function.BiFunction;

public interface Metric {
    double calculateDistance(List<Object> vector1, List<Object> vector2);

    default double calculate(List<Object> vector1, List<Object> vector2, BiFunction<Double, Double, Double> function) {
        if (vector1.size() != vector2.size()) {
            throw new IllegalArgumentException("Vectors must have the same size");
        }

        double result = 0;
        for (int i = 0; i < vector1.size(); i++) {
            if (vector1.get(i) != null || vector2.get(i) != null) {
                if (vector1.get(i) instanceof String && vector2.get(i) instanceof String) {
                    double measure = GeneralizedNgramMeasure.calculateMetric((String) vector1.get(i), (String) vector2.get(i));
                    result = function.apply(result, Math.abs(measure));
                } else if (vector1.get(i) instanceof Number && vector2.get(i) instanceof Number) {
                    double value1 = ((Number) vector1.get(i)).doubleValue();
                    double value2 = ((Number) vector2.get(i)).doubleValue();
                    result = function.apply(result, Math.abs(value1 - value2));
                }
            }
        }
        return result;
    }
}
