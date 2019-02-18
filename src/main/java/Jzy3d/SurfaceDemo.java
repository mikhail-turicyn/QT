package Jzy3d;

import Jzy3d.fractals.FractalEstimation;
import org.jzy3d.analysis.AbstractAnalysis;
import org.jzy3d.analysis.AnalysisLauncher;
import org.jzy3d.chart.factories.AWTChartComponentFactory;
import org.jzy3d.colors.ColorMapper;
import org.jzy3d.colors.colormaps.ColorMapRainbow;
import org.jzy3d.maths.Coord3d;
import org.jzy3d.maths.Range;
import org.jzy3d.plot3d.primitives.Point;
import org.jzy3d.plot3d.primitives.Polygon;
import org.jzy3d.plot3d.primitives.Shape;
import org.jzy3d.plot3d.rendering.canvas.Quality;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class SurfaceDemo extends AbstractAnalysis {
    private static final Path pathToDataFile = Paths.get("/home/qq/ch1.csv");
    private static final int SPLITCNT = 60;
    static Shape surface;
    private static double[] arr;

    public static void main(String[] args) throws Exception {
        loadDataFromFile();
        FractalEstimation fr = new FractalEstimation(arr, SPLITCNT);
        double[] perc = fr.getPerc();
        Polygon polygon = new Polygon();
        List<Polygon> polygons = new ArrayList<Polygon>();
        for (int i = 0; i < arr.length - 1; i++) {
            polygon.add(new Point(new Coord3d(i, arr[i], perc[i])));
            polygons.add(polygon);
        }
        surface = new Shape(polygons);
        surface.setColorMapper(new ColorMapper(new ColorMapRainbow(), surface.getBounds().getZmin(), surface.getBounds().getZmax(), new org.jzy3d.colors.Color(1, 1, 1, 1f)));
        surface.setWireframeDisplayed(true);
        surface.setWireframeColor(org.jzy3d.colors.Color.BLACK);

        AnalysisLauncher.open(new SurfaceDemo());
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

    @Override
    public void init() {
        // Define a function to plot
//        Mapper mapper;
//        mapper.f(perc, arr);

//            @Override
//            public double f(double v, double v1) {
//                return 0;
//            }
//        };
//        Mapper mapper = new Mapper() {
//            @Override
//            public double f(double x, double y) {
//                return x * Math.sin(x * y);
//            }
//        };

        // Define range and precision for the function to plot
        Range range = new Range(-3, 3);
        int steps = SPLITCNT;

        // Create the object to represent the function over the given range.
//        final Shape surface = Builder.buildOrthonormal(new OrthonormalGrid(range, steps, range, steps), mapper);
//        surface.setColorMapper(new ColorMapper(new ColorMapRainbow(), surface.getBounds().getZmin(), surface.getBounds().getZmax(), new Color(1, 1, 1, .5f)));
//        surface.setFaceDisplayed(true);
//        surface.setWireframeDisplayed(false);

        // Create a chart
        chart = AWTChartComponentFactory.chart(Quality.Advanced, getCanvasType());
        chart.getScene().getGraph().add(surface);
    }
}