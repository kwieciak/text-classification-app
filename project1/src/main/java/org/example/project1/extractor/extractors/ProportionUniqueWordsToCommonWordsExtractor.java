package org.example.project1.extractor.extractors;

import org.example.project1.extractor.Extractor;
import org.example.project1.util.Article;

import static org.example.project1.extractor.extractors.SumOfCommonWordsExtractor.THRESHOLD;


public class ProportionUniqueWordsToCommonWordsExtractor implements Extractor<Double> {
    @Override
    public Double extract(Article article) {
        int commonWords = countCommonWords(article.getText().toLowerCase(), THRESHOLD);
        int uniqueWords = countUniqueWords(article.getText().toLowerCase());

        if (commonWords == 0) {
            return Double.MAX_VALUE;        // value that represents 'infinity' to avoid dividing by 0
        }

        return (double) uniqueWords / commonWords;
    }
}