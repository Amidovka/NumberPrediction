import org.apache.commons.math3.stat.regression.SimpleRegression;

/**
 * Created by amid on 10.03.14.
 */

public class NumberPrediction {

    public static void main(String[] args){

        SimpleRegression regrese = new SimpleRegression();
//        double[] x = new double[]{1, 2, 3, 4, 5};
        double[] x = {1,2,3,4,5};
//        double[] y = new double[]{1, 2, 3, 4, 5};
        double[] y = {1, 2, 3, 4, 5};

        for (int i = 0; i<x.length; i++) {
            regrese.addData(x[i], y[i]);
        }

        System.out.println("Predicted value is " + regrese.predict(6) + " +- " + regrese.getMeanSquareError());
    }

    public static double predictNextDouble(double[] timeSerie){
        return 0;
    }
}
