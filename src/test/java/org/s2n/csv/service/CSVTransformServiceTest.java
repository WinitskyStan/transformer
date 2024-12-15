package org.s2n.csv.service;

import org.s2n.csv.config.CSVTransformConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CSVTransformServiceTest {

    @Test
    public void testCsvTransformService() throws Exception {
        CSVTransformConfig config = CSVTransformConfig.fromJson("src/test/resources/csvConfig.json");
        CSVProcessor csvProcessor = new CSVProcessor("src/test/resources/sample1.csv");
        CSVTransformService csvTransformService = new CSVTransformService(config);

        Map<String, Object> csvLineOne = csvTransformService.transformCsvLine(csvProcessor.nextLine());

        assertTrue(csvLineOne.get("OrderDate") instanceof LocalDate);
        assertEquals(csvLineOne.get("OrderID"), 1001);
        assertEquals(csvLineOne.get("ProductId"), "ABC123");
        assertEquals(csvLineOne.get("ProductName"), "BLUE WIDGET");
        assertEquals(csvLineOne.get("Unit"), "kg");
        assertEquals(csvLineOne.get("Quantity"), new BigDecimal("10.5"));

        Map<String, Object> csvLineTwo = csvTransformService.transformCsvLine(csvProcessor.nextLine());

        Assertions.assertNull(csvProcessor.nextLine());
    }
}
