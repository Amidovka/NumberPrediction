package com.bp.prediction.model;

import com.bp.prediction.predictor.Predictor;

public class SimpleMovingAverage implements Predictor {

    private double[] yData;
    private int n; //window size

    public SimpleMovingAverage(double[] yData, int n) {
        this.yData = yData;
        this.n = n;
    }

    /**
     * Predicts next value in time series by
     * calculating arithmetic average of n
     * previous values.
     * @return predicted value
     */
    @Override
    public double getNextPrediction() {
        double sum = 0;

        if (yData == null || yData.length == 0 || yData.length < n) {
            throw new IllegalArgumentException("Input data is empty or there are less values than window size!");
        }

        if (n >= 1) {
            for (int i = 0; i < n; i++) {
                sum += yData[yData.length - 1 - i];
            }
        } else {
            throw new IllegalArgumentException("Window size must be a positive number greater than one!");
        }

        return sum/n;
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

    public double[] getYData() {
        return yData;
    }

    public void setYData(double[] yData) {
        this.yData = yData;
    }
}
