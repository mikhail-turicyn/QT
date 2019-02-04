import org.jfree.chart.util.Args;
import org.jfree.data.statistics.HistogramBin;
import org.jfree.data.statistics.HistogramDataset;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestFractalEstimation {
    BigDecimal epsilon;
    private double[] data;
    private int splitCnt;
    private double max;
    private double min;
//    private List<Map<String, Object>> list;

    public FractalEstimation(double[] data, int splitCnt) {
        this.data = data;
        this.splitCnt = splitCnt;
        this.max = getMaximum();
        this.min = getMinimum();
        this.epsilon = (max - min) / splitCnt;
    }

    public FractalEstimation(double[] data, double epsilon) {
        this.data = data;
        this.epsilon = epsilon;
        this.max = getMaximum();
        this.min = getMinimum();
        this.splitCnt = (int) ((max - min) / epsilon);
    }

    double bEntropy() {
        double tmpVal = 0;
        double res = 0;
        double[] prob = getProbArray();
        for (int i = 0; i < splitCnt; i++) {
            for (int j = 0; j < splitCnt; j++) {
                tmpVal += (1.0 - rMetric(i, j)) * prob[j];
            }
            res += prob[i] * Math.log(tmpVal);
        }
//        System.out.println("B-entropy geometrical " + -res);
        return res / Math.log(epsilon.doubleValue());
    }

    double bEntropyInf() {
        double tmpVal = 0;
        double res = 0;
        double[] prob = getProbArray();
        for (int i = 0; i < splitCnt; i++) {
            for (int j = 0; j < splitCnt; j++) {
                tmpVal += (1.0 - Math.abs(prob[i] - prob[j])) * prob[j];
            }
            res += prob[i] * Math.log(tmpVal);
        }
        return res / Math.log(epsilon);
    }

    public double[] getProbArray() {
        double[] res = new double[splitCnt];
//        double[] tmpData = Arrays.copyOf(data, data.length);
//        int curInt = 0;
//        int elCount = 0;
//        Arrays.sort(tmpData);
//        for (int i = 0; i < tmpData.length - 1 ; i++) {
//            if(tmpData[i] < min + (double)(curInt+1)*epsilon){
//                elCount += 1;
//            } else {
//                res[curInt] = (double)(elCount) / (double)(data.length);
//                curInt += 1;
//                elCount = 1;
//            }
//        }
//        curInt -=1;
//        res[curInt] = (double)(elCount+1) / (double)(data.length);
        Map series = addSeries();
        List<HistogramBin> binList = (ArrayList) series.get("bins");
        int i = 0;
        for (HistogramBin bin : binList) {
            res[i] = (double) bin.getCount() / (double) series.get("values.length");
            i++;
        }
        return res;
    }

    public strictfp double[] TestProbArray2() {
        HistChart histChart = new HistChart("temp");
        HistogramDataset testSet = histChart.createDataset(data, splitCnt);
        double[] res = new double[splitCnt];
        Map series = addSeries();
        List<HistogramBin> binList = (ArrayList) series.get("bins");
        int curInt = 0;
//        for (int curInt = 0; curInt < binList.size(); curInt++) {
        for (HistogramBin bin : binList) {
            res[curInt] = (double) bin.getCount() / (double) series.get("values.length");
            System.out.println("моя вероятность" + res[curInt] + "вероятность либы" + testSet.getY(0, curInt));
            curInt++;
        }
        return res;
    }

//    public BigDecimal[] TestProbArray() {
//        HistChart histChart = new HistChart("temp");
//        HistogramDataset testSet = histChart.createDataset(data, splitCnt);
//        BigDecimal[] res = new BigDecimal[splitCnt];
//        BigDecimal[] tmpData = new BigDecimal[data.length];
//        for (int i = 0; i < tmpData.length; i++) {
//            tmpData[i] = BigDecimal.valueOf(data[i]);
//        }
////        BigDecimal[] tmpData = Arrays.copyOf(BigDecimal.valueOf(data), data.length);
//        int curInt = 0;
//        int elCount = 0;
//        Arrays.sort(tmpData);
//        for (int i = 0; i < tmpData.length - 1 ; i++) {
//            if(tmpData[i].compareTo(BigDecimal.valueOf(min + (double)(curInt+1)*epsilon)) == -1){
//                elCount += 1;
//            } else {
//                res[curInt] = BigDecimal.valueOf((double)(elCount) / (double)(data.length));
////                if (res[curInt] == testSet.getY(0, curInt))
//                System.out.println("моя вероятность" + res[curInt] + "вероятность либы" + testSet.getY(0, curInt));
//                curInt += 1;
//                elCount = 1;
//            }
//        }
//        curInt -=1;
//        res[curInt] = BigDecimal.valueOf((double)(elCount+1) / (double)(data.length));
//        System.out.println("моя вероятность" + res[curInt] + "вероятность либы" + testSet.getY(0, curInt));
//        return res;
//    }

    public Map addSeries() {
        Comparable key = "bar";
        Args.nullNotPermitted(key, "key");
        Args.nullNotPermitted(data, "values");
        if (splitCnt < 1) {
            throw new IllegalArgumentException("The 'bins' value must be at least 1.");
        } else {
            double binWidth = (max - min) / (double) splitCnt;
            double lower = min;
            List binList = new ArrayList(splitCnt);

            int i;
            for (i = 0; i < splitCnt; ++i) {
                HistogramBin bin;
                if (i == splitCnt - 1) {
                    bin = new HistogramBin(lower, max);
                } else {
                    double upper = min + (double) (i + 1) * binWidth;
                    bin = new HistogramBin(lower, upper);
                    lower = upper;
                }

                binList.add(bin);
            }

            for (i = 0; i < data.length; ++i) {
                int binIndex = splitCnt - 1;
                if (data[i] < max) {
                    double fraction = (data[i] - min) / (max - min);
                    if (fraction < 0.0D) {
                        fraction = 0.0D;
                    }

                    binIndex = (int) (fraction * (double) splitCnt);
                    if (binIndex >= splitCnt) {
                        binIndex = splitCnt - 1;
                    }
                }

                HistogramBin bin = (HistogramBin) binList.get(binIndex);
//                System.out.println("bin with " + bin.getBinWidth() + " bin count " + bin.getCount() + " start" + bin.getStartBoundary() + " end" + bin.getEndBoundary());
                bin.incrementCount();
            }

            Map map = new HashMap();
            map.put("key", key);
            map.put("bins", binList);
            map.put("values.length", (double) data.length);
            map.put("bin width", binWidth);
//            List list = new ArrayList();
//            list.add(map);
            return map;
        }
    }

    private double rMetric(int curInt, int prevInt) {
        return Math.abs(epsilon * (double) (curInt - prevInt)) / Math.abs(max - min);
    }

    public double[] getPerc() {
        double[] res = new double[data.length];
        for (int i = 0; i < data.length - 1; i++) {
            res[i] = (int) ((data[i + 1] - data[i]) / epsilon);
        }
        return res;
    }

    public int[] getPerc(int dummy) {
        int[] res = new int[data.length];
        for (int i = 0; i < data.length - 1; i++) {
            res[i] = (int) ((data[i + 1] - data[i]) / epsilon);
        }
        return res;
    }

    public int getTrack(double[] data) {
        int res = 0;
        for (int i = 0; i < data.length; i++) {
            if (Math.abs(data[i]) > 0) {
                res += Math.abs(data[i]);
            } else {
                res += 1;
            }
        }
        return res;
    }

//    public void addSeries(Comparable key, double[] values, int bins) {
//
//        Args.nullNotPermitted(key, "key");
//        Args.nullNotPermitted(values, "values");
//        if (bins < 1) {
//            throw new IllegalArgumentException(
//                    "The 'bins' value must be at least 1.");
//        }
//        double binWidth = (max - min) / bins;
//
//        double lower = min;
//        double upper;
//        List<HistogramBin> binList = new ArrayList<>(bins);
//        for (int i = 0; i < bins; i++) {
//            HistogramBin bin;
//            // make sure bins[bins.length]'s upper boundary ends at maximum
//            // to avoid the rounding issue. the bins[0] lower boundary is
//            // guaranteed start from min
//            if (i == bins - 1) {
//                bin = new HistogramBin(lower, max);
//            }
//            else {
//                upper = min + (i + 1) * binWidth;
//                bin = new HistogramBin(lower, upper);
//                lower = upper;
//            }
//            binList.add(bin);
//        }
//        // fill the bins
//        for (double value : values) {
//            int binIndex = bins - 1;
//            if (value < max) {
//                double fraction = (value - min) / (max - min);
//                if (fraction < 0.0) {
//                    fraction = 0.0;
//                }
//                binIndex = (int) (fraction * bins);
//                // rounding could result in binIndex being equal to bins
//                // which will cause an IndexOutOfBoundsException - see bug
//                // report 1553088
//                if (binIndex >= bins) {
//                    binIndex = bins - 1;
//                }
//            }
//            HistogramBin bin = binList.get(binIndex);
//            bin.incrementCount();
//        }
//        // generic map for each series
//        Map<String, Object> map = new HashMap<>();
//        map.put("key", key);
//        map.put("bins", binList);
//        map.put("values.length", values.length);
//        map.put("bin width", binWidth);
//        this.list.add(map);
////        fireDatasetChanged();
//    }

    private double getMinimum() {
        if (data != null && data.length >= 1) {
            double min = 1.7976931348623157E308D;

            for (double aData : data) {
                if (aData < min) {
                    min = aData;
                }
            }
            return min;
        } else {
            throw new IllegalArgumentException("Null or zero length 'data' argument.");
        }
    }

    private double getMaximum() {
        if (data != null && data.length >= 1) {
            double max = -1.7976931348623157E308D;

            for (double aData : data) {
                if (aData > max) {
                    max = aData;
                }
            }
            return max;
        } else {
            throw new IllegalArgumentException("Null or zero length 'data' argument.");
        }
    }

    public void setSplitCnt(int splitCnt) {
        this.splitCnt = splitCnt;
        this.epsilon = (max - min) / splitCnt;
    }

}
