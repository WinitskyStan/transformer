package org.s2n.csv.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.s2n.csv.exceptions.ReadCsvRowException;
import java.util.Map;

public class CSVProcessorTest {

    @Test
    public void testCsvProcessor() {
        CSVProcessor csvProcessor = new CSVProcessor("src/test/resources/randomCsvFileWith2lines.csv");
        Map<String, String> csvLine ;
        while ( (csvLine = csvProcessor.nextLine()) != null ) {

            String name = csvLine.get("Name");
            String age = csvLine.get("Age");
            String city = csvLine.get("City");

            System.out.println("Name: " + name + ", Age: " + age + ", City: " + city);

            Assertions.assertTrue(name != null && age != null && city != null);
            Assertions.assertDoesNotThrow(() -> Integer.parseInt(age));

        }
    }

    @Test
    public void testCsvProcessorWithInvalidCsvStructure() {
        CSVProcessor csvProcessor = new CSVProcessor("src/test/resources/sample2error.csv");

        Assertions.assertThrows(ReadCsvRowException.class, () -> csvProcessor.nextLine());
    }

}
