package org.example.project1.extractor.extractors;

import org.example.project1.extractor.Extractor;
import org.example.project1.article.Article;
import org.example.project1.extractor.WordCounterBuffer;

import java.util.Map;

public class SumOfWordsBeginningWithCapitalLetterExtractor implements Extractor<Integer> {
    private WordCounterBuffer wordCounterBuffer;
    public SumOfWordsBeginningWithCapitalLetterExtractor(WordCounterBuffer wordCounterBuffer) {
        this.wordCounterBuffer = wordCounterBuffer;
    }

    @Override
    public Integer extract(Article article) {
        Map<String, Integer> wordCounter = wordCounterBuffer.getWordCount(article);
        return (int) wordCounter.keySet().stream()
                .filter(word -> Character.isUpperCase(word.charAt(0)))
                .count();
    }
}