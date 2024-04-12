package org.example.project1.util;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class CsvReader {
    public CsvReader() {
    }

    public static Map<String, List<String>> readCsv(String path) {
        Map<String, List<String>> records = new LinkedHashMap<>();
        try (CSVReader csvReader = new CSVReaderBuilder(new FileReader(path))
                .withCSVParser(new CSVParserBuilder()
                        .withSeparator(';')
                        .build())
                .withSkipLines(0)
                .build()) {
            List<String[]> rows = csvReader.readAll();
            for (String[] row : rows) {
                if (row.length == 1) {
                    if (records.isEmpty()) {
                        records.put("values", new ArrayList<>());
                    }
                    records.get("values").add(row[0]);
                } else {
                    if (!records.containsKey(row[0])) {
                        records.put(row[0], new ArrayList<>());
                    }
                    records.get(row[0]).add(row[1]);
                }
            }

        } catch (IOException | CsvException e) {
            throw new RuntimeException(e);
        }
        return sortMap(records);
    }

    private static Map<String, List<String>> sortMap(Map<String, List<String>> map) {
        List<Map.Entry<String, List<String>>> entryList = new ArrayList<>(map.entrySet());
        entryList.sort(Comparator.comparingInt(e -> e.getValue().size()));
        Map<String, List<String>> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<String, List<String>> entry : entryList) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }
}
