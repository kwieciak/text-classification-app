package org.example.project1.extractor.extractors;

import org.example.project1.extractor.Extractor;
import org.example.project1.util.Article;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SumOfUniqueWordsExtractor implements Extractor<Integer> {

    @Override
    public Integer extract(Article article) {
        String text = article.getText().toLowerCase();
        Set<String> uniqueWords = new HashSet<>();

        Pattern pattern = Pattern.compile("\\b[a-z]+\\b");
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            String word = matcher.group();
            uniqueWords.add(word);
        }

        return uniqueWords.size();
    }
}