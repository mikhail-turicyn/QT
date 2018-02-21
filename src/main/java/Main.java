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
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Random;

import static org.jfree.data.statistics.HistogramType.RELATIVE_FREQUENCY;
import static org.jfree.data.statistics.HistogramType.SCALE_AREA_TO_1;

public class Main {

    private static final int SPLITCNT = 4;
    private static double[] data = {9.5,9.03,9.9,7.6,7.7,8.9,8.01,7.6,7.9,7.5,6.6,6.3,5.5,3.3,2.1,1.1,1.11,1.2,1.9};

    public static void main(String[] args){
//        Random r = new Random();
//        for (int i = 0; i < 10; i++) {
//            data[i] = r.nextGaussian();
//        }
        FractalEstimation fr = new FractalEstimation(data, SPLITCNT);
        double[] ar = fr.getProbArray();
        for (double el:ar) {
            System.out.println(el);
        }
        HistogramDataset dataset = new HistogramDataset();
//        HistogramType hType =  RELATIVE_FREQUENCY SCALE_AREA_TO_1;
        dataset.setType(RELATIVE_FREQUENCY);
//        dataset.addSeries("series label",data,SPLITCNT);
        dataset.addSeries("series label",ar,SPLITCNT);
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
