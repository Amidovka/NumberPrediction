package com.bp.prediction.ui;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import java.io.File;
import java.io.IOException;

public class BarChart extends ApplicationFrame {

    private String chartTitle;
    private double[] SMAErrors;
    private double[] LWMAErrors;
    private double[] EMAErrors;

    public BarChart(String chartTitle) {
        super(chartTitle);
        this.chartTitle = chartTitle;
    }

    public JFreeChart createChart() {
        JFreeChart barChart = ChartFactory.createBarChart(
                chartTitle,
                "Time",
                "Error values",
                createDataSet(),
                PlotOrientation.VERTICAL,
                true, true, false);

        CategoryPlot categoryPlot = barChart.getCategoryPlot();
        BarRenderer br = (BarRenderer) categoryPlot.getRenderer();
        br.setMaximumBarWidth(.2); // set maximum width to 35% of chart

        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(3000, 500));
        setContentPane(chartPanel);

        return barChart;
    }
    private CategoryDataset createDataSet() {
        final DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
        for (int i = 0; i < SMAErrors.length; i++) {
            dataSet.addValue(SMAErrors[i], "Simple MA Errors", ""+i);
            dataSet.addValue(LWMAErrors[i], "Linear Weighted MA Errors", ""+i);
            dataSet.addValue(EMAErrors[i], "Exponential MA Errors", ""+i);
        }

        return dataSet;
    }

    public void draw(){
        this.createChart();
        this.pack();
        RefineryUtilities.centerFrameOnScreen(this);
        this.setVisible(true);
    }

    public void saveChartAsImage() {
        int width = 3000;
        int height = 500;
        try {
            File errorsBarChart = new File("resources/" + this.chartTitle + ".jpeg");
            ChartUtilities.saveChartAsJPEG(errorsBarChart, createChart(), width, height);
        } catch (IOException e) {
            System.err.println("Failed to save graph as image.");
        }
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
