package org.example.project1.extractor;

import org.example.project1.extractor.extractors.MostUsedCityExtractor;

public enum ExtractorType {
    MOST_USED_COUNTRY(),
    MOST_USED_CURRENCY(),
    MOST_USED_CITY(),
    MOST_USED_SE(), //Stock Exchange
    MOST_USED_CONTINENT(),
    SUM_OF_WORDS_BEGINNING_WITH_CAPITAL_LETTER(),
    SUM_OF_UNIQUE_WORDS(),
    SUM_OF_COMMON_WORDS(),
    PROPORTION_UNIQUE_WORDS_TO_ALL_WORDS(),
    PROPORTION_UNIQUE_WORDS_TO_COMMON_WORDS(),

}
