    import java.awt.Color;
    import java.io.IOException;
    import java.text.SimpleDateFormat;
    import java.util.List;

    import javax.swing.JPanel;

    import com.amidovka.numberprediction.ReadData;
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
    static int win = 25;
    static int[] params;

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
        //axis.setDateFormatOverride(new SimpleDateFormat("HH:mm:ss.S"));
        axis.setDateFormatOverride(new SimpleDateFormat());

        return chart;
    }

    /**
     * Creates a dataset, consisting of two series of monthly data.
     *
     * @return The dataset.
     */
    private static XYDataset createDataset(int[] params) throws IOException {

        int[] i;
        i = params;
        TimeSeries predictedSeries = new TimeSeries("Predicted data");
        double [] predictedData = NumberPrediction.predictWithOptions(win, i);
        int size = predictedData.length;

        for (int k = 0; k < size; k++){
            predictedSeries.add(new Millisecond(k*50, 57, 46, 8, 30, 3, 2014), predictedData[k]);
        }
        TimeSeries realSeries = new TimeSeries("Real data");
        for (int k = 0; k < getRealData().length; k++){
            realSeries.add(new Millisecond(k*50, 57, 46, 8, 30, 3, 2014), getRealData()[k]);
        }

        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(predictedSeries);
        dataset.addSeries(realSeries);

        return dataset;
    }

    public static double[] getRealData() throws IOException {

        List<String[]> data = ReadData.parsedData();
        int numOfRows = data.size();
        int numOfCol = data.get(0).length;
        double[] yData = new double[numOfRows - win];

        //making matrix from data read
        for (int i = 0; i < yData.length; i++) {
            yData[i] = Double.parseDouble(data.get(i + win)[numOfCol - 1]);
        }
        return yData;
    }

    /**
     * Creates a panel for the graph.
     *
     * @return A panel.
     */
    public static JPanel createPanel() throws IOException {
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
