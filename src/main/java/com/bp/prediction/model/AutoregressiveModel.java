package com.bp.prediction.model;

import com.bp.prediction.ui.GraphVisualization;
import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;

public class AutoregressiveModel implements Predictor{

    private int p;
    private double[] yData;
    private double[] ARParams;
    private double[] predictions;

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
    public double[] estimateARParams() {
        //creating matrix of Y(t-i) values
        double[][] xData = new double[yData.length - p][p];
        for (int j = 0; j < p; j++) {
            for (int i = 0; i < yData.length - p; i++) {
                xData[i][j] = yData[p - 1 - j + i];
            }
        }

        //creating array of Y values in AR model
        double[] yDataAR = new double[yData.length - p];
        for (int i = 0; i < yDataAR.length; i++) {
            yDataAR[i] = yData[i + p];
        }

        OLSMultipleLinearRegression regression = new OLSMultipleLinearRegression();
        regression.newSampleData(yDataAR, xData);
        this.setARParams(regression.estimateRegressionParameters());
        return ARParams;
    }

    /**
     * Predicts next value for time series
     * using auto regression.
     * @return predicted value
     */
    @Override
    public double getNextPrediction() {
        double prediction = 0;
        for (int i = 1; i < this.estimateARParams().length; i++) {
            prediction += this.estimateARParams()[i]*yData[yData.length-i];
        }
        prediction += this.estimateARParams()[0];
        return prediction;
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

    /**
     * Calculates predictions for the values after the
     * prediction start index.
     * @param predictionStart index to start predict from
     */
    public void calculatePredictions(int predictionStart) {
        double[] windowData = new double[predictionStart];
        double[] realData = yData;
        if (yData.length > predictionStart) {
            predictions = new double[yData.length - predictionStart];
        } else {
            throw new IllegalArgumentException("Prediction start point must be in a range of real data values!");
        }
        //filling windowData with values
        System.arraycopy(yData, 0, windowData, 0, predictionStart);
        this.setYData(windowData);

        for (int i = 0; i < predictions.length; i++) {
            predictions[i] = this.getNextPrediction();
            this.update(realData[predictionStart + i], null);
        }
        //setting real data array to the initial array
        this.setYData(realData);
    }

    /**
     * Creates a graph with two real and estimated
     * time series and saves it as image in resources folder.
     */
    public void drawAndSaveGraph() {
        String title = "Autoregressive Model Visualization " + "AR(" + p + ")";
        GraphVisualization chart = new GraphVisualization(title, yData, predictions);
        chart.draw();
        chart.saveGraphAsImage();
    }


    public double[] getYData() {
        return yData;
    }

    public void setYData(double[] yData) {
        this.yData = yData;
    }

    public double[] getARParams() {
        return ARParams;
    }

    public void setARParams(double[] ARParams) {
        this.ARParams = ARParams;
    }

    public double[] getPredictions() {
        return predictions;
    }
}
