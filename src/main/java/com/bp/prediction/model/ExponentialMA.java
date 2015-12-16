package com.bp.prediction.model;

import com.bp.prediction.predictor.Predictor;

public class ExponentialMA implements WeightedMovingAverage, Predictor {

    private double[] yData;
    private int n; //window size

    public ExponentialMA(double[] yData, int n) {
        this.yData = yData;
        this.n = n;
    }

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

    @Override
    public void update(double y, double[] xVector) {

    }
}
