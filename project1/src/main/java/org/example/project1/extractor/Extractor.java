package org.example.project1.extractor;

import org.example.project1.helpers.CsvReader;
import org.example.project1.util.Article;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface Extractor<T> {
    T extract(Article article);

    default String extractStringFeature(Article article, String csvFilePath) {
        String text = article.getText().toLowerCase();
        Map<String, List<String>> featureTerms = CsvReader.readCsv(csvFilePath);
        Map<String, Integer> featureCounter = new HashMap<>();

        for (String featureTerm : featureTerms.keySet()) {
            for (String alias : featureTerms.get(featureTerm)) {
                Pattern pattern = Pattern.compile("\\b" + Pattern.quote(alias.toLowerCase()) + "\\b");
                Matcher matcher = pattern.matcher(text);
                int count = 0;
                while (matcher.find()) {
                    count++;
                }
                if (count > 0) {
                    featureCounter.put(alias, count);
                }
            }
        }

        return featureCounter
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    default Map<String, Integer> countWords(String text) {
        Map<String, Integer> wordCounter = new HashMap<>();
        Pattern pattern = Pattern.compile("\\b[a-zA-Z]+\\b");
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            String word = matcher.group();
            wordCounter.put(word, wordCounter.getOrDefault(word, 0) + 1);
        }
        return wordCounter;
    }

}
