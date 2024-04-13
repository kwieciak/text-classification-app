package org.example.project1;

import org.example.project1.extractor.Extractor;
import org.example.project1.extractor.ExtractorType;
import org.example.project1.extractor.WordCounterBuffer;
import org.example.project1.util.ClassificationStats;
import org.example.project1.metric.Metric;
import org.example.project1.metric.MetricType;
import org.example.project1.article.Article;
import org.example.project1.article.ArticleFeatures;
import org.example.project1.article.ArticleReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException {
        // Loading all articles
        String directoryPath = "project1/data";
        List<Article> articles = new ArrayList<>();
        for (int i = 0; i < 22; i++) {
            String filePath = directoryPath + "/reut2-" + String.format("%03d", i) + ".sgm";
            ArticleReader articleReader = new ArticleReader(filePath);
            articles.addAll(articleReader.readArticles());
        }
        System.out.println("Wczytywanie danych skonczone");
        long start = System.currentTimeMillis();
        WordCounterBuffer wordCounterBuffer = new WordCounterBuffer();
        //Now u have to choose which extractors u want to use
        List<ExtractorType> chosenExtractors = new ArrayList<>();
        chosenExtractors.add(ExtractorType.MOST_USED_CITY);
        chosenExtractors.add(ExtractorType.MOST_USED_COUNTRY);
        chosenExtractors.add(ExtractorType.MOST_USED_CONTINENT);
        chosenExtractors.add(ExtractorType.MOST_USED_CURRENCY);
        chosenExtractors.add(ExtractorType.MOST_USED_SE);
        chosenExtractors.add(ExtractorType.SUM_OF_WORDS_BEGINNING_WITH_CAPITAL_LETTER);
        chosenExtractors.add(ExtractorType.SUM_OF_UNIQUE_WORDS);
        chosenExtractors.add(ExtractorType.SUM_OF_COMMON_WORDS);
        chosenExtractors.add(ExtractorType.PROPORTION_UNIQUE_WORDS_TO_ALL_WORDS);
        chosenExtractors.add(ExtractorType.PROPORTION_UNIQUE_WORDS_TO_COMMON_WORDS);
        // Extracting features for each article and normalizing them
        for (Article article : articles) {
            ArticleFeatures.extractFeatures(article, wordCounterBuffer, chosenExtractors);
        }
        long finish = System.currentTimeMillis();
        System.out.println("Ekstrakcja cech skonczona");
        System.out.println("Czas ekstrakcji cech: " + (finish - start) + " ms");
        ArticleFeatures.normalizeFeatures(articles);
        System.out.println("Normalizacja cech skonczona");

        // kNN classification
        System.out.println(articles.size());
        Metric metric = MetricType.EUCLIDEAN.createMetric();
        int k = 5;
        Knn knn = new Knn(k, metric, articles.subList(0, (int) (articles.size() *0.7)), articles.subList((int) (articles.size() *0.7), articles.size()));
        Map<String, int[]> confusionMatrix = knn.calculateConfusionMatrix();
        ClassificationStats.calculateGlobalStats(confusionMatrix);
        ClassificationStats.calculateClassStats(confusionMatrix);
        ClassificationStats.printConfusionMatrix(confusionMatrix);
    }

}
