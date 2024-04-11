package org.example.project1.extractor.extractors;

import org.example.project1.extractor.Extractor;
import org.example.project1.extractor.ExtractorFactory;
import org.example.project1.extractor.ExtractorType;
import org.example.project1.util.Article;
import java.util.Map;

public class ProportionUniqueWordsToAllWordsExtractor implements Extractor<Double> {
//    private final Extractor<Integer> uniqueWordsExtractor = new SumOfUniqueWordsExtractor();
    private final Extractor<Integer> uniqueWordsExtractor = (Extractor<Integer>) ExtractorFactory.createExtractor(ExtractorType.SUM_OF_UNIQUE_WORDS);

    @Override
    public Double extract(Article article) {
        Map<String, Integer> wordCounter = countWords(article.getText().toLowerCase());
        int allWords = wordCounter.values().stream().mapToInt(Integer::intValue).sum();
        int uniqueWords = uniqueWordsExtractor.extract(article);

        return (double) uniqueWords / allWords;
    }
}