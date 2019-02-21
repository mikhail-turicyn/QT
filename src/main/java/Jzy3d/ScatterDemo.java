package Jzy3d;

import Jzy3d.fractals.FractalEstimation;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.jzy3d.analysis.AbstractAnalysis;
import org.jzy3d.analysis.AnalysisLauncher;
import org.jzy3d.chart.factories.AWTChartComponentFactory;
import org.jzy3d.colors.Color;
import org.jzy3d.maths.Coord3d;
import org.jzy3d.plot3d.primitives.Scatter;
import org.jzy3d.plot3d.rendering.canvas.Quality;

import java.util.Random;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class ScatterDemo extends AbstractAnalysis {
    private static final Path pathToDataFile = Paths.get("D:\\Download\\MITuritsyn\\ch1.csv");
    private static final int SPLITCNT = 600;
//    static Shape surface;
    private static double[] arr;
    
    public static void main(String[] args) throws Exception {
        AnalysisLauncher.open(new ScatterDemo());
    }

    @Override
    public void init() {
        loadDataFromFile();
        System.out.println(arr.length);
        FractalEstimation fr = new FractalEstimation(arr, SPLITCNT);
        double[] perc = fr.getPerc();
        
        int size = 500000;
        float x;
        float y;
        float z;
        float a;

        Coord3d[] points = new Coord3d[arr.length];
        Color[] colors = new Color[arr.length];

        Random r = new Random();
        r.setSeed(0);

//        for (int i = 0; i < size; i++) {
        for (int i = 0; i < arr.length; i++) {
//            x = r.nextFloat() - 0.5f;
//            y = r.nextFloat() - 0.5f;
//            z = r.nextFloat() - 0.5f;
            x = (float) arr[i];
            y = i;
            z = (float) perc[i];
            points[i] = new Coord3d(x, y, z);
            a = 0.25f;
            colors[i] = new Color(x, y, z, a);
        }

        Scatter scatter = new Scatter(points, colors);
        chart = AWTChartComponentFactory.chart(Quality.Advanced, "newt");
        chart.getScene().add(scatter);
    }
    
    private static void loadDataFromFile() {
        if (Files.exists(pathToDataFile)) {
            try {
                arr = Files.lines(pathToDataFile).mapToDouble(val -> Double.parseDouble(val)).toArray();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            JFrame f = new JFrame();
            JFileChooser jf = new JFileChooser();
            int foRes = jf.showOpenDialog(f);
            if (foRes == JFileChooser.APPROVE_OPTION) {
                try {
                    System.out.println(jf.getSelectedFile().toPath());
                    arr = Files.lines(jf.getSelectedFile().toPath()).mapToDouble(val -> Double.parseDouble(val)).toArray();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.err.println("Пожалуйста выберите файл с входными данными!");
                JOptionPane.showMessageDialog(null, "Пожалуйста выберите файл с входными данными!");
                System.exit(1);
            }
        }

    }
}