package org.example.project1;

import org.controlsfx.control.tableview2.filter.filtereditor.SouthFilter;
import org.example.project1.extractor.Extractor;
import org.example.project1.extractor.ExtractorType;
import org.example.project1.extractor.extractors.*;
import org.example.project1.measure.GeneralizedNgramMeasure;
import org.example.project1.metric.Metric;
import org.example.project1.metric.MetricType;
import org.example.project1.util.Article;
import org.example.project1.util.ArticleFeatures;
import org.example.project1.util.ArticleReader;

import java.io.IOException;
import java.util.*;

import static java.util.Collections.max;

public class Main {
    public static void main(String[] args) throws IOException {
//        //Wczytanie wszystkich artykułów
//        String directoryPath = "project1/data";
//        List<Article> comparedArticleList = new ArrayList<>();
//        for (int i = 0; i < 22; i++) {
//            String filePath = directoryPath + "/reut2-" + String.format("%03d", i) + ".sgm";
//            ArticleReader articleReader = new ArticleReader(filePath);
//            comparedArticleList.addAll(articleReader.readArticles());
//        }
//        //Wyliczenie wartości cech dla każdego artykułu
//        for (Article article: comparedArticleList) {
//            ArticleFeatures.extractFeatures(article);
//        }
//
//        //Normalizacja wartości cech liczbowych
//        ArticleFeatures.normalizeFeatures(comparedArticleList);
//        for (Article article : comparedArticleList) {
//            System.out.println("Feature vector for article " + article.getFeaturesVector());
//        }





// TO MIKA NAPISAŁA
        ArticleReader articleReader = new ArticleReader("project1/data/reut2-008.sgm");
        List<Article> articles = articleReader.readArticles();
        Metric metric = MetricType.EUCLIDEAN.createMetric();
        Knn knn = new Knn(3, metric, articles.subList(0,10), articles.subList(10,50));
        knn.assignFeatureVectors();
        for (Article article : articles.subList(10,50)) {
            System.out.println("Feature vector for article " + article.getFeaturesVector());
        }
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


        //ArticleReader articleReader = new ArticleReader("project1/data/reut2-001.sgm");
        //List<Article> articles = articleReader.readArticles();
        //Knn knn = new Knn(3, null, articles.subList(0,10), articles.subList(10,50));
        //knn.assignFeatureVectors();
        //System.out.println(articles.get(1).getFeaturesVector());



    }
}
