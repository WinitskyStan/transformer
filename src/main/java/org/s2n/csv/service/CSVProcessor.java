package org.s2n.csv.service;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.s2n.csv.exceptions.FileLoadException;
import org.s2n.csv.exceptions.ReadCsvRowException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * Reads a CSV file and returns a Map of header names to String values line by line
 * for processing Large files
 */
public class CSVProcessor {

    private final CSVReader csvReader;
    private final String[] headers;

    public CSVProcessor(String filePath) {
        try {
            this.csvReader = new CSVReader(new FileReader(filePath));
            this.headers = csvReader.readNext();
        } catch (IOException | CsvValidationException e) {
            throw new FileLoadException(e);
        }
    }

    /**
     * Reads next line from the CSV file as a Map of header names to String values, similar to a JSON structure
     * @return
     */
    public Map<String, String> nextLine() {
        try {
            String[] line = csvReader.readNext();

            if (line == null) {
                return null;
            }

            HashMap<String, String> headerToValueForLine = new HashMap<>();
            int minIndex = line.length < headers.length ? line.length : headers.length;

            for (int i = 0; i < minIndex; i++) {
                headerToValueForLine.put(headers[i], line[i]);
            }

            return headerToValueForLine;
        } catch (IOException | CsvValidationException e) {
            throw new ReadCsvRowException(e);
        }
    }

}
