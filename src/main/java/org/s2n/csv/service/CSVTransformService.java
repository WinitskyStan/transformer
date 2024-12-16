package org.s2n.csv.service;

import org.s2n.csv.config.CSVTransformConfig;
import org.s2n.csv.config.CSVTransformConfigEntry;
import org.s2n.csv.exceptions.TransformCsvRowException;
import java.text.ParseException;
import java.util.Map;
import java.util.stream.Collectors;

import static org.s2n.csv.service.CSVTransformUtils.*;


public class CSVTransformService {
    private final CSVTransformConfig csvTransformConfig;

    public CSVTransformService(CSVTransformConfig csvTransformConfig)  {
        this.csvTransformConfig = csvTransformConfig;
    }

    /**
     * Map a row returned from CSVProcessor according to defined json config.
     * Returns a Map<String, Object> of header to Object, where Object is String, Integer, BigDecimal, LocalDate, etc
     * @param fromCsvLine
     * @return
     */
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

}
