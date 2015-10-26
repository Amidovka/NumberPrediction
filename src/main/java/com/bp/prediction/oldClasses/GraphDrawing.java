package com.bp.prediction.oldClasses;

import java.awt.Color;
import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.*;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.RefineryUtilities;

public class GraphDrawing extends ApplicationFrame {

    private static final long serialVersionUID = 1L;
    int[] params = {1, 2, 3, 4, 5, 6, 7, 8, 9};

    {
        // set a theme using the new shadow generator feature
        ChartFactory.setChartTheme(new StandardChartTheme("JFree/Shadow", true));
    }

    public GraphDrawing(String title) throws IOException {
        super(title);
        ChartPanel chartPanel = (ChartPanel) createPanel();
        chartPanel.setPreferredSize(new java.awt.Dimension(1000, 500));
        setContentPane(chartPanel);
    }

    /**
     * Creates a chart.
     *
     * @param dataset  a dataset.
     * @return A chart.
     */
    private static JFreeChart createChart(XYDataset dataset) {

        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                "Computation Time Values",  // title
                "Time",                // x-axis label
                "Computation Time",    // y-axis label
                dataset,               // data
                true,                  // create legend?
                true,                  // generate tooltips?
                false                  // generate URLs?
        );

        chart.setBackgroundPaint(Color.white);

        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);
        plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
        plot.setDomainCrosshairVisible(true);
        plot.setRangeCrosshairVisible(true);

        DateAxis axis = (DateAxis) plot.getDomainAxis();
        axis.setDateFormatOverride(new SimpleDateFormat());

        return chart;
    }

    /**
     * Creates a dataset, consisting of two series of monthly data.
     *
     * @return The dataset.
     */
    private static XYDataset createDataset(int[] params) throws IOException {

        TimeSeries empiricalData = new TimeSeries("Empirická data");
        NumberPrediction prediction = new NumberPrediction();
        prediction.fillMatrix(params);
        int size = prediction.numOfRows;

        for (int k = 0; k < size; k++){
            empiricalData.add(new Millisecond(k*50, 57, 46, 8, 30, 3, 2014), prediction.yData[k]);
        }

        double[] regresfunc = NumberPrediction.regressionFunc;
        TimeSeries regression = new TimeSeries("Regresní přímka");
        for (int k = 0; k < size; k++){
            regression.add(new Millisecond(k*50, 57, 46, 8, 30, 3, 2014), regresfunc[k]);
        }

        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(empiricalData);
        dataset.addSeries(regression);
        return dataset;
    }

    /**
     * Creates a panel for the graph.
     *
     * @return A panel.
     */
    public JPanel createPanel() throws IOException {
        JFreeChart chart = createChart(createDataset(params));
        ChartPanel panel = new ChartPanel(chart);
        panel.setFillZoomRectangle(true);
        panel.setMouseWheelEnabled(true);
        return panel;
    }

    public static double draw() throws IOException {

        GraphDrawing graph = new GraphDrawing("Time Series Prediction");
        graph.pack();
        RefineryUtilities.centerFrameOnScreen(graph);
        graph.setVisible(true);

        return 0;
    }
}

