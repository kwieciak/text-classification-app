package org.example.project1;

import org.example.project1.extractor.ExtractorType;
import org.example.project1.helpers.ClassificationStats;
import org.example.project1.metric.Metric;
import org.example.project1.metric.MetricType;
import org.example.project1.util.Article;
import org.example.project1.util.ArticleFeatures;
import org.example.project1.util.ArticleReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException {
        // Loading all articles
        String directoryPath = "project1/data";
        List<Article> articles = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            String filePath = directoryPath + "/reut2-" + String.format("%03d", i) + ".sgm";
            ArticleReader articleReader = new ArticleReader(filePath);
            articles.addAll(articleReader.readArticles());
        }
        // Extracting features for each article and normalizing them
        for (Article article : articles) {
            ArticleFeatures.extractFeatures(article);
        }
        ArticleFeatures.normalizeFeatures(articles);

        // kNN classification
        Metric metric = MetricType.EUCLIDEAN.createMetric();
        int k = 9;
        Knn knn = new Knn(k, metric, articles.subList(0, 600), articles.subList(600, 1000));
        Map<String, int[]> confusionMatrix = knn.calculateConfusionMatrix();
        ClassificationStats.calculateGlobalStats(confusionMatrix);
        ClassificationStats.calculateClassStats(confusionMatrix);
        ClassificationStats.printConfusionMatrix(confusionMatrix);
    }

}
