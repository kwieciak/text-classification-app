package org.example.project1.extractor.extractors;

import org.example.project1.extractor.Extractor;
import org.example.project1.article.Article;



public class MostUsedSeExtractor implements Extractor<String> {
    @Override
    public String extract(Article article) {
        return extractStringFeature(article, "project1/src/main/resources/org/example/project1/extractors/se.csv");
    }
}