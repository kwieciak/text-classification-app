package org.example.project1.article;

import java.util.List;


public class Article {
    private List<String> placesList;
    private List<String> topics;
    private String title;
    private String text;

    public Article(List<String> placesList, List<String> topics, String title, String text) {
        this.placesList = placesList;
        this.topics = topics;
        this.title = title;
        this.text = text;
    }

    public List<String> getPlacesList() {
        return placesList;
    }

    public List<String> getTopics() {
        return topics;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }
}