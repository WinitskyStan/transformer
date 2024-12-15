package org.s2n.csv.service;

import org.apache.commons.text.WordUtils;
import org.s2n.csv.config.CSVTransformConfig;
import org.s2n.csv.config.CSVTransformConfigEntry;
import org.s2n.csv.exceptions.TransformCsvRowException;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
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
        try {
            return Map.entry(config.toColumnName(),
                    switch (config.toColumnType()) {
                        case STRING -> parseString(config, fromCsvLine);
                        case INTEGER -> parseInt(config, fromCsvLine);
                        case DECIMAL -> parseDecimal(config, fromCsvLine);
                        case DATE -> parseDate(config, fromCsvLine);
                        case FIXED -> config.toColumnFormat();
                    }
            );
        } catch ( ParseException | RuntimeException e) {
            throw new TransformCsvRowException(e);
        }
    }

    private static int parseInt(CSVTransformConfigEntry config, Map<String, String> fromCsvLine) {

        var value = fromCsvLine.get(config.fromColumnNames().get(0));
        var format = config.fromColumnFormat().get(0);

        if (!value
                .matches(format)) {
            throw new TransformCsvRowException("Invalid int format");
        }

        return Integer.parseInt(value);
    }

    private static String parseString(CSVTransformConfigEntry config, Map<String, String> fromCsvLine) {

        var value = fromCsvLine.get(config.fromColumnNames().get(0));
        var format = config.fromColumnFormat().get(0);

        if (!value
                .matches(format)) {
            throw new TransformCsvRowException("Invalid string format");
        }

        if ("proper".equals(config.toColumnFormat()) ) {
            return WordUtils.capitalizeFully(value);
        } else {
            return value;
        }

    }

    private static BigDecimal parseDecimal(CSVTransformConfigEntry config, Map<String, String> fromCsvLine) throws ParseException {

        var decimalFormat = new DecimalFormat(config.fromColumnFormat().get(0));
        decimalFormat.setParseBigDecimal(true);

        return (BigDecimal) decimalFormat.parse(fromCsvLine.get(config.fromColumnNames().get(0)));
    }

    private LocalDate parseDate(CSVTransformConfigEntry config, Map<String, String> fromCsvLine) {

        var dateFormat = String.join("-", config.fromColumnFormat());
        var dateString = String.join("-", config.fromColumnNames().stream().map(s -> fromCsvLine.get(s)).collect(Collectors.toList()));

        return LocalDate.parse(dateString, DateTimeFormatter.ofPattern(dateFormat));

    }

}
