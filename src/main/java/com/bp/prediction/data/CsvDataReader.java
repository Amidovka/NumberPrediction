package com.bp.prediction.data;

import au.com.bytecode.opencsv.CSVReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * Created by burdind on 26.10.2015.
 */
public class CsvDataReader implements DataReader {

    private List<String[]> data;
    private double[][] xData;
    private double[] yData;
    private int[] xParams;

    /**
     * Reads, parses .csv file and saves the data from
     * file to list of string arrays.
     * @return List of string arrays
     */
    public List<String[]> parseData() {
        FileReader reader = null;
        CSVReader csvReader = null;

        try {
            reader = new FileReader(new File("Data.csv").getAbsolutePath());
            csvReader = new CSVReader(reader);
            this.setData(csvReader.readAll());
        } catch (FileNotFoundException e) {
            System.out.println("Cannot find file to process! " + e);;
        } catch (IOException e) {
            System.out.println("Cannot find file to process! " + e);
        }
        return this.getData();
    }

    /**
     * Creates two matrices: xData with x values and
     * yData with y values.
     */
    public void createMatrix() {
        int numOfRows = this.getData().size();
        int numOfCol = this.getData().get(0).length;
        int[] xParameters = this.getxParams();
        double[][] xMatrix = new double[numOfRows][numOfCol];
        double[] yMatrix = new double[numOfRows];

        for (int i = 0; i < numOfRows; i++) {
            for (int j = 0; j < xParameters.length; j++) {
                xMatrix[i][j] = Double.parseDouble(data.get(i)[xParameters[j]-1]);
            }
            yMatrix[i] = Double.parseDouble(data.get(i)[numOfCol - 1]);
        }
        this.setxData(xMatrix);
        this.setyData(yMatrix);
    }

    public double[][] getxData() {
        return xData;
    }

    public void setxData(double[][] xData) {
        this.xData = xData;
    }

    public double[] getyData() {
        return yData;
    }

    public void setyData(double[] yData) {
        this.yData = yData;
    }

    public List<String[]> getData() {
        return data;
    }

    public void setData(List<String[]> data) {
        this.data = data;
    }

    public int[] getxParams() {
        return xParams;
    }

    public void setxParams(int[] xParams) {
        this.xParams = xParams;
    }
}
