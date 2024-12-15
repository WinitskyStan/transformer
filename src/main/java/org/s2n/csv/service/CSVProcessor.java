package org.s2n.csv.service;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CSVProcessor {

    private final CSVReader csvReader;
    private final String[] headers;

    public CSVProcessor(String filePath) throws IOException, CsvValidationException {

        this.csvReader = new CSVReader(new FileReader(filePath));
        this.headers = csvReader.readNext();

    }

    public Map<String, String> nextLine() throws CsvValidationException, IOException {
        String[] line = csvReader.readNext();

        if (line == null) {
            return null;
        }

        HashMap<String, String> headerToValueForLine = new HashMap<>();
        int minIndex = line.length < headers.length ? line.length : headers.length;

        for (int i = 0 ; i < minIndex; i++) {
            headerToValueForLine.put(headers[i], line[i]);
        }

        return headerToValueForLine;
    }

}