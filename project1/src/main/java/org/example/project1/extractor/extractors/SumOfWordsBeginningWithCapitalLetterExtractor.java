package org.example.project1.extractor.extractors;

import org.example.project1.extractor.Extractor;
import org.example.project1.util.Article;

import java.util.Map;

public class SumOfWordsBeginningWithCapitalLetterExtractor implements Extractor<Integer> {

    @Override
    public Integer extract(Article article) {
        Map<String, Integer> wordCounter = countWords(article.getText());
        return (int) wordCounter.keySet().stream()
                .filter(word -> Character.isUpperCase(word.charAt(0)))
                .count();
    }
}