package com.bp.prediction.model;

import com.bp.prediction.predictor.Predictor;

public class ExpWeightedMA implements WeightedMovingAverage, Predictor {

    @Override
    public double getNextPrediction() {
        return 0;
    }

    @Override
    public void update(double y, double[] xVector) {

    }
}
