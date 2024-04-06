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
        return null;
    }


}
