package org.example.project1;

import org.example.project1.util.ArticleReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException {
        //ArticleReader articleReader = new ArticleReader("project1/data/reut2-001.sgm");
        //articleReader.readArticles();
        CsvReader csvReader = new CsvReader();
        Map<String, List<String>>  dd = csvReader.readCsv("project1/src/main/resources/org/example/project1/extractors/cities.csv");
        //System.out.println(CsvReader.readCsv("project1/src/main/resources/org/example/project1/extractors/currencies.csv"));
        System.out.println(dd);

    }
}
