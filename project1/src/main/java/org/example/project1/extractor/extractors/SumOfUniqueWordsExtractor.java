package org.example.project1.extractor.extractors;

import org.example.project1.extractor.Extractor;
import org.example.project1.article.Article;
import org.example.project1.extractor.WordCounterBuffer;

import java.util.List;

public class SumOfUniqueWordsExtractor implements Extractor<Integer> {
    private WordCounterBuffer wordCounterBuffer;
    public SumOfUniqueWordsExtractor(WordCounterBuffer wordCounterBuffer) {
        this.wordCounterBuffer = wordCounterBuffer;
    }
    @Override
    public Integer extract(Article article) {
        return wordCounterBuffer.getUniqueWords(article);
    }
}