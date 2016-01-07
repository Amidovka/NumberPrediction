package com.bp.prediction;

import com.bp.prediction.model.AutoregressiveModel;
import com.bp.prediction.model.ExponentialMA;
import com.bp.prediction.model.LinearWeightedMA;
import com.bp.prediction.model.SimpleMovingAverage;
import com.bp.prediction.data.CsvDataReader;
import com.bp.prediction.model.MultipleLinearRegression;

public class Main {

    static double[] getErrors(double[] realValues, double[] estimatedValues) {
        double[] errors = new double[realValues.length];
        for (int i = 0; i < realValues.length; i++) {
            errors[i] = realValues[i] - estimatedValues[i];
        }
        return errors;
    }

    public static void main(String[] args) {

        /**
         * Choosing X parameters from range (1..8) to use in analysis.
         * E.g. if input is {3, 6, 7} it means, that
         * only X3, X6 and X7 parameters are chosen and will
         * be used to create X matrix with explanatory variables.
         */
        //reading, parsing data according to given X parameters
        CsvDataReader dataReader = new CsvDataReader();
        dataReader.parseData("resources/Data.csv");
        int[] params = {3};
        //creating matrices with X and Y values
        dataReader.createMatrix(params);
        double[][] xData = dataReader.getXData();
        double[] yData = dataReader.getYData();

        /**
         * creating predictions from certain index predictionStart
         * to see the difference in real time-series and predicted time-series
         */

        int predictionStart = 200;  //approximately the middle point of time-series data (387 items)
        int window = 10;            //window size

        MultipleLinearRegression predictor = new MultipleLinearRegression(yData, xData, window, predictionStart);
        predictor.calculatePredictions();
        predictor.drawAndSaveGraph();

        AutoregressiveModel arModel = new AutoregressiveModel(yData, 5); //5 - autoregressive model parameter
        arModel.calculatePredictions(predictionStart);
        arModel.drawAndSaveGraph();

        SimpleMovingAverage sma = new SimpleMovingAverage(yData, window);
        sma.calculatePredictions(predictionStart);
        sma.drawAndSaveGraph();

        LinearWeightedMA lwma = new LinearWeightedMA(yData, window);
        lwma.calculatePredictions(predictionStart);
        lwma.drawAndSaveGraph();

        ExponentialMA ema = new ExponentialMA(yData, window);
        ema.calculatePredictions(predictionStart);
        ema.drawAndSaveGraph();

        /**
         * creating errors bar chart.
         */

        /*double[] yDataExamined = new double[30];
        System.arraycopy(yData, yData.length-30, yDataExamined, 0, 30);
        double[] simplePredictionsExamined = new double[30];
        double[] weightedPredictionsExamined = new double[30];
        double[] exponentialPredictionsExamined = new double[30];
        System.arraycopy(simplePredictions, simplePredictions.length-30, simplePredictionsExamined, 0, 30);
        System.arraycopy(weightedPredictions, weightedPredictions.length-30, weightedPredictionsExamined, 0, 30);
        System.arraycopy(exponentialPredictions, exponentialPredictions.length-30, exponentialPredictionsExamined, 0, 30);

        BarChart barChart = new BarChart("Moving Averages Errors Bar Chart");
        barChart.setSMAErrors(getErrors(yDataExamined, simplePredictionsExamined));
        barChart.setLWMAErrors(getErrors(yDataExamined, weightedPredictionsExamined));
        barChart.setEMAErrors(getErrors(yDataExamined, exponentialPredictionsExamined));
        barChart.draw();
        barChart.saveChartAsImage();*/

       /* double[] yValues = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 13, 15, 17, 19};
        double[] yValues1 = {6, 3, 2};
        LinearWeightedMA lwma = new LinearWeightedMA(yValues1, 5);
        System.out.println("lwma prediction: " + lwma.getNextPrediction());
        ExponentialMA ema = new ExponentialMA(yValues, 3);
        System.out.println("ema prediction: " + ema.getNextPrediction());*/
    }
}