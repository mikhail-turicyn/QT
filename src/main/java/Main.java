import org.jfree.chart.axis.NumberTickUnit;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;

public class Main extends JFrame {

    private static final int SPLITCNT = 6;
    private static final Path pathToDataFile = Paths.get("Книга1.csv");
    private static boolean isloadFromFle = false;
        private static double[] arr = {9.5,9.03,9.9,7.6,7.7,8.9,8.01,7.6,7.9,7.5,6.6,6.3,5.5,3.3,2.1,1.1,1.11,1.2,1.9};
//    private static double[] arr = {1.5, 1.7, 2.5, 2.7, 3.5, 3.7, 4.5, 4.7, 5.5, 1, 2, 3, 4, 5, 6};
//    private static double[] arr;

    public static void main(String[] args) {
        entropy();
        fillData();
        FractalEstimation fr = new FractalEstimation(arr, SPLITCNT);

        double[] perc = fr.getPerc();
        for (double q : perc) {
            System.out.println("perc " + q);
        }
//        Map series = fr.addSeries();
//        double len = (double) series.get("values.length");
//        List<HistogramBin> binList = (ArrayList) series.get("bins");
//        for (HistogramBin bin : binList) {
//            System.out.println((double) bin.getCount() / len);
//        }
        System.out.println("трек перколяционной функции " + fr.getTrack(perc));
        fr.getAgregates(arr);
        for (Agregate el : fr.getAgregates(arr)) {
            System.out.println("agregate " + el.start + " " + el.width);
        }
        System.out.println("delta: " + fr.getEpsilon());
        System.out.println("Геометрическая фрактальная размерность " + fr.bEntropy());
        System.out.println("Информационная фрактальная размерность " + fr.bEntropyInf());
//        fr.TestProbArray();
//        fr.TestProbArray2();


        HistChart histChart = new HistChart("Распределение значений", arr, SPLITCNT, "Распределение значений", "x, °C", "P(x)", null);
//        HistogramDataset testSet = histChart.createDataset(arr, SPLITCNT);
////        List bins= testSet.getX(1,1);
//        testSet.getXValue();
//        testSet.get
//        for (int i = 0; i < SPLITCNT; i++) {
//            System.out.printf("bin count real %s %s%n", testSet.getX(0, i), testSet.getY(0, i));
//        }
        histChart.pack();
        histChart.setVisible(true);

        Chart timeLine = new Chart("Временой ряд", arr, "", "номер измерения", "x, °C", null);
        timeLine.pack();
        timeLine.setVisible(true);

        Chart perkGraph = new Chart("Перколяции при delta=" + fr.getEpsilon(), perc, "", "номер измерения", "P(t)", new NumberTickUnit(1));
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
        Chart perkEGraph = new Chart("Перколяции с накоплением при delta=" + fr.getEpsilon(), percE, "", "номер измерения", "P(t)", new NumberTickUnit(1));
        perkEGraph.pack();
        perkEGraph.setVisible(true);

        VectorChart phaseGraph = new VectorChart("Фазовый портрет при delta=" + fr.getEpsilon(), arr, perc, "x, °C", "P(t)", null);
        phaseGraph.pack();
        phaseGraph.setVisible(true);

        int n = 1;
        double[] tmpArr = new double[n*10];
        for (int i = 0; i < n*10; i++) {
            fr.setSplitCnt((i + 1) * n);
            tmpArr[i] = fr.bEntropyInf();
        }
        Chart c = new Chart("Энтропия-эпсилон", tmpArr, "", "число интервалов", "B энтропия", null);
        c.pack();
        c.setVisible(true);
    }

    private static void fillData() {
        if (isloadFromFle) {
            loadDataFromFile();
        } else if (arr == null) {
            arr = new double[5000];
            for (int i = 0; i < arr.length; i++) {
                arr[i] = 2.5 * Math.sin(2 * Math.PI * i * 0.001);
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

    private static void entropy() {
        File file = new File("/home/qq/ch1.csv");
        FileInputStream fin = null;
        try {
            fin = new FileInputStream(file);

            byte fileContent[] = new byte[(int) file.length()];

            // Read data into the byte array
            fin.read(fileContent);

            // create array to keep track of frequency of bytes
            int[] frequency_array = new int[256];
            int fileContentLength = fileContent.length - 1;

            // count frequency of occuring bytes
            for (int i = 0; i < fileContentLength; i++) {
                byte byteValue = fileContent[i];
                frequency_array[byteValue]++;
            }


            double entropy = 0;
            for (int i = 0; i < frequency_array.length; i++) {
                if (frequency_array[i] != 0) {
                    // calculate the probability of a particular byte occuring
                    double probabilityOfByte = (double) frequency_array[i] / (double) fileContentLength;

                    // calculate the next value to sum to previous entropy calculation
                    double value = probabilityOfByte * (Math.log(probabilityOfByte) / Math.log(2));
                    entropy = entropy + value;
                } else {
                }
            }
            entropy *= -1;

            // output the entropy calculated
            DecimalFormat df = new DecimalFormat("#.#####");
            System.out.println("Entropy is: " + df.format(entropy) + " bits per byte");
            fin.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found" + e);
        } catch (IOException ioe) {
            System.out.println("Exception while reading file " + ioe);
        }


    }
}