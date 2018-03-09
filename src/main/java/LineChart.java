import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.io.File;
import java.io.IOException;

class LineChart extends ApplicationFrame {
    LineChart(double[] data, String applicationTitle, String chartTitle) {
        super(applicationTitle);
        JFreeChart lineChart = ChartFactory.createXYLineChart(
                chartTitle,
                "t", "x(t)",
                createDataset(data),
                PlotOrientation.VERTICAL,
                true, true, false);
//        NumberAxis xAxis = new NumberAxis();
//        xAxis.setTickUnit(new NumberTickUnit(1));
//
//        XYPlot plot =  lineChart.getXYPlot();
//        plot.setDomainAxis(xAxis);

        ChartPanel chartPanel = new ChartPanel(lineChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
        chartPanel.setDomainZoomable(true);
        chartPanel.setRangeZoomable(true);
        setContentPane(chartPanel);
        int width = 1280;    /* Width of the image */
        int height = 960;   /* Height of the image */
        File lineChartFile = new File(applicationTitle + ".jpeg");
        try {
            ChartUtils.saveChartAsJPEG(lineChartFile, lineChart, width, height);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //    private DefaultCategoryDataset createDataset(double[] data) {
//        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
//        for (int i = 0; i < data.length; i++) {
//            dataset.addValue(data[i], "value", "" + i);
//        }
//        return dataset;
//    }
    private XYDataset createDataset(double[] xData) {
        final XYSeries xySer = new XYSeries("");
        for (int i = 0; i < xData.length; i++) {
            xySer.add(i, xData[i]);
        }

        final XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(xySer);
        return dataset;
    }

}
