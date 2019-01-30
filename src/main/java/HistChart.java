import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.statistics.HistogramDataset;

import static org.jfree.data.statistics.HistogramType.RELATIVE_FREQUENCY;

class HistChart extends Chart {
    public HistChart(String appTitle,
                     double[] data,
                     int splitCnt,
                     String chartTitle) {
        this(appTitle, data, splitCnt, chartTitle, "", "", null);
    }

    HistChart(String appTitle,
              double[] data,
              int splitCnt,
              String chartTitle,
              String xAxisLabel,
              String yAxisLabel,
              NumberTickUnit tickUnit) {
        super(appTitle);
        JFreeChart chart = ChartFactory.
                createHistogram(chartTitle, xAxisLabel, yAxisLabel,
                        createDataset(data, splitCnt),
                        PlotOrientation.VERTICAL, false, false, false);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(3200, 2400));
        chartPanel.setMouseWheelEnabled(true);
        setContentPane(chartPanel);
        saveAsFile(chart);
    }

    public HistogramDataset createDataset(double[] data, int splitCnt) {
        HistogramDataset dataset = new HistogramDataset();
//        HistogramType hType =  RELATIVE_FREQUENCY SCALE_AREA_TO_1 FREQUENCY;
        dataset.setType(RELATIVE_FREQUENCY);
        dataset.addSeries("origData", data, splitCnt);
        return dataset;
    }
}