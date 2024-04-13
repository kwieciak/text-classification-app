package org.example.project1.extractor;

import org.example.project1.article.Article;
import org.example.project1.util.CsvReader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface Extractor<T> {
    T extract(Article article);

    default String extractStringFeature(String text,  List<String> featureTerms) {
        Map<String, Integer> featureCounter = new HashMap<>();

        for (String featureTerm : featureTerms) {
            Pattern pattern = Pattern.compile("\\b" + featureTerm + "\\b", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(text);

            int count = 0;
            while (matcher.find()) {
                count++;
            }

            featureCounter.put(featureTerm, count);
        }

        return featureCounter
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }
}