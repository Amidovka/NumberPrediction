import com.amidovka.numberprediction.ReadData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
  * Created by amid on 26.05.14.
  */

public class ComparePredictions {

    static int win = 25; //window matrix size
    static int[] a;
    static int k, n;
    static ArrayList<int[]> A = new ArrayList<int[]>();



    //Parsing data
    static List<String[]> Data() throws IOException {

        return ReadData.parsedData();
    }

    public static void main(String[] args) throws IOException {


        double r = bestR2();
        System.out.println(r);
        GraphDrawing.params = permutate(0,0).get(bestIndex());
        GraphDrawing.draw();

       /* System.out.println("The best adjusted R-squared statistic: " + bestAdjR2 + " with the x parameters: ");
        for (int j = 0; j < combinations.get(index).length; j++) {
            System.out.print("X" + combinations.get(index)[j] + " ");
        }*/
    }

    //Returns all possible combinations(permutations) of x parameters without repeats
    public static ArrayList<int[]> combinations() throws IOException {

        int numOfCol = Data().get(0).length;
        n = numOfCol - 1;

        ArrayList<int[]> listOfComb = new ArrayList<int[]>();
        for (k = 1; k <= n; k++) {
            a = new int[k];
            permutate(0, 0);
            //listOfComb.add(permutate(0,0));
        }


        for (int i = 0; i < A.size(); i++) {
            listOfComb.add(A.get(i));
        }
        System.out.println("one");
        return listOfComb;
    }

    //Causes permutations for x parameters. Number of permutations (2^x)-1.
    static ArrayList<int[]> permutate(int pos, int maxUsed){

            if (pos == k) {
                int[] b = new int[a.length];
                for (int i = 0; i < a.length; i++) {
                    b[i] = a[i];
                }
                A.add(b);
            } else {
                for (int i = maxUsed + 1; i <= n; i++) {
                    a[pos] = i;
                    permutate(pos + 1, i);
                }
            }

        return A;
    }



    //Finds best X parameters combination based on the adjusted R-squared statistic
    static double[] adjR2() throws IOException {

        int numOfRows = Data().size();
        int numOfCol = Data().get(0).length;
        int numOfComb = combinations().size();
        double[] yData = new double[numOfRows];
        //numOfCombinations = combinations().size();

        ArrayList<int[]> combinations = combinations();
        ArrayList<double[]> yPredicted = new ArrayList<double[]>();

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

    public static double bestR2() throws IOException {

        double[] allR2 = adjR2();
        double bestAdjR2 = allR2[0];
        for (int i = 1; i < allR2.length; i++){
            if (allR2[i] > bestAdjR2) {
                bestAdjR2 = allR2[i];
            }
        }
        System.out.println(bestAdjR2);
        return bestAdjR2;
    }

    static int bestIndex() throws IOException {

        double[] allR2 = adjR2();
        double bestR2 = bestR2();
        int index = 0;
        for (int i = 1; i < allR2.length; i++){
            if (allR2[i] == bestR2) {
                index = i;
            }
        }
        System.out.println(index);
        return index;
    }
}