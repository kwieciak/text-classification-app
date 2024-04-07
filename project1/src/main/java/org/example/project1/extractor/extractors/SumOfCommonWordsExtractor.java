package org.example.project1.extractor.extractors;

import org.example.project1.extractor.Extractor;
import org.example.project1.util.Article;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class SumOfCommonWordsExtractor implements Extractor<Integer> {
    private static final int THRESHOLD = 5;         // how many times a word is considered common

    @Override
    public Integer extract(Article article) {
        String text = article.getText().toLowerCase();
        Map<String, Integer> wordCounter = new HashMap<>();

        Pattern pattern = Pattern.compile("\\b[a-z]+\\b");
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            String word = matcher.group();
            wordCounter.put(word, wordCounter.getOrDefault(word, 0) + 1);
        }

        // TODO: USUNĄĆ TO, do debuggingu
        //wordCounter.entrySet().stream()
        //        .filter(entry -> entry.getValue() >= THRESHOLD)
        //        .forEach(entry -> System.out.println("Common word: " + entry.getKey() + ", Count: " + entry.getValue()));

        // getting the number of occurrences and filtering which ones are greater than or equal to the threshold (5)
        Stream<Integer> stream = wordCounter.values().stream().filter(v -> v >= THRESHOLD);
        // returning the sum as an integer
        return stream.mapToInt(Integer::intValue).sum();
    }
}