package org.example.project1.extractor.extractors;

import org.example.project1.extractor.Extractor;
import org.example.project1.util.Article;


public class ProportionUniqueWordsToCommonWordsExtractor implements Extractor<Double> {
    private final Extractor<Integer> commonWordsExtractor = new SumOfCommonWordsExtractor();
    private final Extractor<Integer> uniqueWordsExtractor = new SumOfUniqueWordsExtractor();

    @Override
    public Double extract(Article article) {
        int commonWords = commonWordsExtractor.extract(article);
        int uniqueWords = uniqueWordsExtractor.extract(article);

        if (commonWords == 0) {
            return Double.MAX_VALUE;        // value that represents 'infinity' to avoid dividing by 0
        }

        return (double) uniqueWords / commonWords;
    }
}