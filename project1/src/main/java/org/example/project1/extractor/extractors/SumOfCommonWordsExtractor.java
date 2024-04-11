package org.example.project1.extractor.extractors;

import org.example.project1.extractor.Extractor;
import org.example.project1.util.Article;

import java.util.Map;
import java.util.stream.Stream;

public class SumOfCommonWordsExtractor implements Extractor<Integer> {
    private static final int THRESHOLD = 3;         // how many times a word is considered common

    @Override
    public Integer extract(Article article) {
        Map<String, Integer> wordCounter = countWords(article.getText().toLowerCase());
        // getting the number of occurrences and filtering which ones are greater than or equal to the threshold (5)
        Stream<Integer> stream = wordCounter.values().stream().filter(v -> v >= THRESHOLD);
        // returning the sum as an integer
        return stream.mapToInt(Integer::intValue).sum();
    }
}