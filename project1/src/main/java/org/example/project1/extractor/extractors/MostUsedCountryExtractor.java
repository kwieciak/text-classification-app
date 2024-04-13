package org.example.project1.extractor.extractors;

import org.example.project1.extractor.Extractor;
import org.example.project1.article.Article;
import org.example.project1.extractor.WordCounterBuffer;
import org.example.project1.util.CsvReader;

import java.util.List;
import java.util.Map;

public class MostUsedCountryExtractor implements Extractor<String> {
    private WordCounterBuffer wordCounterBuffer;
    private List<String> featureTerms;

    public MostUsedCountryExtractor(WordCounterBuffer wordCounterBuffer,  List<String> featureTerms) {
        this.wordCounterBuffer = wordCounterBuffer;
        this.featureTerms = featureTerms;
    }
    @Override
    public String extract(Article article) {
        String text = wordCounterBuffer.getText(article);
        return extractStringFeature(text, featureTerms);
    }
}