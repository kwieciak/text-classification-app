package org.example.project1.metric;

import java.util.List;

public interface Metric {
    double calculateDistance(List<Double> vector1, List<Double> vector2);
}
