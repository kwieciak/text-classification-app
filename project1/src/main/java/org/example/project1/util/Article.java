package org.example.project1.util;

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
}
