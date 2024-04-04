package org.example.project1.extractor;

public interface Extractor<T> {
    T extract(String article);
}
