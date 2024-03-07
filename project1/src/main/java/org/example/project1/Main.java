package org.example.project1;

import org.example.project1.util.ArticleReader;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        ArticleReader articleReader = new ArticleReader("project1/data/reut2-001.sgm");
        articleReader.readArticles();
    }
}
