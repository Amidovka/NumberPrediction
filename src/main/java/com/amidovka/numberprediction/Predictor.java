package com.amidovka.numberprediction;

public interface Predictor {

    double getNextPrediction();

    void update(double y, double[] xVector);

    void predictWithOptions(int window, int[] xParam);

}
