package com.bp.prediction.model;

public interface Predictor {

    double getNextPrediction();
    void update(double y, double[] xVector);
}
