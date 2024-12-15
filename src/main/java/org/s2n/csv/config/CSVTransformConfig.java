package org.s2n.csv.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.s2n.csv.exceptions.InvalidConfigException;

import java.io.File;
import java.io.IOException;
import java.util.List;

public record CSVTransformConfig(List<CSVTransformConfigEntry> entries) {
    public static CSVTransformConfig fromJson(String filePath) {
        try {
            return new ObjectMapper().readValue(new File(filePath), CSVTransformConfig.class);
        } catch (IOException e) {
            throw new InvalidConfigException(e);
        }
    }
}
