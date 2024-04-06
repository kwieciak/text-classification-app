package org.example.project1.util;

import lombok.Getter;

import java.util.List;


public class Article {
    @Getter
    private List<String> countriesList;
    @Getter
    private List<String> topics;
    @Getter
    private String title;
    @Getter
    private String text;

    public Article(List<String> countriesList, String country, List<String> topics, String title, String text) {
        this.countriesList = countriesList;
        this.topics = topics;
        this.title = title;
        this.text = text;
    }

    public String getCountry() {
        return countriesList.get(0);
    }
}

