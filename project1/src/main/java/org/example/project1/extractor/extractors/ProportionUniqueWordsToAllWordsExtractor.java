package org.example.project1.extractor.extractors;

import org.example.project1.extractor.Extractor;
import org.example.project1.article.Article;
import org.example.project1.extractor.WordCounterBuffer;

public class ProportionUniqueWordsToAllWordsExtractor implements Extractor<Double> {
    private WordCounterBuffer wordCounterBuffer;
    public ProportionUniqueWordsToAllWordsExtractor(WordCounterBuffer wordCounterBuffer) {
        this.wordCounterBuffer = wordCounterBuffer;
    }
    @Override
    public Double extract(Article article) {
        int allWords = wordCounterBuffer.getAllWords(article);
        int uniqueWords = wordCounterBuffer.getUniqueWords(article);

        return (double) uniqueWords / allWords;
    }
}