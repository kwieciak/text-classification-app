package org.example.project1.article;

import org.example.project1.extractor.Extractor;
import org.example.project1.extractor.ExtractorFactory;
import org.example.project1.extractor.ExtractorType;
import org.example.project1.extractor.WordCounterBuffer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ArticleFeatures {
    static ExtractorFactory extractorFactory = new ExtractorFactory();

        public static void extractFeatures(Article article, WordCounterBuffer wordCounterBuffer, List<ExtractorType>chosenExtractors) {
        List<Object> featuresVector = article.getFeaturesVector();
        if (featuresVector == null) {
            featuresVector = new ArrayList<>();
        }
        for (ExtractorType extractorType : chosenExtractors) {
            Extractor<?> extractor = ExtractorFactory.createExtractor(extractorType, wordCounterBuffer);
            featuresVector.add(extractor.extract(article));
        }
        article.setFeaturesVector(featuresVector);
        convertNullsToZeros(article);
    }

    public static void normalizeFeatures(List<Article> articles) {
        Map<String, Double> minValues = new HashMap<>();
        Map<String, Double> maxValues = new HashMap<>();
        for (Article article : articles) {
            List<Object> featuresVector = article.getFeaturesVector();
            if (featuresVector != null) {
                for (int i = 5; i < featuresVector.size(); i++) {
                    String featureName = "Feature" + i;
                    double value = ((Number) featuresVector.get(i)).doubleValue();
                    minValues.put(featureName, Math.min(minValues.getOrDefault(featureName, Double.MAX_VALUE), value));
                    maxValues.put(featureName, Math.max(maxValues.getOrDefault(featureName, Double.MIN_VALUE), value));
                }
            }
        }

        // Normalize numeric features in each article
        for (Article article : articles) {
            List<Object> featuresVector = article.getFeaturesVector();
            if (featuresVector != null) {
                for (int i = 5; i < featuresVector.size(); i++) {
                    String featureName = "Feature" + i;
                    double value = ((Number) featuresVector.get(i)).doubleValue();
                    double minValue = minValues.get(featureName);
                    double maxValue = maxValues.get(featureName);
                    double normalizedValue;
                    if (minValue != maxValue) {
                        normalizedValue = (value - minValue) / (maxValue - minValue);
                    } else {
                        normalizedValue = 0.0; // Set normalized value to 0
                    }
                    featuresVector.set(i, normalizedValue);
                }
            }
        }
    }

    public static void convertNullsToZeros(Article article) {
        List<Object> featuresVector = article.getFeaturesVector();
        if (featuresVector != null) {
            for (int i = 0; i < featuresVector.size(); i++) {
                if (featuresVector.get(i) == null) {
                    featuresVector.set(i, 0.0);
                }
            }
        }
    }


}
