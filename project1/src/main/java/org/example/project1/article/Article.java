package org.example.project1.article;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


public class Article {
    @Getter
    private List<String> placesList;
    @Getter
    private List<String> topics;
    @Getter
    private String title;
    @Getter
    private String text;
    @Setter
    @Getter
    private List<Object> featuresVector;

    public Article(List<String> placesList, List<String> topics, String title, String text) {
        this.placesList = placesList;
        this.topics = topics;
        this.title = title;
        this.text = text;
    }

}

