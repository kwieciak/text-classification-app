package org.example.project1.extractor.extractors;

import org.example.project1.article.Article;
import org.example.project1.extractor.Extractor;
import org.example.project1.extractor.WordCounterBuffer;

import static org.example.project1.extractor.extractors.SumOfCommonWordsExtractor.THRESHOLD;

public class ProportionUniqueWordsToCommonWordsExtractor implements Extractor<Double> {
    private WordCounterBuffer wordCounterBuffer;

    public ProportionUniqueWordsToCommonWordsExtractor(WordCounterBuffer wordCounterBuffer) {
        this.wordCounterBuffer = wordCounterBuffer;
    }

    @Override
    public Double extract(Article article) {
        int commonWords = wordCounterBuffer.getCommonWords(article, THRESHOLD);
        int uniqueWords = wordCounterBuffer.getUniqueWords(article);

        if (commonWords == 0) {
            return Double.MAX_VALUE;        // value that represents 'infinity' to avoid dividing by 0
        }
        return (double) uniqueWords / commonWords;
    }
}