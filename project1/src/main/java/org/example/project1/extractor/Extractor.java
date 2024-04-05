package org.example.project1.extractor;

import org.example.project1.util.Article;

public interface Extractor<T> {
    T extract(Article article);
}
