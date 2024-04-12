package org.example.project1.extractor.extractors;

import org.example.project1.extractor.Extractor;
import org.example.project1.article.Article;

public class ProportionUniqueWordsToAllWordsExtractor implements Extractor<Double> {

    @Override
    public Double extract(Article article) {
        int allWords = countAllWords(article.getText().toLowerCase());
        int uniqueWords = countUniqueWords(article.getText().toLowerCase());

        return (double) uniqueWords / allWords;
    }
}