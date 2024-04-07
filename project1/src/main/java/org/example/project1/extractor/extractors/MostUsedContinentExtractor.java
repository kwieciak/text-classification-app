package org.example.project1.extractor.extractors;

import org.example.project1.extractor.Extractor;
import org.example.project1.helpers.CsvReader;
import org.example.project1.util.Article;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MostUsedContinentExtractor implements Extractor<String> {
    private final Map<String, List<String>> continents = CsvReader.readCsv("project1/src/main/resources/org/example/project1/extractors/continents.csv");

    @Override
    public String extract(Article article) {
        String text = article.getText().toLowerCase();
        Map<String, Integer> continentsCounter = new HashMap<>();

        for (String continent : continents.keySet()) {
            for (String alias : continents.get(continent)) {
                Pattern pattern = Pattern.compile("\\b" + Pattern.quote(alias.toLowerCase()) + "\\b");
                Matcher matcher = pattern.matcher(text);
                int count = 0;
                while (matcher.find()) {
                    count++;
                }
                if (count > 0) {
                    continentsCounter.put(alias, count);
                }
            }
        }

        return continentsCounter
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }
}