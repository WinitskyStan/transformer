package org.s2n.csv.config;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public record CSVTransformConfig ( List<CSVTransformConfigEntry> entries) {
    public static CSVTransformConfig fromJson(String filePath) throws IOException {
        return new ObjectMapper().readValue( new File(filePath), CSVTransformConfig.class);
    }

}
