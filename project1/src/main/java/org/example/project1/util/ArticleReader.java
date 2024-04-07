package org.example.project1.util;

import java.io.*;
import java.util.*;

public class ArticleReader {
    private File file;
    private List<Article> articles = new ArrayList<>();
    private StringBuilder buffer = new StringBuilder();
    private String line = null;
    private Set<String> stopwords = new HashSet<>();

    public ArticleReader(String filePath) {
        this.file = new File(filePath);
        loadStopwords();
    }

    private static String[] META_CHARS = {"&", "<", ">", "\"", "'", ""};

    private static String[] META_CHARS_SERIALIZATIONS = {"&amp;", "&lt;", "&gt;", "&quot;", "&apos;", " Reuter\n" +
            "&#3;"};

    public List<Article> readArticles() throws IOException {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));

            while ((line = reader.readLine()) != null) {
                if (line.contains("<REUTERS")) {
                    buffer = new StringBuilder();
                }
                buffer.append(line).append("\n");

                if (line.contains("</REUTERS>")) {
                    String title = extractTagContent(buffer.toString(), "TITLE");
                    String text = extractTagContent(buffer.toString(), "BODY");
                    text = removeStopwords(text);                           // NEW
                    String topics = extractTagContent(buffer.toString(), "TOPICS");
                    String places = extractTagContent(buffer.toString(), "PLACES");
                    List<String> topicsList = extractElementsWithTheSameTag(topics);
                    List<String> placesList = extractElementsWithTheSameTag(places);


                    if (placesList.size() == 1
                            && (placesList.getFirst().equalsIgnoreCase("usa")
                            || placesList.getFirst().equalsIgnoreCase("canada")
                            || placesList.getFirst().equalsIgnoreCase("west-germany")
                            || placesList.getFirst().equalsIgnoreCase("france")
                            || placesList.getFirst().equalsIgnoreCase("japan")
                            || placesList.getFirst().equalsIgnoreCase("uk"))
                            && !text.isEmpty()){
                        Article article = new Article(placesList, topicsList, title, text);
                        articles.add(article);
                    }


                }
            }
            reader.close();
            return articles;
        } catch (IOException e) {
            throw new IOException("Cannot read this file");
        }
    }

    private static String extractTagContent(String articleContent, String tag) {
        String startTag = "<" + tag + ">";
        String endTag = "</" + tag + ">";
        int startIndex = articleContent.indexOf(startTag);
        int endIndex = articleContent.indexOf(endTag);

        if (startIndex != -1 && endIndex != -1) {
            String tmp = articleContent.substring(startIndex + startTag.length(), endIndex);
            for (int i = 0; i < META_CHARS_SERIALIZATIONS.length; i++) {
                tmp = tmp.replaceAll(META_CHARS_SERIALIZATIONS[i], META_CHARS[i]).trim();
            }
            return tmp;
        } else {
            return "";
        }
    }

    private static List<String> extractElementsWithTheSameTag(String line) {
        List<String> elements = new ArrayList<>();
        int startIndex = line.indexOf("<D>");
        int endIndex;

        while (startIndex != -1) {
            endIndex = line.indexOf("</D>", startIndex + 3);
            if (endIndex != -1) {
                String element = line.substring(startIndex + 3, endIndex);
                elements.add(element);
                startIndex = line.indexOf("<D>", endIndex);
            } else {
                break;
            }
        }
        return elements;
    }

    // STOPWORDS

    private  void loadStopwords() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                Objects.requireNonNull(ArticleReader.class.getResourceAsStream("/org/example/project1/stopwords/stopwords.csv"))))) {
            String line;
            while ((line = br.readLine()) != null) {
                stopwords.add(line.toLowerCase());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String removeStopwords(String text) {
        StringBuilder cleanedText = new StringBuilder();
        String[] words = text.split("\\s+");
        for (String word : words) {
            if (!stopwords.contains(word.toLowerCase())) {
                cleanedText.append(word).append(" ");
            }
        }
        return cleanedText.toString().trim();
    }
}
