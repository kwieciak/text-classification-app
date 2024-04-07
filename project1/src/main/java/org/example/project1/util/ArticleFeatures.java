package org.example.project1.util;

import org.example.project1.extractor.Extractor;
import org.example.project1.extractor.ExtractorFactory;
import org.example.project1.extractor.ExtractorType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Math.max;

public class ArticleFeatures {
    public static void extractFeatures(Article article) {
        List<Object> featuresVector = article.getFeaturesVector();
        if (featuresVector == null) {
            featuresVector = new ArrayList<>();
        }
        for (ExtractorType extractorType : ExtractorType.values()) {
            Extractor<?> extractor = ExtractorFactory.createExtractor(extractorType);
            featuresVector.add(extractor.extract(article));
        }
        article.setFeaturesVector(featuresVector);
    }

    public static void normalizeFeatures(List<Article> articles) {
        // Find min and max values for numeric features
        Map<String, Double> minValues = new HashMap<>();
        Map<String, Double> maxValues = new HashMap<>();
        for (Article article : articles) {
            List<Object> featuresVector = article.getFeaturesVector();
            if (featuresVector != null) {
                for (int i = 5; i < featuresVector.size(); i++) { // Start from index 5 to exclude string features
                    String featureName = "Feature" + i; // Assign a name to each feature
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
                for (int i = 5; i < featuresVector.size(); i++) { // Start from index 5 to exclude string features
                    String featureName = "Feature" + i;
                    double value = ((Number) featuresVector.get(i)).doubleValue();
                    double minValue = minValues.get(featureName);
                    double maxValue = maxValues.get(featureName);
                    double normalizedValue;
                    if (minValue != maxValue) {
                        normalizedValue = (value - minValue) / (maxValue - minValue); // Perform normalization
                    } else {
                        // Handle case when min and max are the same (avoid division by zero)
                        normalizedValue = 0.0; // Set normalized value to 0
                    }
                    featuresVector.set(i, normalizedValue); // Update the feature value in the featuresVector
                }
            }
        }
    }


}
