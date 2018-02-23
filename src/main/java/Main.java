
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultIntervalCategoryDataset;
import org.jfree.data.category.IntervalCategoryDataset;
import org.jfree.data.statistics.HistogramBin;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.statistics.HistogramType;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import static org.jfree.data.statistics.HistogramType.FREQUENCY;
import static org.jfree.data.statistics.HistogramType.RELATIVE_FREQUENCY;
import static org.jfree.data.statistics.HistogramType.SCALE_AREA_TO_1;

public class Main extends JFrame{

    private static final int SPLITCNT = 400;
//    private static double[] arr = {9.5,9.03,9.9,7.6,7.7,8.9,8.01,7.6,7.9,7.5,6.6,6.3,5.5,3.3,2.1,1.1,1.11,1.2,1.9};
    private static double[] arr;

    public static void main(String[] args){
        JFrame f = new JFrame();
        JFileChooser jf = new JFileChooser();
        int foRes = jf.showOpenDialog(f);
        if(foRes == JFileChooser.APPROVE_OPTION) {
            try {
                System.out.println(jf.getSelectedFile().toPath());
                arr = Files.lines(jf.getSelectedFile().toPath()).mapToDouble(val -> Double.parseDouble(val)).toArray();
                for (double el:arr) {
                    System.out.println(el);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("Пожалуйста выберите файл с входными данными!");
            JOptionPane.showMessageDialog(null, "Пожалуйста выберите файл с входными данными!");
            System.exit(1);
        }
//      _________________________________________________________________________________________________________________________
//        try {
//            arr = Files.lines(Paths.get("/home/eq/Документы/log.csv"))
//                    .mapToDouble(val -> Double.parseDouble(val)).toArray();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//      _________________________________________________________________________________________________________________________
        FractalEstimation fr = new FractalEstimation(arr, SPLITCNT);
        double[] ar = fr.getProbArray();
        System.out.println("сумма элементов массива вероятностей: " + Arrays.stream(ar).sum());
//        for (double el:ar) {
//            System.out.println(el);
//        }
        System.out.println("B entropy " + fr.bEntropy());
        fr.addSeries("origData",arr,SPLITCNT);
        System.out.println("Eurica " + fr.map.get("bins"));
        HistogramDataset dataset = new HistogramDataset();
//        HistogramType hType =  RELATIVE_FREQUENCY SCALE_AREA_TO_1 FREQUENCY;
        dataset.setType(RELATIVE_FREQUENCY);
        dataset.addSeries("origData",arr,SPLITCNT);
//        dataset.addSeries("series label",ar,SPLITCNT);
        JFreeChart chart = ChartFactory.
                createHistogram( "B энтропия", "x(t)", "P(x)",
                dataset, PlotOrientation.VERTICAL, false, false, false);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        chartPanel.setMouseZoomable(true, false);

        f.add(chartPanel);
        f.setVisible(true);
        f.setSize(640,320);
        f.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    }
}
