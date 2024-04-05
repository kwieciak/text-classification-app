package org.example.project1.extractor.extractors;

import org.example.project1.CsvReader;
import org.example.project1.extractor.Extractor;
import org.example.project1.util.Article;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MostUsedCityExtractor implements Extractor<String> {
    private final Map<String, List<String>> cities = CsvReader.readCsv("project1/src/main/resources/org/example/project1/extractors/cities.csv");
    @Override
    public String extract(Article article) {
        String text = article.getText().toLowerCase();
        Map<String, Integer> citiesCounter = new HashMap<>();
        for (String city : cities.keySet()) {
            for (String alias : cities.get(city)) {
                if (text.contains(alias.toLowerCase())) {
                    if(!citiesCounter.containsKey(alias)){
                        citiesCounter.put(alias, 1);
                    }
                    else{
                        citiesCounter.put(alias, citiesCounter.get(alias) + 1);
                    }
                }
            }
        }
        return citiesCounter
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .get()
                .getKey();
    }
}
