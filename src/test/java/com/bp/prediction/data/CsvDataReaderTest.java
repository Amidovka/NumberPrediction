package com.bp.prediction.data;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CsvDataReaderTest {

    /**
     * Tests data read and parsed from CSV
     * file is not null.
     */
    @Test
     public void testParseData() {
        CsvDataReader dataReader = new CsvDataReader();
        dataReader.parseData("resources/Data.csv");
        assertNotNull(dataReader.getData());
    }

    /**
     * Tests created X matrix and Y matrix
     * by checking certain values that correspond
     * with initial data read from file.
     */
    @Test
    public void testCreateMatrix() {
        CsvDataReader dataReader = new CsvDataReader();
        dataReader.parseData("resources/Data.csv");
        int dataRowLength = dataReader.getData().get(0).length;
        dataReader.createMatrix(new int[]{1, 3, 5});
        assertEquals(Double.parseDouble(dataReader.getData().get(0)[0]), dataReader.getXData()[0][0], 0);
        assertEquals(Double.parseDouble(dataReader.getData().get(0)[2]), dataReader.getXData()[0][1], 0);
        assertEquals(Double.parseDouble(dataReader.getData().get(0)[4]), dataReader.getXData()[0][2], 0);
        assertEquals(Double.parseDouble(dataReader.getData().get(0)[dataRowLength - 1]), dataReader.getYData()[0], 0);
    }
}