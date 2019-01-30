import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.io.File;
import java.io.IOException;

public class Chart extends ApplicationFrame {
    JFreeChart chart;

    public Chart(String appTitle) {
        super(appTitle);
    }

//    public Chart(String appTitle,
//                 double[] xData,
//                 double[] yData,
//                 String chartTitle,
//                 String xAxisLabel,
//                 String yAxisLabel,
//                 NumberTickUnit tickUnit) {
//        super(appTitle);
//        chart = createChart(appTitle, xAxisLabel, yAxisLabel, createDataset(xData, yData), null);
//        setPanelProps();
//        saveAsFile(chart);
//    }

    public Chart(String appTitle,
                 double[] data,
                 String chartTitle,
                 String xAxisLabel,
                 String yAxisLabel,
                 NumberTickUnit tickUnit) {
        super(appTitle);
//        chart = createChart(appTitle, xAxisLabel, yAxisLabel, createDataset(data), new NumberTickUnit(10));
        chart = createChart(appTitle, xAxisLabel, yAxisLabel, createDataset(data), null);
        setPanelProps();
        saveAsFile(chart);
    }

    public JFreeChart createChart(String chartTitle, String xAxisLabel, String yAxisLabel, XYDataset dataset, NumberTickUnit tickUnit) {
        JFreeChart lineChart = ChartFactory.createXYLineChart(chartTitle, xAxisLabel, yAxisLabel, dataset,
                PlotOrientation.VERTICAL, false, false, false);
        if (tickUnit != null) {
            setPlotTickUnit(lineChart, tickUnit);
        }
        return lineChart;
    }

    public XYDataset createDataset(double[] data) {
        final XYSeries xySer = new XYSeries("");
        for (int i = 0; i < data.length; i++) {
//            xySer.add((i+1)*10, data[i]);
            xySer.add(i, data[i]);
        }
        final XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(xySer);
        return dataset;
    }

//    public XYDataset createDataset(double[] xData, double[] yData) {
//        final XYSeries xySer = new XYSeries("", false, true);
//        for (int i = 0; i < xData.length; i++) {
//            xySer.add(xData[i], yData[i]);
//        }
//        final XYSeriesCollection dataset = new XYSeriesCollection();
//        dataset.addSeries(xySer);
//        return dataset;
//    }

    public void setPlotTickUnit(JFreeChart chart, NumberTickUnit tick) {
        NumberAxis xAxis = new NumberAxis();
        xAxis.setTickUnit(tick);
        XYPlot plot = chart.getXYPlot();
        plot.setDomainAxis(xAxis);
    }

    public void saveAsFile(JFreeChart lineChart) {
        int width = 3200;    /* Width of the image */
        int height = 2400;   /* Height of the image */
        File lineChartFile = new File(super.getTitle() + ".jpeg");
        try {
            ChartUtils.saveChartAsJPEG(lineChartFile, lineChart, width, height);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setPanelProps() {
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(3200, 2400));
        chartPanel.setMouseWheelEnabled(true);
        chartPanel.setDomainZoomable(true);
        chartPanel.setRangeZoomable(true);
        setContentPane(chartPanel);
    }

    public JFreeChart getChart() {
        return chart;
    }
}
