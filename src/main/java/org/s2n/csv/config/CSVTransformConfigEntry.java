package org.s2n.csv.config;

import java.util.List;

public record CSVTransformConfigEntry(
        List<String> fromColumnNames,
        String toColumnName,
        CSVType toColumnType,
        List<String> fromColumnFormat,
        String toColumnFormat) {
}
