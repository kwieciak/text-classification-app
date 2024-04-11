package org.example.project1;

import javafx.util.Pair;
import org.example.project1.extractor.ExtractorType;
import org.example.project1.metric.Metric;
import org.example.project1.util.Article;

import java.util.*;

public class Knn {
    private final int k;
    private final Metric metric;
    private final List<Article> trainingArticles;
    private final List<Article> testingArticles;
    private final List<ExtractorType> featureTypes;

    public Knn(int k, Metric metric, List<Article> trainingArticles, List<Article> testingArticles, List<ExtractorType> featureTypes) {
        this.k = k;
        this.metric = metric;
        this.trainingArticles = trainingArticles;
        this.testingArticles = testingArticles;
        this.featureTypes = featureTypes;
    }

    public List<String> classifyArticles() {
        List<String> classifiedArticles = new ArrayList<>();
        for (Article article : testingArticles) {
            List<Pair<Double, Article>> distances = new ArrayList<>();
            for (Article trainingArticle : trainingArticles) {
                List<Object> articleFeatures = selectFeatures(article.getFeaturesVector());
                List<Object> trainingArticleFeatures = selectFeatures(trainingArticle.getFeaturesVector());
                double distance = metric.calculateDistance(articleFeatures, trainingArticleFeatures);
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

    private List<Object> selectFeatures(List<Object> featuresVector) {
        List<Object> selectedFeatures = new ArrayList<>();
        for (ExtractorType featureType : featureTypes) {
            selectedFeatures.add(featuresVector.get(featureType.ordinal()));
        }
        return selectedFeatures;
    }
}
