package com.bp.prediction;

import com.bp.prediction.model.AutoregressiveModel;
import com.bp.prediction.model.ExponentialMA;
import com.bp.prediction.model.LinearWeightedMA;
import com.bp.prediction.model.SimpleMovingAverage;
import com.bp.prediction.data.CsvDataReader;
import com.bp.prediction.model.MultipleLinearRegression;
import com.bp.prediction.ui.BarChart;

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
        int[] params = {3, 4};
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

        MultipleLinearRegression multipleLinearRegression = new MultipleLinearRegression(yData, xData, window, predictionStart);
        multipleLinearRegression.calculatePredictions();
        multipleLinearRegression.drawAndSaveGraph();

        AutoregressiveModel arModel = new AutoregressiveModel(yData, 5); //5 - autoregressive model parameter
        arModel.calculatePredictions(predictionStart);
        //arModel.drawAndSaveGraph();

        SimpleMovingAverage sma = new SimpleMovingAverage(yData, window);
        sma.calculatePredictions(predictionStart);
        //sma.drawAndSaveGraph();

        LinearWeightedMA lwma = new LinearWeightedMA(yData, window);
        lwma.calculatePredictions(predictionStart);
        //lwma.drawAndSaveGraph();

        ExponentialMA ema = new ExponentialMA(yData, window);
        ema.calculatePredictions(predictionStart);
        //ema.drawAndSaveGraph();

        /**
         * creating errors bar chart
         */

        int size = 10;
        double[] yDataExamined = new double[size];
        System.arraycopy(yData, yData.length-size, yDataExamined, 0, size);
        double[] multiLinRegPredictionsExamined = new double[size];
        double[] arPredictionsExamined = new double[size];
        double[] simplePredictionsExamined = new double[size];
        double[] weightedPredictionsExamined = new double[size];
        double[] exponentialPredictionsExamined = new double[size];
        System.arraycopy(multipleLinearRegression.getPredictions(), multipleLinearRegression.getPredictions().length-size, multiLinRegPredictionsExamined, 0, size);
        System.arraycopy(arModel.getPredictions(), arModel.getPredictions().length-size, arPredictionsExamined, 0, size);
        System.arraycopy(sma.getPredictions(), sma.getPredictions().length-size, simplePredictionsExamined, 0, size);
        System.arraycopy(lwma.getPredictions(), lwma.getPredictions().length-size, weightedPredictionsExamined, 0, size);
        System.arraycopy(ema.getPredictions(), ema.getPredictions().length-size, exponentialPredictionsExamined, 0, size);

        BarChart barChart = new BarChart("Errors Bar Chart");
        barChart.setMultiLinRegErrors(getErrors(yDataExamined, multiLinRegPredictionsExamined));
        barChart.setAutoRegErrors(getErrors(yDataExamined, arPredictionsExamined));
        barChart.setSimpleMAErrors(getErrors(yDataExamined, simplePredictionsExamined));
        barChart.setLinWMAErrors(getErrors(yDataExamined, weightedPredictionsExamined));
        barChart.setExpMAErrors(getErrors(yDataExamined, exponentialPredictionsExamined));
        barChart.draw();
        barChart.saveChartAsImage();
    }
}