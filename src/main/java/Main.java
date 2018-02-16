import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.statistics.HistogramDataset;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Main {

    private static final int SPLITCNT = 20;
    private static double[] data = new double[1000];

    public static void main(String[] args){
       // System.out.println(FractalEstimation.rMetric(12,23,2));
        Random r = new Random();
        for (int i = 0; i < 1000; i++) {
            data[i] = r.nextGaussian();
            System.out.println(data[i]);
        }
        HistogramDataset dataset = new HistogramDataset();
        dataset.addSeries("series label",data,SPLITCNT);
        JFreeChart chart = ChartFactory.
                createHistogram( "plotTitle", "xaxis label", "yaxis label",
                dataset, PlotOrientation.VERTICAL, false, false, false);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        chartPanel.setMouseZoomable(true, false);
        Frame f = new JFrame();
        f.add(chartPanel);
        f.setVisible(true);
        f.setSize(640,320);
    }
}
