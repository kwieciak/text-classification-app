package org.example.project1.extractor;

import org.example.project1.article.Article;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordCounterBuffer {
    private final Map<Article, String> textBuffer;
    private final Map<Article, Integer> allWordsBuffer;
    private final Map<Article, Integer> uniqueWordsBuffer;
    private final Map<Article, Integer> commonWordsBuffer;
    private final Map<Article, Map<String, Integer>> wordCountBuffer;

    public WordCounterBuffer() {
        this.textBuffer = new HashMap<>();
        this.allWordsBuffer = new HashMap<>();
        this.uniqueWordsBuffer = new HashMap<>();
        this.commonWordsBuffer = new HashMap<>();
        this.wordCountBuffer = new HashMap<>();
    }

    public String getText(Article article) {
        return textBuffer.computeIfAbsent(article, Article::getText);
    }

    public Integer getAllWords(Article article) {
        return allWordsBuffer.computeIfAbsent(article, a -> countAllWords(getText(a)));
    }

    public Integer getUniqueWords(Article article) {
        return uniqueWordsBuffer.computeIfAbsent(article, a -> countUniqueWords(getText(a)));
    }

    public Integer getCommonWords(Article article, int threshold) {
        return commonWordsBuffer.computeIfAbsent(article, a -> countCommonWords(getText(a), threshold));
    }

    public Map<String, Integer> getWordCount(Article article) {
        return wordCountBuffer.computeIfAbsent(article, a -> countWords(getText(a)));
    }

    private Map<String, Integer> countWords(String text) {
        Map<String, Integer> wordCounter = new HashMap<>();
        Pattern pattern = Pattern.compile("\\b[a-zA-Z]+\\b");
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            String word = matcher.group();
            wordCounter.put(word, wordCounter.getOrDefault(word, 0) + 1);
        }
        return wordCounter;
    }


    private int countAllWords(String text) {
        return countWords(text).values().stream().mapToInt(Integer::intValue).sum();
    }

    private int countUniqueWords(String text) {
        return (int) countWords(text).values().stream().filter(count -> count == 1).count();
    }

    private int countCommonWords(String text, int threshold) {
        return countWords(text).values().stream().filter(count -> count >= threshold).mapToInt(Integer::intValue).sum();
    }
}
