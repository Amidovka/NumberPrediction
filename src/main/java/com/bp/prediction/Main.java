package com.bp.prediction;

import com.bp.prediction.statsModel.AutoregressiveModel;
import com.bp.prediction.statsModel.SimpleMovingAverage;
import com.bp.prediction.ui.GraphVisualization;
import com.bp.prediction.data.CsvDataReader;
import com.bp.prediction.predictor.NumberPredictor;

/**
 * Created by burdind on 26.10.2015.
 */
public class Main {

    public static void main(String[] args) {
        /*
        Choosing X parameters from range (1..8) to use in analysis
        E.g. if input is {3, 6, 7} it means, that
        only X3, X6 and X7 parameters are chosen and will
        be used to create X matrix with explanatory variables*/
        int[] params = {3,4};
        //reading, parsing data according to given X parameters
        CsvDataReader dataReader = new CsvDataReader();
        dataReader.setxParams(params);
        dataReader.parseData();

        //creating matrices with X and Y values
        dataReader.createMatrix();
        double[][] xData = dataReader.getxData();
        double[] yData = dataReader.getyData();

        //creating predictor instance
        NumberPredictor predictor = new NumberPredictor();
        //getting regression function parameters
        double[] regParams = predictor.getMultipleRegParams(yData, xData);

        System.out.println("Regression parameters: ");
        for (int i = 0; i < regParams.length; i++){
            System.out.print("b_"+i+" = " + regParams[i] + " ");
        }

        //creating time series from regression function values
        double[] regressionFunc = new double[yData.length];
        for (int i = 0; i < regressionFunc.length; i++) {
            for (int j = 1; j < regParams.length; j++) {
                regressionFunc[i] = (regParams[j])*xData[i][j-1];
            }
            regressionFunc[i] += regParams[0];
        }

        //example of using SimpleMovingAverage class
        System.out.println();
        SimpleMovingAverage sma = new SimpleMovingAverage(yData);
        sma.setN(5);
        System.out.println("Value predicted by simple moving average using window size 5 is " + sma.getNextPrediction());

        /*
        Calling draw() method to draw a graph with
        two time series. One with actual values - yData.
        Second is regression function values - regressionFunc.
         */
        GraphVisualization chart = new GraphVisualization("Time-series Visualization");
        chart.setRealData(yData);
        chart.setEstimatedData(regressionFunc);
        chart.draw();
        //comment 14
        //<!--comment 4 gitignore-->
    }
}