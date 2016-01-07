package com.bp.prediction.data;

import au.com.bytecode.opencsv.CSVReader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class CsvDataReader implements DataReader {

    private List<String[]> data;
    private double[][] xData;
    private double[] yData;

    /**
     * Reads, parses CSV file and saves the data from
     * file to list of string arrays.
     */
    public void parseData(String pathToFile) {
        FileReader reader;
        CSVReader csvReader;

        try {
            reader = new FileReader(new File(pathToFile).getAbsolutePath());
            csvReader = new CSVReader(reader);
            this.setData(csvReader.readAll());
        } catch (IOException e) {
            System.out.println("Cannot find file to process! " + e);
        }
    }

    /**
     * Creates two matrices: xData with x values and
     * yData with y values from initial CSV file.
     */
    public void createMatrix(int[] xParams) {
        int numOfRows = this.getData().size();
        int numOfCol = this.getData().get(0).length;
        double[][] xMatrix = new double[numOfRows][xParams.length];
        double[] yMatrix = new double[numOfRows];

        for (int i = 0; i < numOfRows; i++) {
            for (int j = 0; j < xParams.length; j++) {
                xMatrix[i][j] = Double.parseDouble(data.get(i)[xParams[j]-1]);
            }
            yMatrix[i] = Double.parseDouble(data.get(i)[numOfCol - 1]);
        }
        this.setXData(xMatrix);
        this.setYData(yMatrix);
    }

    public double[][] getXData() {
        return xData;
    }

    public void setXData(double[][] xData) {
        this.xData = xData;
    }

    public double[] getYData() {
        return yData;
    }

    public void setYData(double[] yData) {
        this.yData = yData;
    }

    public List<String[]> getData() {
        return data;
    }

    public void setData(List<String[]> data) {
        this.data = data;
    }
}
