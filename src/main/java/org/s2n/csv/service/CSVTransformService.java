package org.s2n.csv.service;

import org.s2n.csv.config.CSVTransformConfig;
import org.s2n.csv.config.CSVTransformConfigEntry;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.stream.Collectors;

public class CSVTransformService {
    private final CSVTransformConfig csvTransformConfig;

    public CSVTransformService(CSVTransformConfig csvTransformConfig)  {
        this.csvTransformConfig = csvTransformConfig;
    }

    public Map<String, Object> transformCsvLine(Map<String, String> fromCsvLine) {
        return csvTransformConfig.entries().stream()
                .map(e -> transformCell(e, fromCsvLine))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    protected Map.Entry<String, Object> transformCell(CSVTransformConfigEntry config, Map<String, String> fromCsvLine) {
        return Map.entry(config.toColumnName(),
            switch (config.toColumnType()) {
                case STRING -> fromCsvLine.get(config.fromColumnNames().get(0));
                case INTEGER -> Integer.parseInt(fromCsvLine.get(config.fromColumnNames().get(0)));
                case DECIMAL -> new BigDecimal(fromCsvLine.get(config.fromColumnNames().get(0)));
                case DATE -> parseDate(config, fromCsvLine);
                case FIXED -> config.toColumnFormat();
            }
        );
    }

    private LocalDate parseDate(CSVTransformConfigEntry config, Map<String, String> fromCsvLine) {
        String dateFormat = String.join("-", config.fromColumnFormat());
        String dateString = String.join("-", config.fromColumnNames().stream().map(s -> fromCsvLine.get(s)).collect(Collectors.toList()));
        return LocalDate.parse(dateString, DateTimeFormatter.ofPattern(dateFormat));
    }

}
