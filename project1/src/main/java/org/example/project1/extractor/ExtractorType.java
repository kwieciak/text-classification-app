package org.example.project1.extractor;

import org.example.project1.extractor.extractors.*;

public enum ExtractorType {
    MOST_USED_COUNTRY(new MostUsedCountryExtractor()),
    MOST_USED_CURRENCY(new MostUsedCurrencyExtractor()),
    MOST_USED_CITY(new MostUsedCityExtractor()),
    MOST_USED_SE(new MostUsedSeExtractor()), //Stock Exchange
    MOST_USED_CONTINENT(new MostUsedContinentExtractor()),
    WORDS_DEPENDENCIES(new WordsDependenciesExtractor());
//    SUM_OF_WORDS_BEGINNING_WITH_CAPITAL_LETTER(),
//    SUM_OF_UNIQUE_WORDS(),
//    SUM_OF_COMMON_WORDS(),
//    PROPORTION_UNIQUE_WORDS_TO_ALL_WORDS(),
//    PROPORTION_UNIQUE_WORDS_TO_COMMON_WORDS();

    private final Extractor<?> extractor;

    ExtractorType(Extractor<?> extractor) {
        this.extractor = extractor;
    }

    public Extractor<?> getExtractor() {
        return extractor;
    }
}
