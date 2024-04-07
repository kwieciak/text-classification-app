package org.example.project1;

import javafx.util.Pair;
import org.example.project1.extractor.ExtractorType;
import org.example.project1.util.Article;
import org.example.project1.metric.Metric;
import org.example.project1.util.ArticleFeatures;

import java.util.*;

public class Knn {
    private final int k;
    private final Metric metric;
    private final List<Article> trainingArticles;
    private final List<Article> testingArticles;

    public Knn(int k, Metric metric, List<Article> trainingArticles, List<Article> testingArticles) {
        this.k = k;
        this.metric = metric;
        this.trainingArticles = trainingArticles;
        this.testingArticles = testingArticles;
    }

    public void assignFeatureVectors() {
        for (Article article : trainingArticles) {
            List<Object> featureVector = new ArrayList<>();
            for (ExtractorType extractorType : ExtractorType.values()) {
                featureVector.add(extractorType.getExtractor().extract(article));
            }
            article.setFeaturesVector(featureVector);
            ArticleFeatures.convertNullsToZeros(article);
        }

        for (Article article : testingArticles) {
            List<Object> featureVector = new ArrayList<>();
            for (ExtractorType extractorType : ExtractorType.values()) {
                featureVector.add(extractorType.getExtractor().extract(article));
            }
            article.setFeaturesVector(featureVector);
            ArticleFeatures.convertNullsToZeros(article);
        }
    }

            public List<String> classifyArticles() {
                List<String> classifiedArticles = new ArrayList<>();
                for (Article article : testingArticles) {
                    List<Pair<Double, Article>> distances = new ArrayList<>();
                    for (Article trainingArticle : trainingArticles) {
                        double distance = metric.calculateDistance(article.getFeaturesVector(), trainingArticle.getFeaturesVector());
                        distances.add(new Pair<>(distance, trainingArticle));
                    }

                    distances.sort(Comparator.comparing(Pair::getKey));
                    Map<String, Integer> classCounts = new HashMap<>();
                    for (int i = 0; i < k; i++) {
                        String neighborClass = distances.get(i).getValue().getPlacesList().get(0);
                        classCounts.put(neighborClass, classCounts.getOrDefault(neighborClass, 0) + 1);
                    }

                    String classifiedClass = Collections.max(classCounts.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey();
                    classifiedArticles.add(classifiedClass);
                }
                return classifiedArticles;
        }

        public double calculateAccuracy(List<String> classifiedArticles, List<Article> actualArticles) {
            int correct = 0;
            for (int i = 0; i < classifiedArticles.size(); i++) {
                if (classifiedArticles.get(i).equals(actualArticles.get(i).getPlacesList().get(0))) {
                    correct++;
                }
            }
            return (double) correct / classifiedArticles.size();
        }
}
