package org.example.project1;

import java.util.List;

public class QualityMeasures {

    // może tak
    public static double calculateAccuracy(List<String> actual, List<String> predicted) {
        if (actual.size() == predicted.size()) {
            int correct = 0;
            for (int i = 0; i < actual.size(); i++) {
                if (actual.get(i).equals(predicted.get(i))) {
                    correct++;
                }
            }
            return (double) correct / actual.size();
        } else {
            throw new IllegalArgumentException("Actual and predicted lists must have the same size");
        }
    }
}