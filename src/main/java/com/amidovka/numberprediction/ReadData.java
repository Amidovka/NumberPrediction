package com.amidovka.numberprediction;

import au.com.bytecode.opencsv.CSVReader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * Created by amid on 22.05.14.
 */
public class ReadData {

    /**
     * Parsing data from the file.
     *
     * @return List of Strings
     * @throws java.io.IOException
     */
    public static List<String[]> parsedData() throws IOException {
        FileReader reader = new FileReader(new File("compTimeStat.csv").getAbsolutePath());
        CSVReader csvReader = new CSVReader(reader);

        return csvReader.readAll();
    }
}
