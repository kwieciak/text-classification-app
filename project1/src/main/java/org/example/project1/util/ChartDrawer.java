package org.example.project1.util;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class ChartDrawer extends JFrame {

    public ChartDrawer(String applicationTitle, String chartTitle, Map<String, int[]> confusionMatrix) {
        super(applicationTitle);
        setLayout(new GridLayout(2, 2));

        add(createChartPanel("Precision", createDataset(confusionMatrix, "Precision")));
        add(createChartPanel("Recall", createDataset(confusionMatrix, "Recall")));
        add(createChartPanel("F1 Score", createDataset(confusionMatrix, "F1")));
//        add(createChartPanel("Accuracy", createDataset(confusionMatrix, "Accuracy")));

        pack();
        setVisible(true);
    }

    private ChartPanel createChartPanel(String title, DefaultCategoryDataset dataset) {
        JFreeChart barChart = ChartFactory.createBarChart(
                title,
                "Class",
                "%",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false);

        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new Dimension(600, 500));

        return new ChartPanel(barChart);
    }

    private DefaultCategoryDataset createDataset(Map<String, int[]> confusionMatrix, String metric) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (Map.Entry<String, int[]> entry : confusionMatrix.entrySet()) {
            int TP = entry.getValue()[0];
            int FP = entry.getValue()[1];
            int TN = entry.getValue()[2];
            int FN = entry.getValue()[3];

            double recall = (double) TP / (TP + FN) * 100;
            double precision = (double) TP / (TP + FP) * 100;
            double f1 = 2 * (precision * recall) / (precision + recall) * 100;

            switch (metric) {
                case "Precision":
                    dataset.addValue(precision, entry.getKey(), metric);
                    break;
                case "Recall":
                    dataset.addValue(recall, entry.getKey(), metric);
                    break;
                case "F1":
                    dataset.addValue(f1, entry.getKey(), metric);
                    break;
            }
        }

        return dataset;
    }
}