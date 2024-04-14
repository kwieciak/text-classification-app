package org.example.project1.gui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import org.example.project1.Knn;
import org.example.project1.extractor.ExtractorType;
import org.example.project1.extractor.WordCounterBuffer;
import org.example.project1.measure.GeneralizedNgramMeasure;
import org.example.project1.metric.Metric;
import org.example.project1.metric.MetricType;
import org.example.project1.article.Article;
import org.example.project1.article.ArticleFeatures;
import org.example.project1.article.ArticleReader;
import org.example.project1.util.ChartDrawer;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class AppController {
    @FXML
    public ComboBox chooseMeasure;
    @FXML
    public ListView extractorsList;
    @FXML
    public GridPane resultsGrid;
    @FXML
    public GridPane accuracyGrid;
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
    private WordCounterBuffer wordCounterBuffer = new WordCounterBuffer();
    private FileChooser fileChooser = new FileChooser();
    private  ChartDrawer chartDrawer;

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
        if (metric == null || numOfNeighbors.getText().isEmpty() || sliderTrainingPercent.getValue() == 0 || selectedExtractors.isEmpty()) {
            popUpWindow.showInfo("Please select a metric, measure, number of neighbors, features to extract and a percent of training articles before starting kNN.");
            return;
        }
        for (Article article : articles) {
            ArticleFeatures.extractFeatures(article, wordCounterBuffer, selectedExtractors);
        }
        ArticleFeatures.normalizeFeatures(articles);
        double trainingPercent = sliderTrainingPercent.getValue() / 100.0;
        List<ExtractorType> extractors = new ArrayList<>(selectedExtractors);
        int k = Integer.parseInt(numOfNeighbors.getText());
        int totalArticles = articles.size();
        int trainingArticles = (int) (totalArticles * trainingPercent);
        List<Article> trainingSet = articles.subList(0, trainingArticles);
        List<Article> testingSet = articles.subList(trainingArticles, totalArticles);
        int testingSetSize = testingSet.size();
        System.out.println("Starting Knn with k=" + k + ", metric=" + metric + ", trainingSet=" + trainingSet.size() + ", testingSet=" + testingSet.size() + ", extractors=" + extractors);
        Knn knn = new Knn(k, metric, trainingSet, testingSet);
        Map<String, int[]> confusionMatrix = knn.calculateConfusionMatrix();
        populateResultsGrid(resultsGrid, accuracyGrid, confusionMatrix, testingSetSize);
    }

    public void populateResultsGrid(GridPane resultsGrid, GridPane accuracyGrid, Map<String, int[]> confusionMatrix, int testingSetSize) {
        resultsGrid.getChildren().clear();
        resultsGrid.getRowConstraints().clear();

        Map<String, double[]> classStats = calculateClassStats(confusionMatrix);

        int rowIndex = 1;
        int totalInstances = 0;
        double totalRecall = 0, totalPrecision = 0;
        for (Map.Entry<String, double[]> entry : classStats.entrySet()) {
            String className = entry.getKey();
            double[] stats = entry.getValue();
            double recall = stats[0];
            double precision = stats[1];
            double f1 = stats[2];
            int instances = (int) stats[3];

            totalRecall += recall * instances;
            totalPrecision += precision * instances;
            totalInstances += instances;

            resultsGrid.add(new Label(className), 0, rowIndex);
            resultsGrid.add(new Label(String.format("%.2f", recall)), 1, rowIndex);
            resultsGrid.add(new Label(String.format("%.2f", precision)), 2, rowIndex);
            resultsGrid.add(new Label(String.format("%.2f", f1)), 3, rowIndex);
            rowIndex++;
        }

        double globalRecall = totalRecall / totalInstances;
        double globalPrecision = totalPrecision / totalInstances;
        double globalF1 = 2 * (globalPrecision * globalRecall) / (globalPrecision + globalRecall);

        accuracyGrid.add(new Label("Global accuracy"), 0, 0);
        accuracyGrid.add(new Label(String.format("%.2f", globalRecall)), 1, 0);

        resultsGrid.add(new Label("Recall"), 1, 0);
        resultsGrid.add(new Label("Precision"), 2, 0);
        resultsGrid.add(new Label("F1 Score"), 3, 0);

        resultsGrid.add(new Label("global"), 0, rowIndex);
        resultsGrid.add(new Label(String.format("%.2f", globalRecall)), 1, rowIndex);
        resultsGrid.add(new Label(String.format("%.2f", globalPrecision)), 2, rowIndex);
        resultsGrid.add(new Label(String.format("%.2f", globalF1)), 3, rowIndex);

        chartDrawer = new ChartDrawer("k-NN Classification", "Classification Statistics", confusionMatrix);
        chartDrawer.pack();
        chartDrawer.setVisible(true);;
    }

    private Map<String, double[]> calculateClassStats(Map<String, int[]> confusionMatrix) {
        Map<String, double[]> classStats = new HashMap<>();
        for (Map.Entry<String, int[]> entry : confusionMatrix.entrySet()) {
            int TP = entry.getValue()[0];
            int FP = entry.getValue()[1];
            int TN = entry.getValue()[2];
            int FN = entry.getValue()[3];

            double precision = (TP + FP) == 0 ? 0 : (double) TP / (TP + FP);
            double recall = (TP + FN) == 0 ? 0 : (double) TP / (TP + FN);
            double f1 = (precision + recall) == 0 ? 0 : 2 * (precision * recall) / (precision + recall);
            int instances = TP + FN;

            classStats.put(entry.getKey(), new double[]{recall, precision, f1, instances});
        }
        return classStats;
    }

    @FXML
    public void changeMeasure() {
        String measureName = chooseMeasure.getSelectionModel().getSelectedItem().toString();
        System.out.println("Measure changed to: " + measureName);
    }
}