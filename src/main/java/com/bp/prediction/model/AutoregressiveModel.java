package com.bp.prediction.model;

import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;

public class AutoregressiveModel {

    private int p;
    private double[] yData;

    /**
     * Constructor for autoregressive model class.
     * @param yData examined data
     * @param p order of a model
     */
    public AutoregressiveModel(double[] yData, int p) {
        this.yData = yData;
        this.p = p;
    }

    /**
     * Creates regressors from previous values of examined data.
     * Transforms examined data according to the order of a model.
     * @return array of model parameters.
     */
    public double[] getARParams() {
        //creating matrix of Y(t-i) values
        double[][] xData = new double[this.yData.length - this.p][this.p];
        for (int j = 0; j < this.p; j++) {
            for (int i = 0; i < this.yData.length - this.p; i++) {
                xData[i][j] = this.yData[this.p - 1 - j + i];
            }
        }

        //creating array of Y values in AR model
        double[] yDataAR = new double[this.yData.length - this.p];
        for (int i = 0; i < yDataAR.length; i++) {
            yDataAR[i] = this.yData[i + this.p];
        }

        OLSMultipleLinearRegression regression = new OLSMultipleLinearRegression();
        regression.newSampleData(yDataAR, xData);
        return regression.estimateRegressionParameters();
    }
}
