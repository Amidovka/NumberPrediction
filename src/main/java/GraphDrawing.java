import java.awt.Color;
    import java.io.IOException;
    import java.text.SimpleDateFormat;
    import java.util.List;

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

/**
 * Created by amid on 28.04.14.
 */

public class GraphDrawing extends ApplicationFrame {

    private static final long serialVersionUID = 1L;

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
            "Computional Time Values Prediction",  // title
            "Time",                // x-axis label
            "Computional Time",    // y-axis label
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
        axis.setDateFormatOverride(new SimpleDateFormat("HH:mm:ss.S"));

        return chart;
    }

    /**
     * Creates a dataset, consisting of two series of monthly data.
     *
     * @return The dataset.
     */
    private static XYDataset createDataset() throws IOException {

        TimeSeries predictedSeries = new TimeSeries("Predicted data");
        int win = 25;
        double [] predictedData = NumberPrediction.predictWithOptions(win, new int[]{0, 1, 5});
        int size = predictedData.length;

        for (int i = 0; i < size; i++){
            predictedSeries.add(new Millisecond(i*50, 57, 46, 8, 30, 3, 2014), predictedData[i]);
        }
        TimeSeries realSeries = new TimeSeries("Real data");
        for (int i = win; i < getRealData().length; i++){
            realSeries.add(new Millisecond((i-win)*50, 57, 46, 8, 30, 3, 2014), getRealData()[i]);
        }

        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(predictedSeries);
        dataset.addSeries(realSeries);

        return dataset;
    }

    /**
     * Creates a panel for the graph.
     *
     * @return A panel.
     */
    public static JPanel createPanel() throws IOException {
        JFreeChart chart = createChart(createDataset());
        ChartPanel panel = new ChartPanel(chart);
        panel.setFillZoomRectangle(true);
        panel.setMouseWheelEnabled(true);
        return panel;
    }

    /**
     * Starting point for the demonstration application.
     *
     * @param args  ignored.
     */
    public static void main(String[] args) throws IOException {

        GraphDrawing graph = new GraphDrawing("Time Series Prediction");
        graph.pack();
        RefineryUtilities.centerFrameOnScreen(graph);
        graph.setVisible(true);
    }

    public static double[] getRealData() throws IOException {

        List<String[]> data = NumberPrediction.parsedData();
        int numOfRows = data.size();
        int numOfCol = data.get(0).length;
        double[] yData = new double[numOfRows];

        //making matrix from data read
        for (int i = 0; i < numOfRows; i++) {
            yData[i] = Double.parseDouble(data.get(i)[numOfCol - 1]);
        }
        return yData;
    }
}
