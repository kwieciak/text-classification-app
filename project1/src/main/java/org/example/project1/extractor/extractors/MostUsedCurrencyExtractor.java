package org.example.project1.extractor.extractors;

import org.example.project1.CsvReader;
import org.example.project1.extractor.Extractor;
import org.example.project1.util.Article;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MostUsedCurrencyExtractor implements Extractor<String> {
    private final Map<String, List<String>> currencies = CsvReader.readCsv("project1/src/main/resources/org/example/project1/extractors/currencies.csv");

    @Override
    public String extract(Article article) {
        String text = article.getText().toLowerCase();
        Map<String, Integer> currenciesCounter = new HashMap<>();

        for (String currency : currencies.keySet()) {
            for (String alias : currencies.get(currency)) {
                Pattern pattern = Pattern.compile("\\b" + Pattern.quote(alias.toLowerCase()) + "\\b");
                Matcher matcher = pattern.matcher(text);
                int count = 0;
                while (matcher.find()) {
                    count++;
                }
                if (count > 0) {
                    currenciesCounter.put(alias, count);
                }
            }
        }

        return currenciesCounter
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }
}