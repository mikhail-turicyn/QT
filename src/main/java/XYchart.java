import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.io.File;
import java.io.IOException;

import static org.jfree.data.statistics.HistogramType.RELATIVE_FREQUENCY;

public class XYchart extends ApplicationFrame {
    XYchart(double[] xData, double[] yData, String applicationTitle, String chartTitle) {
        super(applicationTitle);

        JFreeChart chart = ChartFactory.
                createXYLineChart(chartTitle, "x(t)", "P(x)",
                        createDataset(xData, yData));

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
        setContentPane(chartPanel);
        int width = 1280;    /* Width of the image */
        int height = 960;   /* Height of the image */
        File lineChartFile = new File(applicationTitle + ".jpeg");
        try {
            ChartUtils.saveChartAsJPEG(lineChartFile, chart, width, height);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private HistogramDataset createDataset(double[] data, int splitCnt) {
        HistogramDataset dataset = new HistogramDataset();
//        HistogramType hType =  RELATIVE_FREQUENCY SCALE_AREA_TO_1 FREQUENCY;
        dataset.setType(RELATIVE_FREQUENCY);
        dataset.addSeries("origData", data, splitCnt);
        return dataset;
    }

    private XYDataset createDataset(double[] xData, double[] yData) {
        final XYSeries xySer = new XYSeries("");
        for (int i = 0; i < xData.length; i++) {
            xySer.add(xData[i], yData[i]);
        }

        final XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(xySer);
        return dataset;
    }
}