package com.bp.prediction.predictor;

import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by burdind on 26.10.2015.
 */
public class NumberPredictor implements Predictor{

    private ArrayList<int[]> combinations = new ArrayList<int[]>();

    public double getNextPrediction() {
        return 0;
    }

    public void update(double y, double[] xVector) {

    }

    /**
     * defines the best R2 statistics
     * and index of X params combination with
     * this R2 statistics
     *
     * @param list
     * @return  double[] of all R2 statistics
     */
   /* double[] getRSquaredStatistics(ArrayList<int[]> list){

        OLSMultipleLinearRegression regression = new OLSMultipleLinearRegression();
        com.bp.prediction.oldClasses.NumberPrediction prediction = new com.bp.prediction.oldClasses.NumberPrediction();
        double[] allRSquaredStatistics = new double[list.size()];

        for (int i = 0; i < list.size(); i++){
            prediction.fillMatrix(list.get(i));
            regression.newSampleData(prediction.yData, prediction.xData);
            allRSquaredStatistics[i] = regression.calculateRSquared();
        }

        bestR2 = allRSquaredStatistics[0];
        for (int i = 1; i < allRSquaredStatistics.length; i++){
            if (allRSquaredStatistics[i] > bestR2) {
                bestR2 = allRSquaredStatistics[i];
                bestCombinationIndex = i;
            }
        }

        return allRSquaredStatistics;
    }*/

    /**
     * Gets regression function parameters
     * @return  regression parameters
     */
    public double[] getRegParams(double[] yData, double[][] xData){

        OLSMultipleLinearRegression regression = new OLSMultipleLinearRegression();
        regression.newSampleData(yData, xData);
        double[] regParams = regression.estimateRegressionParameters();

        return regParams;
    }

    /**
     *
     * @return all the x parameters combinations
     */
    public void createCombinations(int[] xParams) {
        int n = xParams.length;
        int k;

        for (k = 1; k < n+1; k++){
            int[] a = new int[k];
            permutation(0, 0, k, n, a, xParams);
        }
    }

    /**
     * Algorithm for permutations without repeats.
     * Number of combintaions = 2^n - 1.
     * n - number of parameters to combinate.
     * @param pos
     * @param inPos initial position in array to begin permutation from
     */
    private void permutation(int pos, int inPos, int k, int n, int[] a, int[] xParams) {

        if (pos == k) {
            int[] b = new int[a.length];
            for (int i = 0; i < a.length; i++) {
                b[i] = a[i];
            }
            this.combinations.add(b);
        } else {
            for(int i = inPos+1; i <= n; i++) {
                a[pos] = xParams[i - 1];
                permutation(pos+1, i, k, n, a, xParams);
            }
        }
    }

    public ArrayList<int[]> getCombinations() {
        return combinations;
    }

    public void setCombinations(ArrayList<int[]> combinations) {
        this.combinations = combinations;
    }
}
