package org.example.project1.extractor;

import lombok.Getter;
import org.example.project1.util.CsvReader;

import java.util.List;
import java.util.Map;

public class WordDictionaries {
    @Getter
    static Map<String, List<String>> citiesDictionary = CsvReader.readCsv("project1/src/main/resources/org/example/project1/extractors/cities.csv");
    @Getter
    static Map<String, List<String>> countriesDictionary = CsvReader.readCsv("project1/src/main/resources/org/example/project1/extractors/countries.csv");
    @Getter
    static Map<String, List<String>> continentsDictionary = CsvReader.readCsv("project1/src/main/resources/org/example/project1/extractors/continents.csv");
    @Getter
    static Map<String, List<String>> seDictionary = CsvReader.readCsv("project1/src/main/resources/org/example/project1/extractors/se.csv");
    @Getter
    static Map<String, List<String>> currenciesDictionary = CsvReader.readCsv("project1/src/main/resources/org/example/project1/extractors/currencies.csv");

}
