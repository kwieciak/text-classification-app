package org.example.project1.gui;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import org.example.project1.Knn;
import org.example.project1.extractor.ExtractorType;
import org.example.project1.measure.GeneralizedNgramMeasure;
import org.example.project1.metric.Metric;
import org.example.project1.metric.MetricType;
import org.example.project1.util.Article;
import org.example.project1.util.ArticleReader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AppController {
    @FXML
    public ComboBox chooseMeasure;
    @FXML
    public ListView extractorsList;
    @FXML
    public TableView confusionMatrix;
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
        initializeConfusionMatrix();
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

//    private List<Article> loadArticlesFromDirectory(String directoryPath) throws IOException {
//        for (int i = 0; i < 3; i++) {
//            String filePath = directoryPath + "/reut2-" + String.format("%03d", i) + ".sgm";
//            ArticleReader articleReader = new ArticleReader(filePath);
//            articles.addAll(articleReader.readArticles());
//        }
//    }

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
        if (metric == null || measure == null || numOfNeighbors.getText().isEmpty() || sliderTrainingPercent.getValue() == 0 || selectedExtractors.isEmpty()) {
            popUpWindow.showInfo("Please select a metric, measure, number of neighbors, features to extract and a percent of training articles before starting kNN.");
            return;
        }

        double trainingPercent = sliderTrainingPercent.getValue() / 100.0;
        List<ExtractorType> extractors = new ArrayList<>(selectedExtractors);
        int k = Integer.parseInt(numOfNeighbors.getText());
        int totalArticles = articles.size();
        int trainingArticles = (int) (totalArticles * trainingPercent);
        List<Article> trainingSet = articles.subList(0, trainingArticles);
        List<Article> testingSet = articles.subList(trainingArticles, totalArticles);
        System.out.println("Starting Knn with k=" + k + ", metric=" + metric + ", trainingSet=" + trainingSet.size() + ", testingSet=" + testingSet.size() + ", extractors=" + extractors);
//        Knn knn = new Knn(k, metric, trainingSet, testingSet);
    }

    public void populateConfusionMatrix(Map<String, int[]> confusionMatrixData) {
        for (Map.Entry<String, int[]> entry : confusionMatrixData.entrySet()) {
            ConfusionMatrixRow row = new ConfusionMatrixRow(
                    entry.getKey(),
                    entry.getValue()[0],
                    entry.getValue()[1],
                    entry.getValue()[2],
                    entry.getValue()[3],
                    entry.getValue()[4],
                    entry.getValue()[5]
            );
            confusionMatrix.getItems().add(row);
        }
    }

    @FXML
    public void initializeConfusionMatrix() {
        confusionMatrix.getColumns().clear();
        confusionMatrix.getItems().clear();

        TableColumn<ConfusionMatrixRow, String> actualClassColumn = new TableColumn<>("Actual Class");
        actualClassColumn.setCellValueFactory(new PropertyValueFactory<>("actualClass"));

        TableColumn<ConfusionMatrixRow, Number> japanColumn = new TableColumn<>("Japan");
        japanColumn.setCellValueFactory(new PropertyValueFactory<>("japan"));

        TableColumn<ConfusionMatrixRow, Number> germanyColumn = new TableColumn<>("West Germany");
        germanyColumn.setCellValueFactory(new PropertyValueFactory<>("germany"));

        TableColumn<ConfusionMatrixRow, Number> ukColumn = new TableColumn<>("UK");
        ukColumn.setCellValueFactory(new PropertyValueFactory<>("uk"));

        TableColumn<ConfusionMatrixRow, Number> usaColumn = new TableColumn<>("USA");
        usaColumn.setCellValueFactory(new PropertyValueFactory<>("usa"));

        TableColumn<ConfusionMatrixRow, Number> canadaColumn = new TableColumn<>("Canada");
        canadaColumn.setCellValueFactory(new PropertyValueFactory<>("canada"));

        TableColumn<ConfusionMatrixRow, Number> franceColumn = new TableColumn<>("France");
        franceColumn.setCellValueFactory(new PropertyValueFactory<>("france"));

        confusionMatrix.getColumns().addAll(actualClassColumn, japanColumn, germanyColumn, ukColumn, usaColumn, canadaColumn, franceColumn);
    }


    @FXML
    public void changeMeasure() {
        String measureName = chooseMeasure.getSelectionModel().getSelectedItem().toString();
        System.out.println("Measure changed to: " + measureName);
    }
}