package org.s2n.csv.driver;

import org.s2n.csv.config.CSVTransformConfig;
import org.s2n.csv.exceptions.TransformCsvRowException;
import org.s2n.csv.service.CSVProcessor;
import org.s2n.csv.service.CSVTransformService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {
    private final CSVTransformConfig csvTransformConfig;
    private final CSVTransformService csvTransformService;
    private final CSVProcessor csvProcessor;
    private int totalLines = 0;
    private int failedLines = 0;
    private int successfulLines = 0;
    private List<String> errors = new ArrayList<>();

    public Main(String configFilePath, String csvFilePath) {
        this.csvTransformConfig = CSVTransformConfig.fromJson(configFilePath);
        this.csvTransformService = new CSVTransformService(csvTransformConfig);
        this.csvProcessor = new CSVProcessor(csvFilePath);
    }

    public void transformCsvFile() {
        Map<String, String> line;
        boolean hasMoreLines = true;

        while (hasMoreLines) {
            try {
                hasMoreLines = ((line = csvProcessor.nextLine()) != null);

                if (hasMoreLines) {
                    Map<String, Object> transformedLine = csvTransformService.transformCsvLine(line);
                    System.out.println(transformedLine);
                    successfulLines++;
                    totalLines++;
                }

            } catch (TransformCsvRowException e) {
                failedLines++;
                totalLines++;
                errors.add(e.getMessage());
            }

        }

        System.out.println("Total lines: " + totalLines);
        System.out.println("Failed lines: " + failedLines);
        System.out.println("Successful lines: " + successfulLines);
        System.out.println("Errors: " + errors);
    }

    public static void main(String[] args) throws Exception {
        // just a dummy main method for testing purposes
        new Main("src/test/resources/csvConfig.json", "src/test/resources/sample1.csv").transformCsvFile();
    }

    public int getFailedLines() {
        return failedLines;
    }

    public int getSuccessfulLines() {
        return successfulLines;
    }

    public int getTotalLines() {
        return totalLines;
    }

    public List<String> getErrors() {
        return errors;
    }
}