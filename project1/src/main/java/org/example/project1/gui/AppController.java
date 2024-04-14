package org.example.project1.gui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import org.example.project1.Knn;
import org.example.project1.extractor.ExtractorType;
import org.example.project1.measure.GeneralizedNgramMeasure;
import org.example.project1.metric.Metric;
import org.example.project1.metric.MetricType;
import org.example.project1.article.Article;
import org.example.project1.article.ArticleFeatures;
import org.example.project1.article.ArticleReader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class AppController {
    @FXML
    public ComboBox chooseMeasure;
    @FXML
    public ListView extractorsList;
    @FXML
    public GridPane resultsGrid;
    @FXML
    private Button startKnnButton;
    @FXML
    private ComboBox chooseMetric;
    @FXML
    private TextField numOfNeighbors;
    @FXML
    private Slider sliderTrainingPercent;
    private Metric metric;
    private GeneralizedNgramMeasure measure;
    private List<Article> articles = new ArrayList<>();
    private List<File> selectedFiles = new ArrayList<>();
    private PopUpWindow popUpWindow = new PopUpWindow();
    private DirectoryChooser directoryChooser = new DirectoryChooser();
    private FileChooser fileChooser = new FileChooser();

    @FXML
    public void initialize() {
        chooseMetric.getItems().addAll(MetricType.values());
        chooseMeasure.getItems().addAll("GENERALIZED N-GRAM");
        extractorsList.getItems().addAll(ExtractorType.values());
        extractorsList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        startKnnButton.setDisable(true);
        numOfNeighbors.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                numOfNeighbors.setText(newValue.replaceAll("\\D", ""));
            }
            if (newValue.startsWith("0")) {
                numOfNeighbors.setText(newValue.replaceFirst("^0+(?!$)", ""));
            }
            System.out.println("k changed to: " + numOfNeighbors.getText());
        });
        sliderTrainingPercent.valueProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("Training percent changed to: " + newValue);
        });
    }

    @FXML
    public void pressedChooseFiles() throws IOException {
        fileChooser.setTitle("Choose .sgm files");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("SGM Files", "*.sgm"));
        selectedFiles = fileChooser.showOpenMultipleDialog(startKnnButton.getScene().getWindow());

        if (selectedFiles != null && !selectedFiles.isEmpty()) {
            startKnnButton.setDisable(false);

            for (File file : selectedFiles) {
                String filePath = file.getAbsolutePath();
                System.out.println("Selected file: " + filePath);
                ArticleReader articleReader = new ArticleReader(filePath);
                articles.addAll(articleReader.readArticles());
            }
        } else {
            popUpWindow.showError("No files selected");
            System.out.println("No files selected");
        }
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
        ObservableList<ExtractorType> selectedExtractors = extractorsList.getSelectionModel().getSelectedItems();
        if (metric == null || numOfNeighbors.getText().isEmpty() || sliderTrainingPercent.getValue() == 0) {
            popUpWindow.showInfo("Please select a metric, measure, number of neighbors, features to extract and a percent of training articles before starting kNN.");
            return;
        }
        for (Article article : articles) {
//            ArticleFeatures.extractFeatures(article);
        }
        ArticleFeatures.normalizeFeatures(articles);
        double trainingPercent = sliderTrainingPercent.getValue() / 100.0;
        List<ExtractorType> extractors = new ArrayList<>(selectedExtractors);
        int k = Integer.parseInt(numOfNeighbors.getText());
        int totalArticles = articles.size();
        int trainingArticles = (int) (totalArticles * trainingPercent);
        List<Article> trainingSet = articles.subList(0, trainingArticles);
        List<Article> testingSet = articles.subList(trainingArticles, totalArticles);
        System.out.println("Starting Knn with k=" + k + ", metric=" + metric + ", trainingSet=" + trainingSet.size() + ", testingSet=" + testingSet.size() + ", extractors=" + extractors);
        Knn knn = new Knn(k, metric, trainingSet, testingSet);
////        Map<String, Map<String, Integer>> confusionMatrix = knn.calculateConfusionMatrix();
        Map<String, int[]> confusionMatrix = knn.calculateConfusionMatrix();
        populateResultsGrid(resultsGrid, confusionMatrix);
////        displayResults(confusionMatrix);
//        ClassificationStats.calculateGlobalStats(confusionMatrix);
//        ClassificationStats.calculateClassStats(confusionMatrix);
    }

    public void populateResultsGrid(GridPane resultsGrid, Map<String, int[]> confusionMatrix) {
        // Clear the GridPane
        resultsGrid.getChildren().clear();
        resultsGrid.getRowConstraints().clear();

        // Calculate global stats
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

        resultsGrid.add(new Label("Accuracy"), 1, 0);
        resultsGrid.add(new Label("Recall"), 2, 0);
        resultsGrid.add(new Label("Precision"), 3, 0);
        resultsGrid.add(new Label("F1 Score"), 4, 0);

        resultsGrid.add(new Label("Global"), 0, 1);
        resultsGrid.add(new Label(String.format("%.2f", accuracy)), 1, 1);
        resultsGrid.add(new Label(String.format("%.2f", recall)), 2, 1);
        resultsGrid.add(new Label(String.format("%.2f", precision)), 3, 1);
        resultsGrid.add(new Label(String.format("%.2f", f1)), 4, 1);

        // Calculate class stats and add them to the GridPane
        int rowIndex = 4;
        for (Map.Entry<String, int[]> entry : confusionMatrix.entrySet()) {
            int TP = entry.getValue()[0];
            int FP = entry.getValue()[1];
            int FN = entry.getValue()[3];

            accuracy = (TP + FP) == 0 ? 0 : (double) (TP + FN) / (TP + FP + FN);
            precision = (TP + FP) == 0 ? 0 : (double) TP / (TP + FP);
            recall = (TP + FN) == 0 ? 0 : (double) TP / (TP + FN);
            f1 = (precision + recall) == 0 ? 0 : 2 * (precision * recall) / (precision + recall);

            resultsGrid.add(new Label(entry.getKey()), 0, rowIndex);
            resultsGrid.add(new Label(String.format("%.2f", accuracy)), 1, rowIndex);
            resultsGrid.add(new Label(String.format("%.2f", precision)), 2, rowIndex);
            resultsGrid.add(new Label(String.format("%.2f", recall)), 3, rowIndex);
            resultsGrid.add(new Label(String.format("%.2f", f1)), 4, rowIndex);
            rowIndex++;
        }
    }

    @FXML
    public void changeMeasure() {
        String measureName = chooseMeasure.getSelectionModel().getSelectedItem().toString();
        System.out.println("Measure changed to: " + measureName);
    }
}