package com.bp.prediction.model;

import com.bp.prediction.ui.GraphVisualization;
import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;
import org.apache.commons.math3.stat.regression.SimpleRegression;

import java.util.ArrayList;

public class MultipleLinearRegression implements Predictor{

    private double[] yData;
    private double[][] multipleXData;
    private double[] simpleXData;
    private int n; //window size
    private int predictionStart;
    private double[] predictions;
    private double[] regParams;
    private ArrayList<int[]> combinations = new ArrayList<>();

    public MultipleLinearRegression(double[] yData, double[][] xData, int window, int predictionStart) {
        this.yData = yData;
        this.multipleXData = xData;
        this.n = window;
        this.predictionStart = predictionStart;
    }

    public MultipleLinearRegression(double[] yData, double[] simpleXData, int window, int predictionStart) {
        this.yData = yData;
        this.simpleXData = simpleXData;
        this.n = window;
        this.predictionStart = predictionStart;
    }

    /**
     * Calculates regression parameters of n explanatory
     * variables (window) and predicts the next value using
     * next explanatory values.
     * @return predicted value
     */
    @Override
    public double getNextPrediction() {
        double prediction = 0;
        double[] windowYData = new double[n];
        double[] initialYData = yData;
        System.arraycopy(initialYData, predictionStart - n, windowYData, 0, n);

        if (multipleXData != null) {
            double[][] windowXData = new double[n][multipleXData[0].length];
            double[][] initialXData = multipleXData;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < multipleXData[0].length; j++) {
                    windowXData[i][j] = multipleXData[predictionStart - n + i][j];
                }
            }
            this.getMultipleRegParams(windowYData, windowXData);
            for (int i = 0; i < initialXData[0].length; i++) {
                prediction += regParams[i+1]*initialXData[predictionStart][i];
            }
            prediction += regParams[0];
        } else if (simpleXData != null) {
            double[] windowXData = new double[n];
            System.arraycopy(simpleXData, predictionStart, windowXData, 0, n);
            this.getSimpleRegParams(windowYData, windowXData);
            prediction += regParams[0] + regParams[1]*(simpleXData[predictionStart] + 1);
        } else {
            throw new IllegalArgumentException("Explanatory data are empty!");
        }
        return prediction;
    }

    /**
     * Increases point to start to predict from
     * on 1, by which the window "moves" forward
     * through the data values.
     */
    public void updateWindow() {
        this.predictionStart += 1;
    }

    /**
     * Calculates predictions of values from the certain
     * point - predictionStart index.
     */
    public void calculatePredictions() {
        predictions = new double[yData.length - predictionStart];
        for (int i = 0; i < predictions.length; i++) {
            predictions[i] = this.getNextPrediction();
            this.updateWindow();
        }
    }

    /**
     * Creates a graph with two real and estimated
     * time series and saves it as image in resources folder.
     */
    public void drawAndSaveGraph() {
        String title = "Multiple Linear Regression Model Visualization " + "MLR(" + n + ")";
        GraphVisualization chart = new GraphVisualization(title, yData, predictions);
        chart.draw();
        chart.saveGraphAsImage();
    }

    @Override
    public void update(double y, double[] xVector) {
    }

    /**
     * defines the best R2 statistics
     * and index of X params combination with
     * this R2 statistics
     *
     * @param list
     * @return  double[] of all R2 statistics
     */
   /* double[] getRSquaredStatistics(ArrayList<int[]> list){

        OLSMultipleLinearRegression regression = new OLSMultipleLinearRegression();
        com.bp.prediction.oldClasses.NumberPrediction prediction = new com.bp.prediction.oldClasses.NumberPrediction();
        double[] allRSquaredStatistics = new double[list.size()];

        for (int i = 0; i < list.size(); i++){
            prediction.fillMatrix(list.get(i));
            regression.newSampleData(prediction.yData, prediction.multipleXData);
            allRSquaredStatistics[i] = regression.calculateRSquared();
        }

        bestR2 = allRSquaredStatistics[0];
        for (int i = 1; i < allRSquaredStatistics.length; i++){
            if (allRSquaredStatistics[i] > bestR2) {
                bestR2 = allRSquaredStatistics[i];
                bestCombinationIndex = i;
            }
        }

        return allRSquaredStatistics;
    }*/

    /**
     * Gets multiple regression function parameters.
     * @param yData dependent variable values
     * @param xData independent variable values
     */
    public void getMultipleRegParams(double[] yData, double[][] xData){
        OLSMultipleLinearRegression regression = new OLSMultipleLinearRegression();
        regression.newSampleData(yData, xData);
        this.setRegParams(regression.estimateRegressionParameters());
    }

    /**
     * Gets regression parameters - b0 (intercept)
     * and b1 - slope.
     * @param yData dependent variable values
     * @param xData independent variable values
     */
    public void getSimpleRegParams(double[] yData, double[] xData) {
        SimpleRegression regression = new SimpleRegression();
        for (int i = 0; i < yData.length; i++) {
            regression.addData(xData[i], yData[i]);
        }
        this.setRegParams(regression.regress().getParameterEstimates());
    }

    /**
     * Creates all X parameters combinations.
     */
    public void createCombinations(int[] xParams) {
        int n = xParams.length;
        int k;

        for (k = 1; k < n+1; k++){
            int[] a = new int[k];
            permutation(0, 0, k, n, a, xParams);
        }
    }

    /**
     * Algorithm for permutations without repeats.
     * Number of combinations = 2^n - 1.
     * n - number of parameters to combinate.
     * @param pos position
     * @param inPos initial position in array to begin permutation from
     */
    private void permutation(int pos, int inPos, int k, int n, int[] a, int[] xParams) {

        if (pos == k) {
            int[] b = new int[a.length];
            for (int i = 0; i < a.length; i++) {
                b[i] = a[i];
            }
            this.combinations.add(b);
        } else {
            for(int i = inPos+1; i <= n; i++) {
                a[pos] = xParams[i - 1];
                permutation(pos+1, i, k, n, a, xParams);
            }
        }
    }

    public double predictNextDouble(double[] numberSeq) {
        return 0;
    }

    public ArrayList<int[]> getCombinations() {
        return combinations;
    }

    public double[] getRegParams() {
        return regParams;
    }

    public void setRegParams(double[] regParams) {
        this.regParams = regParams;
    }

    public double[] getYData() {
        return yData;
    }

    public double[] getPredictions() {
        return predictions;
    }
}