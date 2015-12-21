package com.bp.prediction;

import com.bp.prediction.model.ExponentialMA;
import com.bp.prediction.model.LinearWeightedMA;
import com.bp.prediction.model.SimpleMovingAverage;
import com.bp.prediction.ui.GraphVisualization;
import com.bp.prediction.data.CsvDataReader;
import com.bp.prediction.predictor.NumberPredictor;

public class Main {

    public static void main(String[] args) {

        /**
         * Choosing X parameters from range (1..8) to use in analysis
         * E.g. if input is {3, 6, 7} it means, that
         * only X3, X6 and X7 parameters are chosen and will
         * be used to create X matrix with explanatory variables
         */
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

        /**
         * creating predictions from certain index startingIndex
         * to see the difference in real time-series and predicted time-series
         */

        int startingIndex = 200; //approximately the middle point of time-series data (387 items)
        int window = 10;
        double[] windowData = new double[window];

        //filling windowData with values
        System.arraycopy(yData, startingIndex-window, windowData, 0, window);

        double[] simplePredictions = new double[yData.length];
        double[] weightedPredictions = new double[yData.length];
        double[] exponentialPredictions = new double[yData.length];
        System.arraycopy(yData, 0, simplePredictions, 0, startingIndex);
        System.arraycopy(yData, 0, weightedPredictions, 0, startingIndex);
        System.arraycopy(yData, 0, exponentialPredictions, 0, startingIndex);

        SimpleMovingAverage sma = new SimpleMovingAverage(windowData, window);
        LinearWeightedMA lwma = new LinearWeightedMA(windowData, window);
        ExponentialMA ema = new ExponentialMA(windowData, window);

        for (int i = startingIndex; i < yData.length; i++) {
            simplePredictions[i] = sma.getNextPrediction();
            weightedPredictions[i] = lwma.getNextPrediction();
            exponentialPredictions[i] = ema.getNextPrediction();
            sma.update(yData[i], null);
            lwma.update(yData[i], null);
            ema.update(yData[i], null);
        }

        GraphVisualization chart = new GraphVisualization("Simple Moving Average Visualization");
        chart.setRealData(yData);
        chart.setEstimatedData(simplePredictions);
        chart.draw();
        chart.saveGraphAsImage();

        GraphVisualization chart1 = new GraphVisualization("Weighted Moving Average Visualization");
        chart1.setRealData(yData);
        chart1.setEstimatedData(weightedPredictions);
        chart1.draw();
        chart1.saveGraphAsImage();

        GraphVisualization chart2 = new GraphVisualization("Exponential Moving Average Visualization");
        chart2.setRealData(yData);
        chart2.setEstimatedData(exponentialPredictions);
        chart2.draw();
        chart2.saveGraphAsImage();

        System.out.println();

       /* double[] yValues = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 13, 15, 17, 19};
        double[] yValues1 = {6, 3, 2};
        LinearWeightedMA lwma = new LinearWeightedMA(yValues1, 5);
        System.out.println("lwma prediction: " + lwma.getNextPrediction());
        ExponentialMA ema = new ExponentialMA(yValues, 3);
        System.out.println("ema prediction: " + ema.getNextPrediction());*/

        /**
         * Calling draw() method to draw a graph with
         * two time series. One with actual values - yData
         * Second is regression function values - regressionFunc.
         */
        /*GraphVisualization chart = new GraphVisualization("Time-series Visualization");
        chart.setRealData(yData);
        chart.setEstimatedData(regressionFunc);
        chart.draw();*/
    }
}