package org.example.project1.extractor;

import org.example.project1.extractor.extractors.*;

public enum ExtractorType {
    MOST_USED_COUNTRY(new MostUsedCountryExtractor()),
    MOST_USED_CURRENCY(new MostUsedCurrencyExtractor()),
    MOST_USED_CITY(new MostUsedCityExtractor()),
    MOST_USED_SE(new MostUsedSeExtractor()), //Stock Exchange
    MOST_USED_CONTINENT(new MostUsedContinentExtractor()),
    SUM_OF_WORDS_BEGINNING_WITH_CAPITAL_LETTER(new SumOfWordsBeginningWithCapitalLetterExtractor()),
    SUM_OF_UNIQUE_WORDS(new SumOfUniqueWordsExtractor()),
    SUM_OF_COMMON_WORDS(new SumOfCommonWordsExtractor()),
    PROPORTION_UNIQUE_WORDS_TO_ALL_WORDS(new ProportionUniqueWordsToAllWordsExtractor()),
    PROPORTION_UNIQUE_WORDS_TO_COMMON_WORDS(new ProportionUniqueWordsToCommonWordsExtractor());

    private final Extractor<?> extractor;

    ExtractorType(Extractor<?> extractor) {
        this.extractor = extractor;
    }


    public Extractor<?> getExtractor() {
        return extractor;
    }
}
