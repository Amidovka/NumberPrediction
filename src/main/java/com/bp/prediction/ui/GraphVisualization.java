package com.bp.prediction.ui;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Minute;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import javax.swing.JPanel;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

public class GraphVisualization extends ApplicationFrame {

    private String chartTitle;
    private double[] realData;
    private double[] estimatedData;

    static {
        //set a theme using the new shadow generator feature
        ChartFactory.setChartTheme(new StandardChartTheme("JFree/Shadow", true));
    }

    /**
     * GraphVisualization class constructor.
     * @param chartTitle chart window title.
     */
    public GraphVisualization(String chartTitle, double[] realData, double[] estimatedData) {
        super(chartTitle);
        this.chartTitle = chartTitle;
        this.realData = realData;
        this.estimatedData = estimatedData;
    }

    /**
     * Creates graph with set width and length of window.
     */
    private void createGraph() {
        ChartPanel chartPanel;
        try {
            chartPanel = (ChartPanel) createPanel();
            chartPanel.setPreferredSize(new java.awt.Dimension(1000, 500));
            setContentPane(chartPanel);
        } catch (IOException e) {
            System.err.println("Cannot create graph!");
        }
    }

    /**
     * Creates ChartPanel instance.
     * Uses JFreeChart. Sets graph zooming,
     * viewing options.
     * @return panel
     * @throws IOException
     */
    private JPanel createPanel() throws IOException {
        JFreeChart chart = createChart();
        ChartPanel panel = new ChartPanel(chart);
        panel.setFillZoomRectangle(true);
        panel.setMouseWheelEnabled(true);
        return panel;
    }

    /**
     * Creates JFreeChart type chart.
     * Sets title, x-axis and y-axis labels.
     * Sets time-series x-axis format to date.
     * Uses data set of time-series.
     * @return chart
     */
    private JFreeChart createChart() {
        JFreeChart xyLineChart = ChartFactory.createTimeSeriesChart(
                chartTitle,
                "Time",                            //x-axis label
                "Computational Time",              //y-axis label
                createDataSet(),                   //data
                true,                              //create legend?
                true,                              //generate tooltips?
                false                              //generate URLs?
        );

        final XYPlot plot = xyLineChart.getXYPlot();
        plot.setDomainCrosshairVisible(true);
        plot.setRangeCrosshairVisible(true);

        DateAxis axis = (DateAxis) plot.getDomainAxis();
        axis.setDateFormatOverride(new SimpleDateFormat("HH:mm:ss"));

        return xyLineChart;
    }

    /**
     * Creates data set of two time-series.
     * Both time-series have time intervals of 5 minutes
     * starting from 8:44:00, 30.3.2014.
     * @return data set of time-series.
     */
    private XYDataset createDataSet() {
        TimeSeries realDataSeries = new TimeSeries("Real Data");
        Minute realTimePeriod = new Minute(44, 8, 30, 3, 2014);

        if (realData.length == 0) {
            throw new IllegalArgumentException("Real data are empty!");
        } else {
            for (double aRealData : realData) {
                realDataSeries.add(realTimePeriod, aRealData);
                realTimePeriod = (Minute) realTimePeriod.next().next().next().next().next();
            }
        }

        TimeSeries estimatedDataSeries = new TimeSeries("Estimated Data");
        Minute estimatedTimePeriod = new Minute(44, 8, 30, 3, 2014);

        //set up time line from prediction start
        int predictionStart = realData.length - estimatedData.length;
        for (int i = 0; i < predictionStart; i++){
            estimatedTimePeriod = (Minute) estimatedTimePeriod.next().next().next().next().next();
        }

        if (estimatedData.length == 0) {
            throw new IllegalArgumentException("Estimated data are empty!");
        } else {
            for (double anEstimatedData : estimatedData) {
                estimatedDataSeries.add(estimatedTimePeriod, anEstimatedData);
                estimatedTimePeriod = (Minute) estimatedTimePeriod.next().next().next().next().next();
            }
        }

        TimeSeriesCollection dataSet = new TimeSeriesCollection();
        dataSet.addSeries(realDataSeries);
        dataSet.addSeries(estimatedDataSeries);
        return dataSet;
    }

    /**
     * Creates a graph with two time-series
     * in a new window.
     */
    public void draw() {
        this.createGraph();
        this.pack();
        RefineryUtilities.centerFrameOnScreen(this);
        this.setVisible(true);
    }

    public void saveGraphAsImage() {
        int width = 1000;
        int height = 500;
        try {
            File timeSeriesGraph = new File("resources/" + this.chartTitle + ".jpeg");
            ChartUtilities.saveChartAsJPEG(timeSeriesGraph, createChart(), width, height);
        } catch (IOException e) {
            System.err.println("Failed to save graph as image.");
        }
    }
}