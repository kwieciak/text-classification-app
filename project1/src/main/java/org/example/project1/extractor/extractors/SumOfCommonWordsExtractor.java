package org.example.project1.extractor.extractors;

import org.example.project1.extractor.Extractor;
import org.example.project1.util.Article;

import java.util.Map;
import java.util.stream.Stream;

public class SumOfCommonWordsExtractor implements Extractor<Integer> {
    static final int THRESHOLD = 3;         // how many times a word is considered common

    @Override
    public Integer extract(Article article) {
        return countCommonWords(article.getText().toLowerCase(), THRESHOLD);
    }
}