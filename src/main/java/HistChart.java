import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.PlotOrientation;

class HistChart extends Chart {
    public HistChart(String appTitle,
                     double[] data,
                     int spitCnt,
                     String chartTitle) {
        this(appTitle, data, spitCnt, chartTitle, "", "", null);
    }

    HistChart(String appTitle,
              double[] data,
              int spitCnt,
              String chartTitle,
              String xAxisLabel,
              String yAxisLabel,
              NumberTickUnit tickUnit) {
        super(appTitle);
        JFreeChart chart = ChartFactory.
                createHistogram(chartTitle, xAxisLabel, yAxisLabel,
                        createDataset(data, spitCnt),
                        PlotOrientation.VERTICAL, false, false, false);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
        setContentPane(chartPanel);
        saveAsFile(chart);
    }
}