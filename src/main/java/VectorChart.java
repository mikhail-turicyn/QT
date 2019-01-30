import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.xy.VectorRenderer;
import org.jfree.data.xy.VectorSeries;
import org.jfree.data.xy.VectorSeriesCollection;
import org.jfree.data.xy.XYDataset;

public class VectorChart extends Chart {

    public VectorChart(String title,
                       double[] xData,
                       double[] yData,
                       String chartTitle) {
        this(title, xData, yData, "", "", null);
    }

    public VectorChart(String title,
                       double[] xData,
                       double[] yData,
                       String xAxisLabel,
                       String yAxisLabel,
                       NumberTickUnit tickUnit) {
        super(title);
        JFreeChart chart = createChart(xData, yData, title, xAxisLabel, yAxisLabel);
        saveAsFile(chart);
    }

    private JFreeChart createChart(double[] xData,
                                   double[] yData,
                                   String title,
                                   String xAxisLabel,
                                   String yAxisLabel) {
        JFreeChart chart = ChartFactory.
                createXYLineChart(title, xAxisLabel, yAxisLabel,
                        createDataset(xData, yData), PlotOrientation.VERTICAL, false, false, false);

        VectorRenderer renderer = new VectorRenderer();
        chart.getXYPlot().setRenderer(renderer);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(3200, 2400));
        chartPanel.setMouseWheelEnabled(true);
        setContentPane(chartPanel);
        return chart;
    }

    public XYDataset createDataset(double[] xData, double[] yData) {
        VectorSeries vectorSeries = new VectorSeries("Vector Series");
        for (int i = 0; i < xData.length - 1; i++) {
            vectorSeries.add(xData[i], yData[i], xData[i + 1] - xData[i], yData[i + 1] - yData[i]);
        }
        VectorSeriesCollection dataSet = new VectorSeriesCollection();
        dataSet.addSeries(vectorSeries);
        return dataSet;
    }

}