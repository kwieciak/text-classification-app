package org.example.project1.extractor;

import org.example.project1.extractor.extractors.*;
import org.example.project1.util.CsvReader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExtractorFactory {

    private static final Map<ExtractorType, Extractor<?>> extractors = new HashMap<>();
    private static final Map<String, List<String>> featureTerms = new HashMap<>();

    static {
        featureTerms.put("cities", CsvReader.readCsv("project1/src/main/resources/org/example/project1/extractors/cities.csv"));
        featureTerms.put("continents", CsvReader.readCsv("project1/src/main/resources/org/example/project1/extractors/continents.csv"));
        featureTerms.put("countries", CsvReader.readCsv("project1/src/main/resources/org/example/project1/extractors/countries.csv"));
        featureTerms.put("currencies", CsvReader.readCsv("project1/src/main/resources/org/example/project1/extractors/currencies.csv"));
        featureTerms.put("stock_exchanges", CsvReader.readCsv("project1/src/main/resources/org/example/project1/extractors/se.csv"));
    }

    public static Extractor<?> createExtractor(ExtractorType extractorType, WordCounterBuffer wordCounterBuffer){
        if (!extractors.containsKey(extractorType)) {
            switch (extractorType) {
                case MOST_USED_CITY -> {
                    extractors.put(extractorType, new MostUsedCityExtractor(wordCounterBuffer,  featureTerms.get("cities")));
                }
                case MOST_USED_CONTINENT -> {
                    extractors.put(extractorType, new MostUsedContinentExtractor(wordCounterBuffer, featureTerms.get("continents")));
                }
                case MOST_USED_COUNTRY -> {
                    extractors.put(extractorType, new MostUsedCountryExtractor(wordCounterBuffer,  featureTerms.get("countries")));
                }
                case MOST_USED_CURRENCY -> {
                    extractors.put(extractorType, new MostUsedCurrencyExtractor(wordCounterBuffer,  featureTerms.get("currencies")));
                }
                case MOST_USED_SE -> {
                    extractors.put(extractorType, new MostUsedSeExtractor (wordCounterBuffer,  featureTerms.get("stock_exchanges")));
                }
                case SUM_OF_COMMON_WORDS -> {
                    extractors.put(extractorType, new SumOfCommonWordsExtractor(wordCounterBuffer));
                }
                case SUM_OF_UNIQUE_WORDS -> {
                    extractors.put(extractorType, new SumOfUniqueWordsExtractor(wordCounterBuffer));
                }
                case SUM_OF_WORDS_BEGINNING_WITH_CAPITAL_LETTER -> {
                    extractors.put(extractorType, new SumOfWordsBeginningWithCapitalLetterExtractor(wordCounterBuffer));
                }
                case PROPORTION_UNIQUE_WORDS_TO_ALL_WORDS -> {
                    extractors.put(extractorType, new ProportionUniqueWordsToAllWordsExtractor(wordCounterBuffer));
                }
                case PROPORTION_UNIQUE_WORDS_TO_COMMON_WORDS -> {
                    extractors.put(extractorType, new ProportionUniqueWordsToCommonWordsExtractor(wordCounterBuffer));
                }
                default -> throw new IllegalArgumentException("Extractor type not supported");
            }
        }
        return extractors.get(extractorType);
    }
}
