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


    public Knn(int k, Metric metric, List<Article> trainingArticles, List<Article> testingArticles) {
        this.k = k;
        this.metric = metric;
        this.trainingArticles = trainingArticles;
        this.testingArticles = testingArticles;
    }

    public Pair<List<String>, List<Article>> classifyArticles() {
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
        return new Pair<>(classifiedArticles, testingArticles);
    }

//    public double calculateAccuracy(List<String> classifiedArticles, List<Article> actualArticles) {
//        int correct = 0;
//        for (int i = 0; i < classifiedArticles.size(); i++) {
//            if (classifiedArticles.get(i).equals(actualArticles.get(i).getPlacesList().get(0))) {
//                correct++;
//            }
//        }
//        return (double) correct / classifiedArticles.size();
//    }

    public Map<String, int[]> calculateConfusionMatrix() {
        Pair<List<String>, List<Article>> result = classifyArticles();
        List<String> classifiedArticles = result.getKey();
        List<Article> actualArticles = result.getValue();

        List<String> classes = Arrays.asList("west germany", "usa", "france", "uk", "canada", "japan");
        Map<String, int[]> confusionMatrix = new HashMap<>();
        for (String className : classes) {
            confusionMatrix.put(className, new int[4]);     // TP, FP, TN, FN
        }

        for (int i = 0; i < classifiedArticles.size(); i++) {
            String predictedClass = classifiedArticles.get(i);
            String actualClass = actualArticles.get(i).getPlacesList().get(0);

            for (String className : classes) {
                if (className.equals(actualClass)) {
                    if (className.equals(predictedClass)) {
                        confusionMatrix.get(className)[0]++;    // TP
                    } else {
                        confusionMatrix.get(className)[3]++;    // FN
                    }
                } else {
                    if (className.equals(predictedClass)) {
                        confusionMatrix.get(className)[1]++;    // FP
                    } else {
                        confusionMatrix.get(className)[2]++;    // TN
                    }
                }
            }
        }

        return confusionMatrix;
    }

}


