import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class Main extends JFrame{

    private static final int SPLITCNT = 50;
//    private static double[] arr = {9.5,9.03,9.9,7.6,7.7,8.9,8.01,7.6,7.9,7.5,6.6,6.3,5.5,3.3,2.1,1.1,1.11,1.2,1.9};
//    private static double[] arr = {1.5, 1.7, 2.5, 2.7, 3.5, 3.7, 4.5, 4.7, 5.5, 1, 2, 3, 4, 5, 6};
    private static double[] arr;

    public static void main(String[] args){
        JFrame f = new JFrame();
//        JFileChooser jf = new JFileChooser();
//        int foRes = jf.showOpenDialog(f);
//        if(foRes == JFileChooser.APPROVE_OPTION) {
//            try {
//                System.out.println(jf.getSelectedFile().toPath());
//                arr = Files.lines(jf.getSelectedFile().toPath()).mapToDouble(val -> Double.parseDouble(val)).toArray();
//                for (double el:arr) {
//                    System.out.println(el);
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        } else {
//            System.err.println("Пожалуйста выберите файл с входными данными!");
//            JOptionPane.showMessageDialog(null, "Пожалуйста выберите файл с входными данными!");
//            System.exit(1);
//        }
//      _________________________________________________________________________________________________________________________
        try {
            arr = Files.lines(Paths.get("/home/eq/Документы/log.csv"))
                    .mapToDouble(val -> Double.parseDouble(val)).toArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
//      _________________________________________________________________________________________________________________________
        FractalEstimation fr = new FractalEstimation(arr, SPLITCNT);
        double[] ar = fr.getProbArray();
        System.out.println("сумма элементов массива вероятностей: " + Arrays.stream(ar).sum());
        System.out.println("B entropy " + fr.bEntropy());

        HistChart hc = new HistChart(arr, SPLITCNT, "B энтропия", "B энтропия");
        hc.pack();
        hc.setVisible(true);

        LineChart ch = new LineChart(arr, "Временой ряд", "");
        ch.pack();
        ch.setVisible(true);
    }
}
