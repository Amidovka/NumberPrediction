import com.amidovka.numberprediction.ReadData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
  * Created by amid on 26.05.14.
  */

public class ComparePredictions {

    static int[] a;
    static int k, n;
    static ArrayList<int[]> A = new ArrayList<int[]>();
    static ArrayList<double[]> yPred = new ArrayList<double[]>();

    /**
     *  Expression for adjusted R-squared statistic
     *
     R2adj = 1 - [SSR (n - 1)] / [SSTO (n - p)]
     SSR is the sum of squared residuals
     SSTO is the total sum of squares
     n is the number of observations
     p is the number of parameters estimated (including the intercept)
     */
    public static double adjustedRSquared() throws IOException {

        double R;
        List<String[]> Data = ReadData.parsedData();
        int numOfRows = Data.size();
        int n = Data.get(0).length;
        int win = 25;
        int numOfComb = permutate(0,0).size();
        double[] yData = new double[numOfRows];

        //real Y values
        for(int i = 0; i < numOfRows; i++){
            yData[i] = Double.parseDouble(Data.get(i)[n - 1]);
        }

        for (k = 1; k <= n; k++) {
            a = new int[k];
            permutate(0, 0);
        }

        ArrayList<int[]> series = new ArrayList<int[]>();
        for (int i = 0; i < A.size(); i++) {
            series.add(A.get(i));
        }

        for (int i = 0; i < numOfComb; i++){
            yPred.add(NumberPrediction.predictWithOptions(win, series.get(i)));
        }

        return 0;
    }

    //trida main na zkouseni
    public static void main(String[] args) throws IOException {

        int win = 25;

        List<String[]> Data = ReadData.parsedData();
        n = Data.get(0).length;

        for (k = 1; k <= n; k++) {
            a = new int[k];
            permutate(0, 0);
        }

        int numOfComb = permutate(0,0).size();

        ArrayList<int[]> series = new ArrayList<int[]>();
        for (int i = 0; i < A.size(); i++) {
            series.add(A.get(i));
        }

        //vypis vsech permutace
        for (int i = 0; i < series.size(); i++) {
            for (int j = 0; j < series.get(i).length; j++) {
                System.out.print((series.get(i))[j] + " ");
            }
            System.out.println();
        }

        //size of series array
        System.out.println(numOfComb);

        //vypis vsech predikci podle pomoci permutace
        // ERROR: java.lang.ArrayIndexOutOfBoundsException: 8
        for (int i = 0; i < numOfComb; i++){
            yPred.add(NumberPrediction.predictWithOptions(win, series.get(i)));
        }

        }

    /**
     * Cause permutations for x parameters.
     * Number of permutations (2^x)-1
     *
     * @param pos
     * @param maxUsed
     * @return             list of integer arrays of combinations
     */
        public static ArrayList<int[]> permutate(int pos, int maxUsed){

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
}










