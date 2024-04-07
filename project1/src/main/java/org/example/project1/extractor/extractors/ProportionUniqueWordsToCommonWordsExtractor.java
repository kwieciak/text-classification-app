package org.example.project1.extractor.extractors;

import org.example.project1.extractor.Extractor;
import org.example.project1.util.Article;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProportionUniqueWordsToCommonWordsExtractor implements Extractor<Double> {
    private static final int THRESHOLD = 3;     // how many times a word is considered common

    @Override
    public Double extract(Article article) {
        String text = article.getText().toLowerCase();
        Set<String> uniqueWords = new HashSet<>();
        Map<String, Integer> wordCounter = new HashMap<>();

        Pattern pattern = Pattern.compile("\\b[a-z]+\\b");
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            String word = matcher.group();
            uniqueWords.add(word);
            wordCounter.put(word, wordCounter.getOrDefault(word, 0) + 1);
        }

        int commonWords = wordCounter.values().stream().filter(v -> v >= THRESHOLD).mapToInt(Integer::intValue).sum();
        if (commonWords == 0) {
            return Double.MAX_VALUE;        // value that represents 'infinity' to avoid dividing by 0
        }

        return (double) uniqueWords.size() / commonWords;
    }
}