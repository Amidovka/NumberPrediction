import au.com.bytecode.opencsv.CSVReader;
import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;
//import org.apache.commons.math3.stat.regression.SimpleRegression;

import java.io.*;
import java.util.List;

/**
 * Created by amid on 10.03.14.
 */

public class NumberPrediction {

    public static double[] predictCompTimeStat(double[][] data) throws IOException{

        FileReader reader = new FileReader(new File("compTimeStat.csv").getAbsolutePath());
        CSVReader csvReader = new CSVReader(reader);
        FileReader reader2 = new FileReader(new File("compTimeStat.csv").getAbsolutePath());
        CSVReader csvReader2 = new CSVReader(reader2);

        List<String[]> arrayData = csvReader.readAll();
        String[] stringData = csvReader2.readNext();

        double[][] xData = new double[arrayData.size()][stringData.length-1];
        double[] yData = new double[arrayData.size()];

        for (int i = 0; i < arrayData.size(); i++){
            for (int j = 0; j < stringData.length-1; j++){
                xData[i][j] = Double.parseDouble(stringData[j]);
            }
            yData[i] = Double.parseDouble(stringData[stringData.length-1]);
            stringData = csvReader2.readNext();
        }


        OLSMultipleLinearRegression regression = new OLSMultipleLinearRegression();
        regression.newSampleData(yData, xData);

        double[] u = regression.estimateResiduals();
        double[] b = regression.estimateRegressionParameters();

        System.out.println("Regression parameters:");
        for (int i = 0; i < b.length; i++){
            System.out.println("beta"+(i)+ " = " + b[i]);
        }

        return regressionFunction(data, b, u);
    }

    // regression function Y = X*b + u
    public static double[] regressionFunction(double[][] regressors, double[] regressionParameters, double[] residuals){

        double[] y = new double[regressors.length];
        for (int i = 0; i < regressors.length; i++){
            for (int j = 1; j < regressionParameters.length; j++){
                y[i] += regressors[i][j-1]* regressionParameters[j];
            }   y[i] += regressionParameters[0] + residuals[i];
        }
        return y;
    }
}

    /*
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