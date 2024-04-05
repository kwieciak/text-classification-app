package org.example.project1;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Stopwords {
    private static final List<String> STOPWORDS = new ArrayList<>();
    static {
        Path path = Paths.get("src/main/resources/org/example/project1/stopwords/stopwords.csv");
        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line;
            while ((line = br.readLine()) != null) {
                STOPWORDS.add(line.toLowerCase());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static boolean isStopword(String word) {
        return STOPWORDS.contains(word.toLowerCase());
    }
}
