package com.bp.prediction.statsModel;

import com.bp.prediction.predictor.Predictor;

/**
 * Created by burdind on 28.11.2015.
 */
public class SimpleMovingAverage implements Predictor{

    private double[] yData;
    private int n; //window size

    public SimpleMovingAverage(double[] yData) {
        this.yData = yData;
    }

    /**
     * Predicts next value in time series by
     * calculating arithmetic average of n
     * previous values.
     * @return predicted value
     */
    public double getNextPrediction() {
        double sum = 0;
        double[] yData = this.getyData();
        int n = this.getN();

        if (yData.equals(null) || yData.length == 0 || yData.length < n) {
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

    public void update(double y, double[] xVector) {

    }

    public double[] getyData() {
        return yData;
    }

    public void setyData(double[] yData) {
        this.yData = yData;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }
}
