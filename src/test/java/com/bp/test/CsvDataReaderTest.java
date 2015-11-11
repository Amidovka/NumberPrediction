package com.bp.test;

import com.bp.prediction.data.CsvDataReader;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


/**
 * Created by burdind on 8.11.2015.
 */
public class CsvDataReaderTest {

    /**
     * Tests data read and parsed from CSV
     * file is not null.
     */
    @Test
     public void testParseData() {
        CsvDataReader dataReader = new CsvDataReader();
        dataReader.parseData();
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
        dataReader.parseData();
        int dataRowLength = dataReader.getData().get(0).length;
        dataReader.setxParams(new int[]{1, 3, 5});
        dataReader.createMatrix();
        assertEquals(Double.parseDouble(dataReader.getData().get(0)[0]), dataReader.getxData()[0][0], 0);
        assertEquals(Double.parseDouble(dataReader.getData().get(0)[2]), dataReader.getxData()[0][1], 0);
        assertEquals(Double.parseDouble(dataReader.getData().get(0)[4]), dataReader.getxData()[0][2], 0);
        assertEquals(Double.parseDouble(dataReader.getData().get(0)[dataRowLength - 1]), dataReader.getyData()[0], 0);
    }
}