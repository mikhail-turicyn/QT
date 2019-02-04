
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.data.statistics.HistogramBin;
import org.jfree.data.statistics.HistogramDataset;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main extends JFrame{

    private static final int SPLITCNT = 500;
    private static final Path pathToDataFile = Paths.get("/home/qq/ch1.csv");
    //        private static double[] arr = {9.5,9.03,9.9,7.6,7.7,8.9,8.01,7.6,7.9,7.5,6.6,6.3,5.5,3.3,2.1,1.1,1.11,1.2,1.9};
//    private static double[] arr = {1.5, 1.7, 2.5, 2.7, 3.5, 3.7, 4.5, 4.7, 5.5, 1, 2, 3, 4, 5, 6};
    private static double[] arr;

    public static void main(String[] args) {
        fillData();
        FractalEstimation fr = new FractalEstimation(arr, SPLITCNT);
//        double[] ar =
        double[] perc = fr.getPerc();
        Map series = fr.addSeries();
        double len = (double) series.get("values.length");
        List<HistogramBin> binList = (ArrayList) series.get("bins");
        for (HistogramBin bin : binList) {
            System.out.println((double) bin.getCount() / len);
        }
        System.out.println("трек перколяционной функции " + fr.getTrack(perc));
        System.out.println("delta: " + fr.epsilon);
        System.out.println("Геометрическая фрактальная размерность " + fr.bEntropy());
        System.out.println("Информационная фрактальная размерность " + fr.bEntropyInf());
//        fr.TestProbArray();
        fr.TestProbArray2();


        HistChart histChart = new HistChart("Распределение значений", arr, SPLITCNT, "Распределение значений", "x, °C", "P(x)", null);
        HistogramDataset testSet = histChart.createDataset(arr, SPLITCNT);
////        List bins= testSet.getX(1,1);
//        testSet.getXValue();
//        testSet.get
        for (int i = 0; i < SPLITCNT; i++) {
//            System.out.printf("bin count real %s %s%n", testSet.getX(0, i), testSet.getY(0, i));
        }
        histChart.pack();
        histChart.setVisible(true);

        Chart timeLine = new Chart("Временой ряд", arr, "", "номер измерения", "x, °C", null);
        timeLine.pack();
        timeLine.setVisible(true);

        Chart perkGraph = new Chart("Перколяции при delta=" + fr.epsilon, perc, "", "номер измерения", "P(t)", new NumberTickUnit(1));
        perkGraph.pack();
        perkGraph.setVisible(true);

        double[] percE = new double[perc.length];
        percE[0] = perc[0];
        for (int i = 1; i < perc.length; i++) {
            if (perc[i] != 0)
                percE[i] = percE[i - 1] + perc[i];
            else
                percE[i] = percE[i - 1];
        }
        Chart perkEGraph = new Chart("Перколяции с накоплением при delta=" + fr.epsilon, percE, "", "номер измерения", "P(t)", new NumberTickUnit(1));
        perkEGraph.pack();
        perkEGraph.setVisible(true);

        VectorChart phaseGraph = new VectorChart("Фазовый портрет при delta=" + fr.epsilon, arr, perc, "x, °C", "P(t)", null);
        phaseGraph.pack();
        phaseGraph.setVisible(true);

        int n = 50;
        double[] tmpArr = new double[99 * n];
        for (int i = 0; i < 100; i++) {
            fr.setSplitCnt((i + 1) * n);
            tmpArr[i] = fr.bEntropy();
        }
        Chart c = new Chart("Энтропия-эпсилон", tmpArr, "", "число интервалов", "B энтропия", null);
        c.pack();
        c.setVisible(true);
    }

    private static void fillData() {
        if (arr == null) {
            loadDataFromFile();
        } else {
            arr = new double[5000];
            for (int i = 0; i < arr.length; i++) {
                arr[i] = 2.5 * Math.sin(2 * Math.PI * i * 0.001);
//            arr[i] =
            }
        }
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
