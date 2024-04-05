package org.example.project1.extractor;

public class ExtractorFactory {
    public ExtractorFactory() {
    }
    public static Extractor<?> createExtractor(ExtractorType extractorType){
        return extractorType.getExtractor();
    }
}
