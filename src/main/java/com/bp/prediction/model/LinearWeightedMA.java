package com.bp.prediction.model;

import com.bp.prediction.predictor.Predictor;

public class LinearWeightedMA implements WeightedMovingAverage, Predictor {

    private double[] yData;
    private int n; //window size

    public LinearWeightedMA(double[] yData, int n) {
        this.yData = yData;
        this.n = n;
    }

    @Override
    public double getNextPrediction() {
        double sum = 0;
        if (yData == null || yData.length == 0 || yData.length < n) {
            throw new IllegalArgumentException("Input data is empty or there are less values than window size!");
        }

        if (n >= 1) {
            //create weights
            double[] weights = new double[n];
            double denominator = n*(n+1)/2;
            for (int i = 0; i < n; i++) {
                weights[i] = (i+1)/denominator;
            }

            for (int i = 0; i < n; i++) {
                sum += yData[yData.length - 1 - i]*weights[n - 1 - i];
            }
        } else {
            throw new IllegalArgumentException("Window size must be a positive number greater than one!");
        }

        return sum;
    }

    @Override
    public void update(double y, double[] xVector) {

    }
}
