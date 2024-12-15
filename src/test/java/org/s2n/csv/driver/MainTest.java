package org.s2n.csv.driver;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainTest {

    @Test
    public void testMain() throws Exception {
        Main main = new Main("src/test/resources/csvConfig.json", "src/test/resources/sample1.csv");
        main.transformCsvFile();

        assertEquals(2, main.getTotalLines());
        assertEquals(0, main.getFailedLines());
        assertEquals(2, main.getSuccessfulLines());
        assertEquals(main.getErrors(),  new ArrayList<String>());
    }
}