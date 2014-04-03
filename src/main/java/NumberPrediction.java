import org.apache.commons.math3.stat.regression.SimpleRegression;

/**
 * Created by amid on 10.03.14.
 */

public class NumberPrediction {

    public static double predictNextDouble(double[] timeSerie){

        SimpleRegression regrese = new SimpleRegression();
        double[] x = new double[timeSerie.length];
        for (int i = 0; i<timeSerie.length; i++) {
            x[i] = i+1;
            regrese.addData(x[i], timeSerie[i]);
        }

        double output = regrese.predict(timeSerie.length + 1);
        return output;
    }


    /*
    public static void main(String[] args){

        SimpleRegression regrese = new SimpleRegression();
        double[] x = {1,2,3,4,5,6};
        double[] y = {1, 2, 3, 1, 2, 3};

        for (int i = 0; i<x.length; i++) {
            regrese.addData(x[i], y[i]);
        }

        System.out.println("Predicted value is " + regrese.predict(6) + " +- " + regrese.getMeanSquareError());
    }
    */

}
