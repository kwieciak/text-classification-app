package org.example.project1.util;

import lombok.Getter;

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
    @Getter
//    private List<Object> featuresVector;

    public Article(List<String> countriesList, List<String> topics, String title, String text) {
        this.placesList = countriesList;
        this.topics = topics;
        this.title = title;
        this.text = text;
    }


//    public void setFeaturesVector(List<Object> featuresVector) {
//        this.featuresVector = featuresVector;
//    }
}

