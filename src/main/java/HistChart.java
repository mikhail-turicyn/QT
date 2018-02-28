import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.data.statistics.HistogramDataset;

import static org.jfree.data.statistics.HistogramType.RELATIVE_FREQUENCY;

class HistChart extends ApplicationFrame {
    HistChart(double[] data, int spitCnt, String applicationTitle, String chartTitle) {
        super(applicationTitle);

        JFreeChart chart = ChartFactory.
                createHistogram(chartTitle, "x(t)", "P(x)",
                        createDataset(data, spitCnt),
                        PlotOrientation.VERTICAL, false, false, false);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
        setContentPane(chartPanel);
    }

    private HistogramDataset createDataset(double[] data, int splitCnt) {
        HistogramDataset dataset = new HistogramDataset();
//        HistogramType hType =  RELATIVE_FREQUENCY SCALE_AREA_TO_1 FREQUENCY;
        dataset.setType(RELATIVE_FREQUENCY);
        dataset.addSeries("origData", data, splitCnt);
        return dataset;
    }
}
