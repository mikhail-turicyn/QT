import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.renderer.xy.VectorRenderer;

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
                        createVectorDataset(xData, yData));

        VectorRenderer renderer = new VectorRenderer();
        chart.getXYPlot().setRenderer(renderer);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
        setContentPane(chartPanel);
        return chart;
    }


}