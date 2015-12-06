package com.bp.prediction.predictor;

public interface Predictor {

    double getNextPrediction();
    void update(double y, double[] xVector);
}
