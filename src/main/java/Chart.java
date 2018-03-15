import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.xy.*;

import java.io.File;
import java.io.IOException;

import static org.jfree.data.statistics.HistogramType.RELATIVE_FREQUENCY;

public class Chart extends ApplicationFrame {
    JFreeChart chart;

    public Chart(String appTitle) {
        super(appTitle);
    }

    public Chart(String appTitle,
                 double[] xData,
                 double[] yData,
                 String chartTitle,
                 String xAxisLabel,
                 String yAxisLabel,
                 NumberTickUnit tickUnit) {
        super(appTitle);
        chart = createChart(appTitle, xAxisLabel, yAxisLabel, createDataset(xData, yData), null);
        setPanelProps();
        saveAsFile(chart);
    }

    public Chart(String appTitle,
                 double[] data,
                 String chartTitle,
                 String xAxisLabel,
                 String yAxisLabel,
                 NumberTickUnit tickUnit) {
        super(appTitle);
        chart = createChart(appTitle, xAxisLabel, yAxisLabel, createDataset(data), null);
        setPanelProps();
        saveAsFile(chart);
    }

    public JFreeChart createChart(String chartTitle, String xAxisLabel, String yAxisLabel, XYDataset dataset, NumberTickUnit tickUnit) {
        JFreeChart lineChart = ChartFactory.createXYLineChart(chartTitle, xAxisLabel, yAxisLabel, dataset, PlotOrientation.VERTICAL, true, true, false);
        if (tickUnit != null) {
            setPlotTickUnit(lineChart, tickUnit);
        }
        return lineChart;
    }

    public HistogramDataset createDataset(double[] data, int splitCnt) {
        HistogramDataset dataset = new HistogramDataset();
//        HistogramType hType =  RELATIVE_FREQUENCY SCALE_AREA_TO_1 FREQUENCY;
        dataset.setType(RELATIVE_FREQUENCY);
        dataset.addSeries("origData", data, splitCnt);
        return dataset;
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

    public XYDataset createDataset(double[] xData, double[] yData) {
        final XYSeries xySer = new XYSeries("", false, true);
        for (int i = 0; i < xData.length; i++) {
            xySer.add(xData[i], yData[i]);
        }
        final XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(xySer);
        return dataset;
    }

    public XYDataset createVectorDataset(double[] xData, double[] yData) {
        VectorSeries vectorSeries = new VectorSeries("Vector Series");
        for (int i = 0; i < xData.length - 1; i++) {
            vectorSeries.add(xData[i], yData[i], xData[i + 1] - xData[i], yData[i + 1] - yData[i]);
        }
        VectorSeriesCollection dataSet = new VectorSeriesCollection();
        dataSet.addSeries(vectorSeries);
        return dataSet;
    }

    public void setPlotTickUnit(JFreeChart chart, NumberTickUnit tick) {
        NumberAxis xAxis = new NumberAxis();
        xAxis.setTickUnit(tick);
        XYPlot plot = chart.getXYPlot();
        plot.setDomainAxis(xAxis);
    }

    public void saveAsFile(JFreeChart lineChart) {
        int width = 1280;    /* Width of the image */
        int height = 960;   /* Height of the image */
        File lineChartFile = new File(super.getTitle() + ".jpeg");
        try {
            ChartUtils.saveChartAsJPEG(lineChartFile, lineChart, width, height);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setPanelProps() {
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
        chartPanel.setDomainZoomable(true);
        chartPanel.setRangeZoomable(true);
        setContentPane(chartPanel);
    }

    public JFreeChart getChart() {
        return chart;
    }
}
