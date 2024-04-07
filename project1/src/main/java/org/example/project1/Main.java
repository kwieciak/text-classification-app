package org.example.project1;

import org.example.project1.metric.Metric;
import org.example.project1.metric.MetricType;
import org.example.project1.util.Article;
import org.example.project1.util.ArticleFeatures;
import org.example.project1.util.ArticleReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
        for (int i = 1; i < 10; i++) {
            Knn knn = new Knn(i, metric, articles.subList(0, 600), articles.subList(600, 1000));
            List<String> classifiedArticles = knn.classifyArticles();
            double accuracy = knn.calculateAccuracy(classifiedArticles, articles.subList(600, 1000));
            System.out.println("Accuracy for k = " + i + ": " + accuracy);
        }

    }
}
