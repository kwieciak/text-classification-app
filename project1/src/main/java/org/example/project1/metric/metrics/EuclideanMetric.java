package org.example.project1.metric.metrics;

import org.example.project1.measure.GeneralizedNgramMeasure;
import org.example.project1.metric.Metric;

import java.util.List;

public class EuclideanMetric implements Metric {
    @Override
    public double calculateDistance(List<Object> vector1, List<Object> vector2) {
        double distance = calculate(vector1, vector2, (sum, diff) -> sum + Math.pow(diff, 2));
        return Math.sqrt(distance);
    }
}
