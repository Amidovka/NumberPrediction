import au.com.bytecode.opencsv.CSVReader;
import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;
import org.apache.commons.math3.stat.regression.SimpleRegression;

import java.io.*;
import java.util.List;

/**
 * Created by amid on 10.03.14.
 */

public class NumberPrediction {

    /**
     * Parsing data from the file.
     *
     * @return List of Strings
     * @throws IOException
     */
    public static List<String[]> parsedData() throws IOException {
        FileReader reader = new FileReader(new File("compTimeStat.csv").getAbsolutePath());
        CSVReader csvReader = new CSVReader(reader);

        return csvReader.readAll();
    }

    /**
     * Returns a value with the certain index.
     * The value is predicted using changeable auxiliary matrix (window).
     * Parameters of variable x are chosen to define relations.
     *
     * @param window    size of a helping "window" matrix
     * @param xParam    x parameters to analyse
     * @return          predicted value
     * @throws IOException
     */

    public static double[] predictWithOptions(int window, int[] xParam) throws IOException{

        List<String[]> Data = NumberPrediction.parsedData();

        int numOfRows = Data.size();
        int numOfCol = Data.get(0).length;

        double[][] xData = new double[numOfRows][numOfCol-1];
        double[] yData = new double[numOfRows];

        //making matrix from data read
        for(int i = 0; i < numOfRows; i++){
            for (int j = 0; j < numOfCol - 1; j++) {
                xData[i][j] = Double.parseDouble(Data.get(i)[j]);
            }
            yData[i] = Double.parseDouble(Data.get(i)[numOfCol - 1]);
        }

        //making xSample matrix with certain x parameters
        double[][] xSample = new double[xData.length][xParam.length];
        double[] ySample = new double[yData.length];

        for (int i = 0; i < xData.length; i++) {
            for (int j = 0; j < xParam.length; j++) {
                xSample[i][j] = xData[i][xParam[j]];
            }
            ySample[i] = yData[i];
        }

        int dataSize = xSample.length;
        int N = dataSize - window;

        //creating window matrix
        double[][] xWindow = new double[window][xParam.length];
        double[] yWindow = new double[window];

        OLSMultipleLinearRegression regression = new OLSMultipleLinearRegression();

        //cycle for predicting using window
        double[] yPredicted = new double[N];
        for (int n = 1; n < N+1; n++){
            //filling window matrix
            for (int i = 0; i < window; i++){
                xWindow[i] = xSample[i+n-1];
                yWindow[i] = ySample[i+n-1];
            }
            //using previous predicted value
            if (n != 1){
                yWindow[window-1] = yPredicted[n-2];
            }
            regression.newSampleData(yWindow, xWindow);
            double[] b = regression.estimateRegressionParameters();
            double[] u = regression.estimateResiduals();
            yPredicted[n-1] = regressionFunctionSingle(xSample[window+n-1], b, u[window-1]);
            //System.out.println("Predicted value Y #" + (window+n) + ": " + yPredicted[n-1]);
        }
        return yPredicted;
    }

    /**
     * Function returns single predicted value of Y for certain row index i.
     * Y(i) = X(i)*b + u(i)
     *
     * @param regressors              X parameters of a row with index i
     * @param regressionParameters    regression parameters we got from regression
     * @param residual                residuals we got from regression
     * @return                        regression function
     */
    public static double regressionFunctionSingle(double[] regressors, double[] regressionParameters, double residual){

        double y = 0;
        for (int j = 1; j < regressionParameters.length; j++){
            y += regressors[j-1]*regressionParameters[j];
        }   y += regressionParameters[0] + residual;
        return y;
    }

    /**
     * Function returns predicted values of Y vector in type of array of double.
     * Y = X*b + u
     *
     * @param regressors               X parameters of data matrix
     * @param regressionParameters     regression parameters we got from regression
     * @param residuals                residuals we got from regression
     * @return                         regression function
     */
    public static double[] regressionFunction(double[][] regressors, double[] regressionParameters, double[] residuals){

        double[] y = new double[regressors.length];
        for (int i = 0; i < regressors.length; i++){
            for (int j = 1; j < regressionParameters.length; j++){
                y[i] += regressors[i][j-1]* regressionParameters[j];
            }   y[i] += regressionParameters[0] + residuals[i];

        }
        return y;
    }

    public static double predictNextDouble(double[] timeSerie){

        SimpleRegression regression = new SimpleRegression();
        double[] x = new double[timeSerie.length];
        double[] yData = new double[timeSerie.length];
        int period = definePeriod(timeSerie);

        //reading data
        for (int i = 0; i < period; i++){
            x[i] = i+1;
            yData[i] = timeSerie[i];
            regression.addData(x[i], yData[i]);
        }

       //If timeSerie has one period, next value is predicted using regression.
        if (period == timeSerie.length){
            return regression.predict(period+1);
        }  else{

            //If timeSerie has more than one period, next value is derived from the data read.
            if (((timeSerie.length) % period) == 0) {
                return yData[0];
            }  else
                return yData[timeSerie.length % period];
                }
        }

    //defining period of timeSerie
    public static int definePeriod(double[] timeSerie){

        double max = timeSerie[0];
        int maxInd = 0;

        //cycle for finding maximum value and it's index in timeSerie
        for (int i = 0; (i < timeSerie.length) && (timeSerie[i] >= max); i++){
                max = timeSerie[i];
                maxInd = i;
            }
        //period is a max value index + 1
        return maxInd+1;
    }
}
