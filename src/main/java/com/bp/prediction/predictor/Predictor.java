package com.bp.prediction.predictor;

/**
 * Created by burdind on 26.10.2015.
 */
public interface Predictor {

    double getNextPrediction();
    void update(double y, double[] xVector);
}
