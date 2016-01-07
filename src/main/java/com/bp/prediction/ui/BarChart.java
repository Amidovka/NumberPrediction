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
    private double[] multiLinRegErrors;
    private double[] autoRegErrors;
    private double[] simpleMAErrors;
    private double[] linWMAErrors;
    private double[] expMAErrors;

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
        for (int i = 0; i < multiLinRegErrors.length; i++) {
            dataSet.addValue(multiLinRegErrors[i], "Multiple Linear Regression Errors", ""+i);
            dataSet.addValue(autoRegErrors[i], "Autoregressive Model Errors", ""+i);
            dataSet.addValue(simpleMAErrors[i], "Simple MA Errors", ""+i);
            dataSet.addValue(linWMAErrors[i], "Linear Weighted MA Errors", ""+i);
            dataSet.addValue(expMAErrors[i], "Exponential MA Errors", ""+i);
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
        int width = 1000;
        int height = 500;
        try {
            File errorsBarChart = new File("resources/" + this.chartTitle + ".jpeg");
            ChartUtilities.saveChartAsJPEG(errorsBarChart, createChart(), width, height);
        } catch (IOException e) {
            System.err.println("Failed to save graph as image.");
        }
    }

    public void setMultiLinRegErrors(double[] multiLinRegErrors) {
        this.multiLinRegErrors = multiLinRegErrors;
    }

    public void setAutoRegErrors(double[] autoRegErrors) {
        this.autoRegErrors = autoRegErrors;
    }

    public void setSimpleMAErrors(double[] simpleMAErrors) {
        this.simpleMAErrors = simpleMAErrors;
    }

    public void setLinWMAErrors(double[] linWMAErrors) {
        this.linWMAErrors = linWMAErrors;
    }

    public void setExpMAErrors(double[] expMAErrors) {
        this.expMAErrors = expMAErrors;
    }
}
