package org.example.project1;

import org.example.project1.extractor.Extractor;
import org.example.project1.extractor.ExtractorType;
import org.example.project1.extractor.extractors.*;
import org.example.project1.measure.GeneralizedNgramMeasure;
import org.example.project1.metric.Metric;
import org.example.project1.metric.MetricType;
import org.example.project1.util.Article;
import org.example.project1.util.ArticleReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {

        ArticleReader articleReader = new ArticleReader("project1/data/reut2-002.sgm");
        List<Article> articles = articleReader.readArticles();
        Metric metric = MetricType.EUCLIDEAN.createMetric();
        Knn knn = new Knn(3, metric, articles.subList(0,10), articles.subList(10,50));
        knn.assignFeatureVectors();
        List<String> classifiedArticles = knn.classifyArticles();
        double accuracy = knn.calculateAccuracy(classifiedArticles, articles.subList(10,50));
        System.out.println("Accuracy: " + accuracy);
        System.out.println(classifiedArticles);
        System.out.println("Classified classes:");
        for (String classifiedArticle : classifiedArticles) {
            System.out.println(classifiedArticle);
        }

        System.out.println("Actual classes:");
        for (Article article : articles.subList(10,50)) {
            System.out.println(article.getPlacesList().get(0));
        }

    }
}
