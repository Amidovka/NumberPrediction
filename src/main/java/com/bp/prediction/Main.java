package com.bp.prediction;

import com.bp.prediction.ui.GraphVisualization;
import com.bp.prediction.data.CsvDataReader;
import com.bp.prediction.predictor.NumberPredictor;

import java.io.IOException;

/**
 * Created by burdind on 26.10.2015.
 */
public class Main {

    public static double[] yData;
    public static double[] regressionFunc;

    public static void main(String[] args) {

        //reading, parsing data according to given X parameters
        int[] params = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        CsvDataReader dataReader = new CsvDataReader();
        dataReader.setxParams(params);
        dataReader.parseData();

        //creating matrices with X and Y values
        dataReader.createMatrix();
        double[][] xData = dataReader.getxData();
        yData = dataReader.getyData();

        //creating predictor instance
        NumberPredictor predictor = new NumberPredictor();
        //getting regression function parameters
        double[] regParams = predictor.getRegParams(yData, xData);

        System.out.println("Regression parameters: ");
        for (int i = 0; i < regParams.length; i++){
            System.out.print("b_"+i+" = " + regParams[i] + " ");
        }

        //creating time series from regression function values
        regressionFunc = new double[yData.length];
        for (int i = 0; i < regressionFunc.length; i++) {
            for (int j = 1; j < regParams.length; j++) {
                regressionFunc[i] = (regParams[j])*xData[i][j-1];
            }
        }

        try {
            GraphVisualization.draw();
        } catch (IOException e) {
            System.out.println("Cannot draw graph! " + e);
        }

        //checking combinations creating
       /* predictor.createCombinations(params);
        List<int[]> combinations = predictor.getCombinations();
        System.out.println();
        for (int[] list : combinations) {
            for (int i : list) {
                System.out.print(i + ", ");
            }
            System.out.println();
        }*/

    }
}
