package org.example.project1;

import org.example.project1.util.Article;
import org.example.project1.metric.Metric;

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

    public List<String> classifyArticles() {
        List<String> predictedCountries = new ArrayList<>();
        for (Article testArticle : testingArticles) {
            Map<Double, String> distanceToCountry = new HashMap<>();
            for (Article trainArticle : trainingArticles) {
                double distance = metric.calculateDistance(testArticle, trainArticle);
                distanceToCountry.put(distance, trainArticle.getCountry());
            }
            List<Double> sortedDistances = new ArrayList<>(distanceToCountry.keySet());
            Collections.sort(sortedDistances);
            Map<String, Integer> countryVotes = new HashMap<>();
            for (int i = 0; i < k; i++) {
                String country = distanceToCountry.get(sortedDistances.get(i));
                countryVotes.put(country, countryVotes.getOrDefault(country, 0) + 1);
            }
            String predictedCountry = Collections.max(countryVotes.entrySet(), Map.Entry.comparingByValue()).getKey();
            predictedCountries.add(predictedCountry);
        }
        return predictedCountries;

    }


}
