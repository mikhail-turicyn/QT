//import org.jfree.chart.util.Args;
//import org.jfree.data.statistics.HistogramBin;
//import org.jfree.data.statistics.HistogramDataset;
//
//import javax.swing.*;
//import java.io.IOException;
//import java.math.BigDecimal;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.util.*;
//
//public class TestFractalEstimation {
//    private BigDecimal epsilon;
//    private BigDecimal[] data;
//    private ArrayList<BigDecimal> bigData;
//    private int splitCnt;
//    private BigDecimal max;
//    private BigDecimal min;
//
//    public TestFractalEstimation(Path pathToDataFile, int splitCnt) {
//        loadDataFromFile(pathToDataFile);
////        this.data = data;
//        this.splitCnt = splitCnt;
//        this.max = getMaximum();
//        this.min = getMinimum();
//        this.epsilon = max.subtract(min).divide(BigDecimal.valueOf(splitCnt));
//    }
//
//    public TestFractalEstimation(BigDecimal[] data, int splitCnt) {
//        this.data = data;
//        this.splitCnt = splitCnt;
//        this.max = getMaximum();
//        this.min = getMinimum();
//        this.epsilon = max.subtract(min).divide(BigDecimal.valueOf(splitCnt));
//    }
//
//    public TestFractalEstimation(BigDecimal[] data, BigDecimal epsilon) {
//        this.data = data;
//        this.epsilon = epsilon;
//        this.max = getMaximum();
//        this.min = getMinimum();
//        this.splitCnt = max.subtract(min).divide(epsilon).intValue();
//    }
//    private BigDecimal getMaximum(){
//        Optional<BigDecimal> max = Arrays.asList(data).stream()
////                               .map(a -> a.amount)
//                               .max(Comparator.naturalOrder());
//        if (max.isPresent()) {
//            // have a max
//            return max.get();
//        } else
//            return BigDecimal.ZERO;
//    }
//
//    private BigDecimal getMinimum(){
//        Optional<BigDecimal> min = Arrays.asList(data).stream()
////                               .map(a -> a.amount)
//                .min(Comparator.naturalOrder());
//        if (min.isPresent()) {
//            // have a max
//            return min.get();
//        } else
//            return BigDecimal.ZERO;
//    }
//
//    private void loadDataFromFile(Path pathToDataFile) {
//        if (Files.exists(pathToDataFile)) {
//            try {
//                data = Files.lines(pathToDataFile).map(val -> BigDecimal.valueOf(Double.parseDouble(val))).toArray();
////                bigData.stream()
//                data = Files.lines(pathToDataFile).mapToDouble(val -> Double.parseDouble(val)).toArray();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        } else {
//            JFrame f = new JFrame();
//            JFileChooser jf = new JFileChooser();
//            int foRes = jf.showOpenDialog(f);
//            if (foRes == JFileChooser.APPROVE_OPTION) {
//                try {
//                    System.out.println(jf.getSelectedFile().toPath());
//                    data = Files.lines(jf.getSelectedFile().toPath()).mapToDouble(val -> Double.parseDouble(val)).toArray();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            } else {
//                System.err.println("Пожалуйста выберите файл с входными данными!");
//                JOptionPane.showMessageDialog(null, "Пожалуйста выберите файл с входными данными!");
//                System.exit(1);
//            }
//        }
//
//    }
//
////
////    public BigDecimal bEntropy() {
////        BigDecimal tmpVal = BigDecimal.valueOf(0d);
////        BigDecimal res = BigDecimal.valueOf(0d);
////        BigDecimal[] prob = getProbArray();
////        for (int i = 0; i < splitCnt; i++) {
////            for (int j = 0; j < splitCnt; j++) {
////                tmpVal.add(BigDecimal.valueOf(1.0).subtract(rMetric(i, j)).multiply(prob[j]));
////            }
////            res.add(prob[i].multiply(BigDecimalMath.ln(tmpVal,5)));
////        }
////        return res.divide(
////                BigDecimal.valueOf(
////                        Math.log(
////                                epsilon.doubleValue()
////                        )
////                )
////        );
////    }
////
////    public BigDecimal bEntropyInf() {
////        double tmpVal = 0;
////        double res = 0;
////        double[] prob = getProbArray();
////        for (int i = 0; i < splitCnt; i++) {
////            for (int j = 0; j < splitCnt; j++) {
////                tmpVal += (1.0 - Math.abs(prob[i] - prob[j])) * prob[j];
////            }
////            res += prob[i] * Math.log(tmpVal);
////        }
////        return res / Math.log(epsilon.doubleValue());
////    }
////
////    public BigDecimal[] getProbArray() {
////        BigDecimal[] res = new BigDecimal[splitCnt];
////        Map series = addSeries();
////        List<HistogramBin> binList = (ArrayList) series.get("bins");
////        int i = 0;
////        for (HistogramBin bin : binList) {
////            res[i] = (double) bin.getCount() / (double) series.get("values.length");
////            i++;
////        }
////        return res;
////    }
////
////    public strictfp double[] TestProbArray2() {
////        HistChart histChart = new HistChart("temp");
////        HistogramDataset testSet = histChart.createDataset(data, splitCnt);
////        double[] res = new double[splitCnt];
////        Map series = addSeries();
////        List<HistogramBin> binList = (ArrayList) series.get("bins");
////        int curInt = 0;
//////        for (int curInt = 0; curInt < binList.size(); curInt++) {
////        for (HistogramBin bin : binList) {
////            res[curInt] = (double) bin.getCount() / (double) series.get("values.length");
////            System.out.println("моя вероятность" + res[curInt] + "вероятность либы" + testSet.getY(0, curInt));
////            curInt++;
////        }
////        return res;
////    }
////
////
////    public Map addSeries() {
////        Comparable key = "bar";
////        Args.nullNotPermitted(key, "key");
////        Args.nullNotPermitted(data, "values");
////        if (splitCnt < 1) {
////            throw new IllegalArgumentException("The 'bins' value must be at least 1.");
////        } else {
////            double binWidth = (max - min) / (double) splitCnt;
////            double lower = min;
////            List binList = new ArrayList(splitCnt);
////
////            int i;
////            for (i = 0; i < splitCnt; ++i) {
////                HistogramBin bin;
////                if (i == splitCnt - 1) {
////                    bin = new HistogramBin(lower, max);
////                } else {
////                    double upper = min + (double) (i + 1) * binWidth;
////                    bin = new HistogramBin(lower, upper);
////                    lower = upper;
////                }
////
////                binList.add(bin);
////            }
////
////            for (i = 0; i < data.length; ++i) {
////                int binIndex = splitCnt - 1;
////                if (data[i] < max) {
////                    double fraction = (data[i] - min) / (max - min);
////                    if (fraction < 0.0D) {
////                        fraction = 0.0D;
////                    }
////
////                    binIndex = (int) (fraction * (double) splitCnt);
////                    if (binIndex >= splitCnt) {
////                        binIndex = splitCnt - 1;
////                    }
////                }
////
////                HistogramBin bin = (HistogramBin) binList.get(binIndex);
//////                System.out.println("bin with " + bin.getBinWidth() + " bin count " + bin.getCount() + " start" + bin.getStartBoundary() + " end" + bin.getEndBoundary());
////                bin.incrementCount();
////            }
////
////            Map map = new HashMap();
////            map.put("key", key);
////            map.put("bins", binList);
////            map.put("values.length", (double) data.length);
////            map.put("bin width", binWidth);
//////            List list = new ArrayList();
//////            list.add(map);
////            return map;
////        }
////    }
////
////    private BigDecimal rMetric(int curInt, int prevInt) {
////        return Math.abs(epsilon * (double) (curInt - prevInt)) / Math.abs(max - min);
////    }
////
////    public double[] getPerc() {
////        double[] res = new double[data.length];
////        for (int i = 0; i < data.length - 1; i++) {
////            res[i] = (int) ((data[i + 1] - data[i]) / epsilon);
////        }
////        return res;
////    }
////
////    public int[] getPerc(int dummy) {
////        int[] res = new int[data.length];
////        for (int i = 0; i < data.length - 1; i++) {
////            res[i] = (int) ((data[i + 1] - data[i]) / epsilon);
////        }
////        return res;
////    }
////
////    public int getTrack(double[] data) {
////        int res = 0;
////        for (int i = 0; i < data.length; i++) {
////            if (Math.abs(data[i]) > 0) {
////                res += Math.abs(data[i]);
////            } else {
////                res += 1;
////            }
////        }
////        return res;
////    }
////
//////    public void addSeries(Comparable key, double[] values, int bins) {
//////
//////        Args.nullNotPermitted(key, "key");
//////        Args.nullNotPermitted(values, "values");
//////        if (bins < 1) {
//////            throw new IllegalArgumentException(
//////                    "The 'bins' value must be at least 1.");
//////        }
//////        double binWidth = (max - min) / bins;
//////
//////        double lower = min;
//////        double upper;
//////        List<HistogramBin> binList = new ArrayList<>(bins);
//////        for (int i = 0; i < bins; i++) {
//////            HistogramBin bin;
//////            // make sure bins[bins.length]'s upper boundary ends at maximum
//////            // to avoid the rounding issue. the bins[0] lower boundary is
//////            // guaranteed start from min
//////            if (i == bins - 1) {
//////                bin = new HistogramBin(lower, max);
//////            }
//////            else {
//////                upper = min + (i + 1) * binWidth;
//////                bin = new HistogramBin(lower, upper);
//////                lower = upper;
//////            }
//////            binList.add(bin);
//////        }
//////        // fill the bins
//////        for (double value : values) {
//////            int binIndex = bins - 1;
//////            if (value < max) {
//////                double fraction = (value - min) / (max - min);
//////                if (fraction < 0.0) {
//////                    fraction = 0.0;
//////                }
//////                binIndex = (int) (fraction * bins);
//////                // rounding could result in binIndex being equal to bins
//////                // which will cause an IndexOutOfBoundsException - see bug
//////                // report 1553088
//////                if (binIndex >= bins) {
//////                    binIndex = bins - 1;
//////                }
//////            }
//////            HistogramBin bin = binList.get(binIndex);
//////            bin.incrementCount();
//////        }
//////        // generic map for each series
//////        Map<String, Object> map = new HashMap<>();
//////        map.put("key", key);
//////        map.put("bins", binList);
//////        map.put("values.length", values.length);
//////        map.put("bin width", binWidth);
//////        this.list.add(map);
////////        fireDatasetChanged();
//////    }
////
////    private BigDecimal getMinimum() {
////        if (data != null && data.length >= 1) {
////            double min = 1.7976931348623157E308D;
////
////            for (double aData : data) {
////                if (aData < min) {
////                    min = aData;
////                }
////            }
////            return min;
////        } else {
////            throw new IllegalArgumentException("Null or zero length 'data' argument.");
////        }
////    }
////
////    private BigDecimal getMaximum() {
////        if (data != null && data.length >= 1) {
////            double max = -1.7976931348623157E308D;
////
////            for (double aData : data) {
////                if (aData > max) {
////                    max = aData;
////                }
////            }
////            return max;
////        } else {
////            throw new IllegalArgumentException("Null or zero length 'data' argument.");
////        }
////    }
////
////    public void setSplitCnt(int splitCnt) {
////        this.splitCnt = splitCnt;
////        this.epsilon = (max - min) / splitCnt;
////    }
////
//}
