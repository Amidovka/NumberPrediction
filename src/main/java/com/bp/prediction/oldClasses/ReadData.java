package com.bp.prediction.oldClasses;

import au.com.bytecode.opencsv.CSVReader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class ReadData {
    /**
     * Parsing data from the file.
     *
     * @return List of Strings
     * @throws java.io.IOException
     */
    static List<String[]> parsedData() throws IOException {
        FileReader reader = new FileReader(new File("Data.csv").getAbsolutePath());
        CSVReader csvReader = new CSVReader(reader);

        return csvReader.readAll();
    }
}
