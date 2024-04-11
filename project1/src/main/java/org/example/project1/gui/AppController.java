package org.example.project1.gui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import org.example.project1.Knn;
import org.example.project1.metric.Metric;
import org.example.project1.metric.MetricType;
import org.example.project1.util.Article;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AppController {
    @FXML
    private Button startKnnButton;
    @FXML
    private ComboBox chooseMetric;
    @FXML
    private TextField numOfNeighbors;
    @FXML
    private Slider sliderTrainingPercent;
    private Metric metric;
    private List<Article> articles = new ArrayList<>();
    DirectoryChooser directoryChooser = new DirectoryChooser();

    @FXML
    public void initialize() {
        chooseMetric.getItems().addAll(MetricType.values());
        chooseMetric.getSelectionModel().selectFirst();
        startKnnButton.setDisable(true);
    }

    @FXML
    public void pressedChooseFiles() {
        directoryChooser.setTitle("Choose directory with articles");
        File selectedDirectory = directoryChooser.showDialog(startKnnButton.getScene().getWindow());

        if (selectedDirectory != null) {
            String directoryPath = selectedDirectory.getAbsolutePath();
            System.out.println("Selected directory: " + directoryPath);
            startKnnButton.setDisable(false);
            articles = loadArticlesFromDirectory(directoryPath);
        } else {
            System.out.println("No directory selected");
        }
    }

    private List<Article> loadArticlesFromDirectory(String directoryPath) {
    }

    @FXML
    public void changeMetric() {
        String metricName = chooseMetric.getSelectionModel().getSelectedItem().toString();
        MetricType metricType = MetricType.valueOf(metricName.toUpperCase());
        metric = metricType.createMetric();
        System.out.println("Metric changed to: " + metricName);
    }

    @FXML
    public void pressedStartKnn() {
        double trainingPercent = sliderTrainingPercent.getValue() / 100.0;
        int k = Integer.parseInt(numOfNeighbors.getText());
        int totalArticles = articles.size();
        int trainingArticles = (int) (totalArticles * trainingPercent);
        List<Article> trainingSet = articles.subList(0, trainingArticles);
        List<Article> testingSet = articles.subList(trainingArticles, totalArticles);
        System.out.println("Starting Knn with k=" + k + ", metric=" + metric + ", trainingSet=" + trainingSet.size() + ", testingSet=" + testingSet.size());
//        Knn knn = new Knn(k, metric, trainingSet, testingSet);
    }
}