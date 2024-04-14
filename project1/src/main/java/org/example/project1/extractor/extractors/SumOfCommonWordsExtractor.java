package org.example.project1.extractor.extractors;

import org.example.project1.article.Article;
import org.example.project1.extractor.Extractor;
import org.example.project1.extractor.WordCounterBuffer;

public class SumOfCommonWordsExtractor implements Extractor<Integer> {
    static final int THRESHOLD = 3;         // how many times a word is considered common
    private WordCounterBuffer wordCounterBuffer;

    public SumOfCommonWordsExtractor(WordCounterBuffer wordCounterBuffer) {
        this.wordCounterBuffer = wordCounterBuffer;
    }

    @Override
    public Integer extract(Article article) {
        return wordCounterBuffer.getCommonWords(article, THRESHOLD);
    }
}