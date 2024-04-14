package org.example.project1.extractor;

import org.example.project1.extractor.extractors.*;

public class ExtractorFactory {

    public ExtractorFactory() {
    }

    public static Extractor<?> createExtractor(ExtractorType extractorType, WordCounterBuffer wordCounterBuffer){
        switch (extractorType) {
            case MOST_USED_CURRENCY -> {
                return new MostUsedCurrencyExtractor(wordCounterBuffer);
            }
            case MOST_USED_COUNTRY -> {
                return new MostUsedCountryExtractor(wordCounterBuffer);
            }
            case MOST_USED_CITY -> {
                return new MostUsedCityExtractor(wordCounterBuffer);
            }
            case MOST_USED_SE -> {
                return new MostUsedSeExtractor(wordCounterBuffer);
            }
            case MOST_USED_CONTINENT -> {
                return new MostUsedContinentExtractor(wordCounterBuffer);
            }
            case SUM_OF_WORDS_BEGINNING_WITH_CAPITAL_LETTER -> {
                return new SumOfWordsBeginningWithCapitalLetterExtractor(wordCounterBuffer);
            }
            case SUM_OF_UNIQUE_WORDS -> {
                return new SumOfUniqueWordsExtractor(wordCounterBuffer);
            }
            case SUM_OF_COMMON_WORDS -> {
                return new SumOfCommonWordsExtractor(wordCounterBuffer);
            }
            case PROPORTION_UNIQUE_WORDS_TO_ALL_WORDS -> {
                return new ProportionUniqueWordsToAllWordsExtractor(wordCounterBuffer);
            }
            case PROPORTION_UNIQUE_WORDS_TO_COMMON_WORDS -> {
                return new ProportionUniqueWordsToCommonWordsExtractor(wordCounterBuffer);
            }
            default -> throw new IllegalArgumentException("Extractor type not supported");
        }
    }
}