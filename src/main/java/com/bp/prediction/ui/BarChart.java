package com.bp.prediction.ui;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

public class BarChart extends ApplicationFrame {

    private String chartTitle;
    private double[] SMAErrors;
    private double[] LWMAErrors;
    private double[] EMAErrors;

    public BarChart(String chartTitle) {
        super(chartTitle);
        this.chartTitle = chartTitle;
    }

    public void createGraph() {
        JFreeChart barChart = ChartFactory.createBarChart(
                chartTitle,
                "Time",
                "Error values",
                createDataSet(),
                PlotOrientation.VERTICAL,
                true, true, false);

        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(1000, 500));
        setContentPane(chartPanel);
    }
    private CategoryDataset createDataSet() {
        final DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
        for (int i = 0; i < SMAErrors.length; i++) {
            dataSet.addValue(SMAErrors[i], "SMAErrors", i+"");
            dataSet.addValue(LWMAErrors[i], "LWMAErrors", i+"");
            dataSet.addValue(EMAErrors[i], "EMAErrors", i+"");
        }

        return dataSet;
    }

    public void draw(){
        this.createGraph();
        this.pack();
        RefineryUtilities.centerFrameOnScreen(this);
        this.setVisible(true);
    }

    public double[] getSMAErrors() {
        return SMAErrors;
    }

    public void setSMAErrors(double[] SMAErrors) {
        this.SMAErrors = SMAErrors;
    }

    public double[] getLWMAErrors() {
        return LWMAErrors;
    }

    public void setLWMAErrors(double[] LWMAErrors) {
        this.LWMAErrors = LWMAErrors;
    }

    public double[] getEMAErrors() {
        return EMAErrors;
    }

    public void setEMAErrors(double[] EMAErrors) {
        this.EMAErrors = EMAErrors;
    }
}
