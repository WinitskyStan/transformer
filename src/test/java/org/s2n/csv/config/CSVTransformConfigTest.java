package org.s2n.csv.config;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CSVTransformConfigTest {

    @Test
    public void testCSVTransformConfig() throws Exception {
        CSVTransformConfig config = CSVTransformConfig.fromJson("src/test/resources/csvConfig.json");

        List<CSVTransformConfigEntry> entries = config.entries();


        assertEquals(6 , entries.size());

        // only validate 1 entry, no need to validate them all
        CSVTransformConfigEntry orderDate =
                entries.stream().filter(e -> e.toColumnName().equals("OrderDate")).findFirst().get();

        assertEquals("OrderDate", orderDate.toColumnName());
        assertEquals(CSVType.DATE, orderDate.toColumnType());
        assertEquals(List.of("Year", "Month", "Day"), orderDate.fromColumnNames());
        assertEquals(List.of("yyyy", "MM", "dd"), orderDate.fromColumnFormat());
        assertEquals(null, orderDate.toColumnFormat());


    }

}
