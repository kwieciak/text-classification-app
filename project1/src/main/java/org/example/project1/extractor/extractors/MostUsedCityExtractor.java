package org.example.project1.extractor.extractors;

import org.example.project1.extractor.Extractor;
import org.example.project1.helpers.CsvReader;
import org.example.project1.util.Article;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MostUsedCityExtractor implements Extractor<String> {
    private final Map<String, List<String>> cities = CsvReader.readCsv("project1/src/main/resources/org/example/project1/extractors/cities.csv");

    @Override
    public String extract(Article article) {
        String text = article.getText().toLowerCase();
        Map<String, Integer> citiesCounter = new HashMap<>();

        for (String city : cities.keySet()) {
            for (String alias : cities.get(city)) {
                Pattern pattern = Pattern.compile("\\b" + Pattern.quote(alias.toLowerCase()) + "\\b");
                Matcher matcher = pattern.matcher(text);
                int count = 0;
                while (matcher.find()) {
                    count++;
                }
                if (count > 0) {
                    citiesCounter.put(alias, count);
                }
            }
        }

        return citiesCounter
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }
}