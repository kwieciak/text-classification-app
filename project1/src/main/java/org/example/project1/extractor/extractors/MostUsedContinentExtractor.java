package org.example.project1.extractor.extractors;

import org.example.project1.extractor.Extractor;
import org.example.project1.article.Article;
import org.example.project1.extractor.WordCounterBuffer;
import org.example.project1.extractor.WordDictionaries;

public class MostUsedContinentExtractor implements Extractor<String> {
    private final WordCounterBuffer wordCounterBuffer;
    public MostUsedContinentExtractor(WordCounterBuffer wordCounterBuffer) {
        this.wordCounterBuffer = wordCounterBuffer;
    }
    @Override
    public String extract(Article article) {
        String text = wordCounterBuffer.getText(article);
        return extractStringFeature(text, WordDictionaries.getContinentsDictionary());
    }
}