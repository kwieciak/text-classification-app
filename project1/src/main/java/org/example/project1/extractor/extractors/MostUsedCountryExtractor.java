package org.example.project1.extractor.extractors;

import org.example.project1.extractor.Extractor;
import org.example.project1.helpers.CsvReader;
import org.example.project1.util.Article;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MostUsedCountryExtractor implements Extractor<String> {
    private final Map<String, List<String>> countries = CsvReader.readCsv("project1/src/main/resources/org/example/project1/extractors/countries.csv");

    @Override
    public String extract(Article article) {
        String text = article.getText().toLowerCase();
        Map<String, Integer> countriesCounter = new HashMap<>();

        for (String country : countries.keySet()) {
            for (String alias : countries.get(country)) {
                Pattern pattern = Pattern.compile("\\b" + Pattern.quote(alias.toLowerCase()) + "\\b");
                Matcher matcher = pattern.matcher(text);
                int count = 0;
                while (matcher.find()) {
                    count++;
                }
                if (count > 0) {
                    countriesCounter.put(alias, count);
                }
            }
        }

        return countriesCounter
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }
}