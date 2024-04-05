package org.example.project1;

import org.example.project1.extractor.Extractor;
import org.example.project1.extractor.ExtractorFactory;
import org.example.project1.extractor.ExtractorType;
import org.example.project1.extractor.extractors.MostUsedCityExtractor;
import org.example.project1.util.Article;
import org.example.project1.util.ArticleReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException {
        ArticleReader articleReader = new ArticleReader("project1/data/reut2-001.sgm");
        List<Article> articles = articleReader.readArticles();
        System.out.println(articles.get(0).getText());

        //CsvReader csvReader = new CsvReader();
        //Map<String, List<String>>  dd = csvReader.readCsv("project1/src/main/resources/org/example/project1/extractors/cities.csv");
        //System.out.println(CsvReader.readCsv("project1/src/main/resources/org/example/project1/extractors/currencies.csv"));
        //System.out.println(dd);
        Extractor<?> extractor = ExtractorFactory.createExtractor(ExtractorType.MOST_USED_CITY);
        String d = (String) extractor.extract(articles.get(0));
        System.out.println(d);
    }
}
