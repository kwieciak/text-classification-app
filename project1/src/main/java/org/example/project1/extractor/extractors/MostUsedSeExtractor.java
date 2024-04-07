package org.example.project1.extractor.extractors;

import org.example.project1.extractor.Extractor;
import org.example.project1.helpers.CsvReader;
import org.example.project1.util.Article;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MostUsedSeExtractor implements Extractor<String> {
    private final Map<String, List<String>> seTerms = CsvReader.readCsv("project1/src/main/resources/org/example/project1/extractors/se.csv");

    @Override
    public String extract(Article article) {
        String text = article.getText().toLowerCase();
        Map<String, Integer> seCounter = new HashMap<>();

        for (String seTerm : seTerms.keySet()) {
            for (String alias : seTerms.get(seTerm)) {
                Pattern pattern = Pattern.compile("\\b" + Pattern.quote(alias.toLowerCase()) + "\\b");
                Matcher matcher = pattern.matcher(text);
                int count = 0;
                while (matcher.find()) {
                    count++;
                }
                if (count > 0) {
                    seCounter.put(alias, count);
                }
            }
        }

        return seCounter
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }
}