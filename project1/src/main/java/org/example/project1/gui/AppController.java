package org.example.project1.gui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import org.example.project1.Knn;
import org.example.project1.metric.Metric;
import org.example.project1.metric.MetricType;
import org.example.project1.util.Article;
import org.example.project1.util.ArticleReader;

import java.io.File;
import java.io.IOException;
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
    private PopUpWindow popUpWindow = new PopUpWindow();
    private DirectoryChooser directoryChooser = new DirectoryChooser();

    @FXML
    public void initialize() {
        chooseMetric.getItems().addAll(MetricType.values());
//        chooseMetric.getSelectionModel().selectFirst();
        startKnnButton.setDisable(true);
    }

    @FXML
    public void pressedChooseFiles() throws IOException {
        directoryChooser.setTitle("Choose directory with articles");
        File selectedDirectory = directoryChooser.showDialog(startKnnButton.getScene().getWindow());

        if (selectedDirectory != null) {
            String directoryPath = selectedDirectory.getAbsolutePath();
            System.out.println("Selected directory: " + directoryPath);
            startKnnButton.setDisable(false);

            File folder = new File(directoryPath);
            File[] listOfFiles = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".sgm"));

            if (listOfFiles != null && listOfFiles.length > 0) {
                for (File file : listOfFiles) {
                    String filePath = file.getAbsolutePath();
                    ArticleReader articleReader = new ArticleReader(filePath);
                    articles.addAll(articleReader.readArticles());
                }
            } else {
                popUpWindow.showInfo("No .sgm files found in the directory");
                System.out.println("No .sgm files found in the directory");
            }
        } else {
            popUpWindow.showError("No directory selected");
            System.out.println("No directory selected");
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
        if (metric == null || numOfNeighbors.getText().isEmpty() || sliderTrainingPercent.getValue() == 0) {
            popUpWindow.showInfo("Please select a metric, number of neighbors and a percent of training articles before starting kNN.");
            return;
        }

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