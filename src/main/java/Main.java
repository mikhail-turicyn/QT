import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultIntervalCategoryDataset;
import org.jfree.data.category.IntervalCategoryDataset;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.statistics.HistogramType;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

import static org.jfree.data.statistics.HistogramType.RELATIVE_FREQUENCY;
import static org.jfree.data.statistics.HistogramType.SCALE_AREA_TO_1;

public class Main {

    private static final int SPLITCNT = 20;
    private static double[] data = new double[1000];

    public static void main(String[] args){
       // System.out.println(FractalEstimation.rMetric(12,23,2));
        Random r = new Random();
        for (int i = 0; i < 1000; i++) {
            data[i] = r.nextGaussian();
//            System.out.println(data[i]);
        }
        HistogramDataset dataset = new HistogramDataset();
//        HistogramType hType =  RELATIVE_FREQUENCYSCALE_AREA_TO_1;
        dataset.setType(RELATIVE_FREQUENCY);
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
