import org.jfree.chart.axis.NumberTickUnit;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main extends JFrame{

    private static final int SPLITCNT = 5000;
    private static final String pathToDataFile = "/home/eq/Документы/log.csv";
    //    static double[] arr = new double[1000];
    static double[] tmpArr = new double[100];
//    private static double[] arr = {9.5,9.03,9.9,7.6,7.7,8.9,8.01,7.6,7.9,7.5,6.6,6.3,5.5,3.3,2.1,1.1,1.11,1.2,1.9};
//private static double[] arr = {1.5, 1.7, 2.5, 2.7, 3.5, 3.7, 4.5, 4.7, 5.5, 1, 2, 3, 4, 5, 6};
private static double[] arr;

    public static void main(String[] args) {
        fillData();

        FractalEstimation fr = new FractalEstimation(arr, SPLITCNT);
        double[] ar = fr.getProbArray();
        double[] perc = fr.getPerc();
        System.out.println("delta: " + fr.epsilon);
        for (int i = 0; i < arr.length; i++) {
            System.out.println(i + " " + arr[i] + " " + perc[i]);
        }
        System.out.println("Геометрическая фрактальная размерность " + fr.bEntropy());
        System.out.println("Информационная фрактальная размерность " + fr.bEntropyInf());


        HistChart histChart = new HistChart("Распределение значений", arr, SPLITCNT, "Распределение значений", "x, град/с", "P(x)", null);
        histChart.pack();
        histChart.setVisible(true);

        Chart timeLine = new Chart("Временой ряд", arr, "", "t*10^(-4), мс", "x(t), град/с", null);
        timeLine.pack();
        timeLine.setVisible(true);

        Chart perkGraph = new Chart("Перколяции при delta=" + fr.epsilon, perc, "", "t*10^(-4), мс", "P(t)", new NumberTickUnit(1));
        perkGraph.pack();
        perkGraph.setVisible(true);

        VectorChart phaseGraph = new VectorChart("Фазовый портрет при delta=" + fr.epsilon, arr, perc, "x(t), град/с", "P(t)", null);
        phaseGraph.pack();
        phaseGraph.setVisible(true);

        for (int i = 0; i < 100; i++) {
            fr.setSplitCnt((i + 1) * 50);
            tmpArr[i] = fr.bEntropy();
//            System.out.println(tmpArr[i]);
        }
        Chart c = new Chart("Энтропия-эпсилон", tmpArr, "", "число интервалов", "B энтропия", null);
        c.pack();
        c.setVisible(true);
    }

    private static void fillData() {
//        loadDataFromFile();

        if (arr == null) {
            try {
                arr = Files.lines(Paths.get(pathToDataFile))
                        .mapToDouble(Double::parseDouble).toArray();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            //        for (int i = 0; i < arr.length; i++) {
//            arr[i]= 2.5*Math.sin(2*Math.PI*i*0.001);
//        }
        }
    }

    private static void loadDataFromFile() {
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
