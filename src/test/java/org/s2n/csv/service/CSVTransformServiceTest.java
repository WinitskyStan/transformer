package org.s2n.csv.service;

import org.s2n.csv.config.CSVTransformConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.s2n.csv.exceptions.TransformCsvRowException;
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

        assertEquals(csvLineOne.get("OrderDate"), LocalDate.of(2024, 1, 15));
        assertEquals(csvLineOne.get("OrderID"), 1001);
        assertEquals(csvLineOne.get("ProductId"), "ABC123");
        assertEquals(csvLineOne.get("ProductName"), "Blue Widget");
        assertEquals(csvLineOne.get("Unit"), "kg");
        assertEquals(csvLineOne.get("Quantity"), new BigDecimal("1010.5"));

        Map<String, Object> csvLineTwo = csvTransformService.transformCsvLine(csvProcessor.nextLine());

        Assertions.assertNull(csvProcessor.nextLine());
    }

    @Test
    public void testCsvWithInvalidData() {

        CSVTransformConfig config = CSVTransformConfig.fromJson("src/test/resources/csvConfig.json");
        CSVProcessor csvProcessor = new CSVProcessor("src/test/resources/sampleManyErrors.csv");
        CSVTransformService csvTransformService = new CSVTransformService(config);

        Assertions.assertNotNull(csvTransformService.transformCsvLine(csvProcessor.nextLine()));


        // invalid order number
        TransformCsvRowException csvValidationException =
                Assertions.assertThrows(TransformCsvRowException.class, () -> csvTransformService.transformCsvLine(csvProcessor.nextLine()));

        assertTrue(csvValidationException.getMessage().contains("Invalid int format"));


        // invalid date
        csvValidationException =
                Assertions.assertThrows(TransformCsvRowException.class, () -> csvTransformService.transformCsvLine(csvProcessor.nextLine()));

        assertTrue(csvValidationException.getMessage().contains("java.time.format.DateTimeParseException"));

        // invalid product number
        csvValidationException =
                Assertions.assertThrows(TransformCsvRowException.class, () -> csvTransformService.transformCsvLine(csvProcessor.nextLine()));

        assertTrue(csvValidationException.getMessage().contains("Invalid string format"));


        // invalid product name
        csvValidationException =
                Assertions.assertThrows(TransformCsvRowException.class, () -> csvTransformService.transformCsvLine(csvProcessor.nextLine()));

        System.out.println(csvValidationException.getMessage());
        assertTrue(csvValidationException.getMessage().contains("Invalid string format"));


        // invalid count
        csvValidationException =
                Assertions.assertThrows(TransformCsvRowException.class, () -> csvTransformService.transformCsvLine(csvProcessor.nextLine()));

        System.out.println(csvValidationException.getMessage());
        assertTrue(csvValidationException.getMessage().contains("Unparseable number"));


    }
}
