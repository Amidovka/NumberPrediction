package com.bp.prediction.data;

public interface DataReader {

    void parseData(String pathToFile);
    void createMatrix(int[] xParams);
}
