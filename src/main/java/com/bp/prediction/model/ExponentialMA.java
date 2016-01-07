package com.bp.prediction.model;

import com.bp.prediction.ui.GraphVisualization;

public class ExponentialMA implements WeightedMovingAverage, Predictor {

    private double[] yData;
    private int n; //window size
    private double[] predictions;

    public ExponentialMA(double[] yData, int n) {
        this.yData = yData;
        this.n = n;
    }

    /**
     * Predicts next value in time-series by
     * calculating exponentially weighted average of n
     * previous values.
     * @return predicted value
     */
    @Override
    public double getNextPrediction() {
        double nextValue;
        double sum = 0;
        if (yData == null || yData.length == 0 || yData.length < n) {
            throw new IllegalArgumentException("Input data is empty or there are less values than the window size!");
        }

        if (n >= 2) {
            double alpha = 2.0/(n+1);
            for (int i = 0; i < n-1; i++) {
                sum += alpha*Math.pow((1-alpha), i)*yData[yData.length - 1 - i];
            }
            nextValue = sum + Math.pow((1-alpha), n-1)*yData[yData.length - n];
        } else {
            throw new IllegalArgumentException("Window size must be equal or greater than 2!");
        }
        return nextValue;
    }

    /**
     * Adds new value to the Y data array.
     * X values are not used.
     * @param y new value
     * @param xVector new x values
     */
    @Override
    public void update(double y, double[] xVector) {
        double[] updatedYData = new double[yData.length + 1];
        System.arraycopy(yData, 0, updatedYData, 0, yData.length);
        updatedYData[yData.length] = y;
        this.setYData(updatedYData);
    }

    /**
     * Calculates predictions for the values after the
     * prediction start index.
     * @param predictionStart index to start predict from
     */
    public void calculatePredictions(int predictionStart) {
        double[] windowData = new double[n];
        double[] realData = yData;
        if (yData.length > predictionStart) {
            predictions = new double[yData.length - predictionStart];
        } else {
            throw new IllegalArgumentException("Prediction start point must be in a range of real data values!");
        }

        //filling windowData with values
        System.arraycopy(yData, predictionStart-n, windowData, 0, n);
        this.setYData(windowData);

        for (int i = 0; i < predictions.length; i++) {
            predictions[i] = this.getNextPrediction();
            this.update(realData[predictionStart + i], null);
        }
        this.setYData(realData);
    }

    /**
     * Creates a graph with two real and estimated
     * time series and saves it as image in resources folder.
     */
    public void drawAndSaveGraph() {
        String title = "Exponential Moving Average Model Visualization " + "EMA(" + n + ")";
        GraphVisualization chart = new GraphVisualization(title, yData, predictions);
        chart.draw();
        chart.saveGraphAsImage();
    }

    public double[] getYData() {
        return yData;
    }

    public void setYData(double[] yData) {
        this.yData = yData;
    }

    public double[] getPredictions() {
        return predictions;
    }
}
