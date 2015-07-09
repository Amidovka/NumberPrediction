import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;
import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NumberPrediction {

    double[][] xData;
    double[] yData;
    int numOfRows;
    int numOfCol;
    static double[] regressionFunc;

    double bestR2;
    int bestCombinationIndex;

    static int n, k;
    static int[] a;
    static ArrayList<int[]> combinations = new ArrayList<int[]>();

    public static void main(String[] args) throws IOException {

        int[] xParams = {1, 2, 3, 4, 5, 6, 7, 8};

        NumberPrediction prediction = new NumberPrediction();
        prediction.fillMatrix(xParams);

        double[][] correlationMatrix;
        PearsonsCorrelation correlation = new PearsonsCorrelation();
        correlation.computeCorrelationMatrix(prediction.xData);
        correlationMatrix = correlation.computeCorrelationMatrix(prediction.xData).getData();

        for (int i = 0; i < 8; i++){
            System.out.print("x"+(i+1));
            for (int j = 0; j <= i; j++){
                System.out.printf(" " + "%.4f", correlationMatrix[i][j]);
            }
            System.out.println();
        }
        for (int i = 1; i < 10; i++){
            System.out.print("    " + "x"+i);
        }
        System.out.println();

        ArrayList<int[]> list;
        list = prediction.getCombinations();
        double[] R2;
        R2 = prediction.getRSquaredStatistics(list);
        System.out.println();

        System.out.println("Metoda výběru nejlepší podmnožiny: ");
        System.out.print("Nejlepší koeficient determinace = ");
        System.out.printf("%.4f", prediction.bestR2);
        System.out.println();
        //System.out.println("Nejlepší index kombinace vysvětlujících proměnných = " + prediction.bestCombinationIndex);

        System.out.println("Vysvětlující proměnné: ");
        for (int i = 0; i < list.get(prediction.bestCombinationIndex).length; i++){
            System.out.print("x"+list.get(prediction.bestCombinationIndex)[i] + " ");
        }
        System.out.println();
        System.out.println();

        System.out.println("Určení koeficientů podle korelační matice: ");
        int[] bestXParams = {3, 4};
        System.out.println("Nejlepší vysvětlující proměnné podle korelační matice: " + "x_3, " + "x_4");

        double[] regParams = new double[3];

        System.out.println("Regresní koeficienty nejlepších vysvětlujících proměnných: ");
        for (int i = 0; i < bestXParams.length+1; i++){
            regParams[i] = prediction.regressionParameters(bestXParams)[i];
            System.out.print("b_"+i+" = " + prediction.regressionParameters(bestXParams)[i] + " ");
        }

        regressionFunc = new double[prediction.numOfRows];
        for (int i = 0; i < prediction.numOfRows; i++) {
            regressionFunc[i] = regParams[0] + (i+1)*(regParams[1]+regParams[2]);
        }

        GraphDrawing.draw();
    }

    /**
     *
     * @return  List of String from data read
     */
    public List<String[]> createMatrix() {

        List<String[]> Data = null;

        try {
            Data = ReadData.parsedData();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return Data;
    }

    /**
     * creates separate matrix of X params
     * and vector of y values
     *
     * @param xParameters
     */
    void fillMatrix(int[] xParameters) {

        List<String[]> matrix;
        NumberPrediction prediction = new NumberPrediction();
        matrix = prediction.createMatrix();

        numOfRows = matrix.size();
        numOfCol = matrix.get(0).length;

        xData = new double[numOfRows][xParameters.length];
        yData = new double[numOfRows];

        for (int i = 0; i < numOfRows; i++) {
            for (int j = 0; j < xParameters.length; j++) {
                xData[i][j] = Double.parseDouble(matrix.get(i)[xParameters[j]-1]);
            }
            yData[i] = Double.parseDouble(matrix.get(i)[numOfCol - 1]);
        }
    }

    /**
     * defines the best R2 statistics
     * and index of X params combination with
     * this R2 statistics
     *
     * @param list
     * @return  double[] of all R2 statistics
     */
    double[] getRSquaredStatistics(ArrayList<int[]> list){

        OLSMultipleLinearRegression regression = new OLSMultipleLinearRegression();
        NumberPrediction prediction = new NumberPrediction();
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
    }

    /**
     *
     * @param bestParameters
     * @return  regression parameters
     */
    double[] regressionParameters(int[] bestParameters){

        NumberPrediction prediction = new NumberPrediction();
        prediction.fillMatrix(bestParameters);
        OLSMultipleLinearRegression regression = new OLSMultipleLinearRegression();
        regression.newSampleData(prediction.yData, prediction.xData);
        double[] params = regression.estimateRegressionParameters();

        return params;
    }

    /**
     *
     * @return all the x parameters combinations
     */
    ArrayList<int[]> getCombinations() {

        NumberPrediction prediction = new NumberPrediction();
        n = prediction.createMatrix().get(0).length - 1; //number of all x params
        for (k = 1; k < n+1; k++){
            a = new int[k];
            permutation(0,0);
        }
        return combinations;
    }

    /**
     * Algorithm for permutations
     * without repeats
     *
     * @param pos
     * @param maxUsed
     */
    static void permutation(int pos, int maxUsed) {

        if (pos == k) {
            int[] b = new int[a.length];
            for (int i = 0; i < a.length; i++) {
                b[i] = a[i];
            }
            combinations.add(b);
        } else {
            for(int i = maxUsed+1; i <= n; i++) {
                a[pos] = i;
                permutation(pos+1,i);
            }
        }
    }
}
