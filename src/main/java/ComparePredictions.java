import com.amidovka.numberprediction.ReadData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
  * Created by amid on 26.05.14.
  */

public class ComparePredictions {

    static int win = 25; //window matrix size
    static int bestIndex; //best index in list of combinations of x params
    static ArrayList<int[]> combinations;

    //Parsing data
    static List<String[]> Data() throws IOException {
        return ReadData.parsedData();
    }

    public static void main(String[] args) throws IOException {

        double R2 = bestR2();
        GraphDrawing.params = combinations.get(bestIndex);

        System.out.println( "By using the x parameters: ");
        for (int j = 0; j < GraphDrawing.params.length; j++) {
            System.out.println("X" + GraphDrawing.params[j] + " ");
        }
        System.out.println("We got the best adjusted R-squared statistic: " + R2);

        GraphDrawing.draw();
    }

    /**
     * Calculates all adjusted R-squared statistics
     * by using list of x parameters combinations
     *
     * @return double[] of adjusted R-squared statistics
     * @throws IOException
     */
    static double[] adjR2() throws IOException {

        int numOfRows = Data().size();
        int numOfCol = Data().get(0).length;
        double[] yData = new double[numOfRows];
        ArrayList<double[]> yPredicted = new ArrayList<double[]>();

        combinations = Combinations.combinations();
        int numOfComb = combinations.size();

        //all possible predictions
        for (int i = 0; i <  numOfComb; i++){
            yPredicted.add(NumberPrediction.predictWithOptions(win, combinations.get(i)));
        }

        //real Y values
        for(int i = 0; i < numOfRows; i++){
            yData[i] = Double.parseDouble(Data().get(i)[numOfCol - 1]);
        }

        /**
         *  Expression for adjusted R-squared statistic
         *
         R2adj = 1 - [SSR (n - 1)] / [SSTO (n - p - 1)]
         SSR is the sum of squared residuals
         SSTO is the total sum of squares
         n is the number of observations
         p is the number of parameters estimated (including the intercept)
         */

        double[] SSR = new double[numOfComb];
        for (int k = 0; k < SSR.length; k++){
            for (int i = 0; i < numOfRows-win; i++) {
                SSR[k] += Math.pow(yData[i+win]- yPredicted.get(k)[i], 2);
            }
        }

        double meanY = 0;
        for (int i = win; i < numOfRows; i++){
            meanY += yData[i]/(numOfRows-win);
        }

        double SSTO = 0;
        for (int i = win; i < numOfRows; i++){
            SSTO += Math.pow((yData[i]-meanY), 2);
        }

        double[] adjR2 = new double[numOfComb];

        for (int i = 0; i < numOfComb; i++) {
            adjR2[i] = 1 - (SSR[i]*((numOfRows - win) - 1))/(SSTO*((numOfRows - win) - numOfCol - 1));
        }

        return adjR2;
    }

    /**
     * Finds the best R-squared statistic by comparing statistics.
     * Defines the best index of combination in combination list.
     *
     * @return the best R-squared statistic
     * @throws IOException
     */
    public static double bestR2() throws IOException {

        double[] allR2 = adjR2();
        double bestAdjR2 = allR2[0];
        for (int i = 1; i < allR2.length; i++){
            if (allR2[i] > bestAdjR2) {
                bestAdjR2 = allR2[i];
                bestIndex = i;
            }
        }
        return bestAdjR2;
    }
}