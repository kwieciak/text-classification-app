package org.example.project1.util;

import org.example.project1.extractor.Extractor;
import org.example.project1.extractor.ExtractorFactory;
import org.example.project1.extractor.ExtractorType;

import java.util.ArrayList;
import java.util.List;

public class ArticleFeatures {
    public static void extractFeatures(Article article){
        List<Object> featuresVector = article.getFeaturesVector();
        if(featuresVector == null) {
            featuresVector = new ArrayList<>();
        }
        for (ExtractorType extractorType : ExtractorType.values()) {
            Extractor<?> extractor = ExtractorFactory.createExtractor(extractorType);
            featuresVector.add(extractor.extract(article));
        }
        article.setFeaturesVector(featuresVector);
    }


}
