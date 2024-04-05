package org.example.project1.extractor.extractors;

import org.example.project1.extractor.Extractor;
import org.example.project1.article.Article;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SumOfWordsBeginningWithCapitalLetterExtractor implements Extractor<Integer> {

    @Override
    public Integer extract(Article article) {
        String text = article.getText();
        Pattern pattern = Pattern.compile("\\b[A-Z][a-z]*\\b");
        Matcher matcher = pattern.matcher(text);
        int count = 0;
        while (matcher.find()) {
            count++;
        }
        return count;
    }
}