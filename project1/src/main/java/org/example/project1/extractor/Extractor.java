package org.example.project1.extractor;

import org.example.project1.article.Article;

public interface Extractor<T> {
    T extract(Article article);
}
