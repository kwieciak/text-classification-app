package org.example.project1.helpers;

import java.util.Map;

public class ClassificationStats {
    public static void calculateGlobalStats(Map<String, int[]> confusionMatrix) {
        int totalTP = 0, totalFP = 0, totalTN = 0, totalFN = 0;

        for (int[] values : confusionMatrix.values()) {
            totalTP += values[0];
            totalFP += values[1];
            totalTN += values[2];
            totalFN += values[3];
        }

        double accuracy = (double) (totalTP + totalTN) / (totalTP + totalFP + totalTN + totalFN);
        double recall = (double) totalTP / (totalTP + totalFN);
        double precision = (double) totalTP / (totalTP + totalFP);
        double f1 = 2 * (precision * recall) / (precision + recall);

        System.out.println("Global Accuracy: " + accuracy);
        System.out.println("Global Recall: " + recall);
        System.out.println("Global Precision: " + precision);
        System.out.println("Global F1 Score: " + f1);
    }

    public static void calculateClassStats(Map<String, int[]> confusionMatrix) {
        System.out.printf("%-15s %-10s %-10s %-10s%n", "Class", "Precision", "Recall", "F1 Score");
        for (Map.Entry<String, int[]> entry : confusionMatrix.entrySet()) {
            int TP = entry.getValue()[0];
            int FP = entry.getValue()[1];
            int FN = entry.getValue()[3];

            double precision = (TP + FP) == 0 ? 0 : (double) TP / (TP + FP);
            double recall = (TP + FN) == 0 ? 0 : (double) TP / (TP + FN);
            double f1 = (precision + recall) == 0 ? 0 : 2 * (precision * recall) / (precision + recall);

            System.out.printf("%-15s %-10.2f %-10.2f %-10.2f%n", entry.getKey(), precision, recall, f1);
        }
    }

    public static void printConfusionMatrix(Map<String, int[]> confusionMatrix) {
        System.out.printf("%-15s %-5s %-5s %-5s %-5s%n", "Class", "TP", "FP", "TN", "FN");
        for (Map.Entry<String, int[]> entry : confusionMatrix.entrySet()) {
            System.out.printf("%-15s %-5d %-5d %-5d %-5d%n",
                    entry.getKey(),
                    entry.getValue()[0],
                    entry.getValue()[1],
                    entry.getValue()[2],
                    entry.getValue()[3]
            );
        }
    }
}
