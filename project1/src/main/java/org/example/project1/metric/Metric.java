package org.example.project1.metric;

import java.util.List;

public interface Metric {
    double calculateDistance(List<Object> vector1, List<Object> vector2);
}
