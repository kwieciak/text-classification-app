package org.example.project1.extractor.extractors;

import org.example.project1.extractor.Extractor;
import org.example.project1.article.Article;
import org.example.project1.extractor.WordCounterBuffer;

public class MostUsedCurrencyExtractor implements Extractor<String> {
    private WordCounterBuffer wordCounterBuffer;
    public MostUsedCurrencyExtractor(WordCounterBuffer wordCounterBuffer) {
        this.wordCounterBuffer = wordCounterBuffer;
    }
    @Override
    public String extract(Article article) {
        String text = wordCounterBuffer.getText(article);
        return extractStringFeature(text, "project1/src/main/resources/org/example/project1/extractors/currencies.csv");
    }
}