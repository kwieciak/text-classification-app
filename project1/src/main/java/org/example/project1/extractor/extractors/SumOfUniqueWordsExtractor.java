package org.example.project1.extractor.extractors;

import org.example.project1.extractor.Extractor;
import org.example.project1.util.Article;
import java.util.Map;

public class SumOfUniqueWordsExtractor implements Extractor<Integer> {

    @Override
    public Integer extract(Article article) {
        return countUniqueWords(article.getText().toLowerCase());
    }
}