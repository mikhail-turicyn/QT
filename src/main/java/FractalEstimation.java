import org.jfree.chart.util.Args;
import org.jfree.data.statistics.HistogramBin;
import org.jfree.data.statistics.HistogramDataset;

import java.util.*;

public class FractalEstimation {
    double epsilon;
    private double[] data;
    private int splitCnt;
    private double max;
    private double min;
    private List<Map<String, Object>> list;

    public FractalEstimation(double[] data, int splitCnt){
        this.data = data;
        this.splitCnt = splitCnt;
        this.max = getMaximum();
        this.min = getMinimum();
        this.epsilon = (max - min) / splitCnt;
    }

    public FractalEstimation(double[] data, double epsilon){
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
                tmpVal += (1 - rMetric(i, j)) * prob[j];
            }
            res += prob[i] * Math.log(tmpVal);
        }
//        System.out.println("B-entropy geometrical " + -res);
        return res/ Math.log(epsilon);
    }

    double bEntropyInf() {
        double tmpVal = 0;
        double res = 0;
        double[] prob = getProbArray();
        for (int i = 0; i < splitCnt; i++) {
            for (int j = 0; j < splitCnt; j++) {
                tmpVal += (1 - Math.abs(prob[i] - prob[j])) * prob[j];
            }
            res += prob[i] * Math.log(tmpVal);
        }
//        System.out.println("B-entropy inf" + -res);
        return res / Math.log(epsilon);
    }

    public double[] getProbArray() {
        double[] res = new double[splitCnt];
        double[] tmpData = Arrays.copyOf(data, data.length);
        int curInt = 0;
        int elCount = 0;
        Arrays.sort(tmpData);
        for (int i = 0; i < tmpData.length ; i++) {
            if(tmpData[i] <= min + (double)(curInt+1)*epsilon){
                elCount += 1;
            } else {
                res[curInt] = (double)(elCount) / (double)(data.length);
                curInt += 1;
                elCount = 1;
            }
        }
        res[curInt] = (double)(elCount) / (double)(data.length);
        return res;
    }

    public double[] TestProbArray() {
        HistChart histChart = new HistChart("temp");
        HistogramDataset testSet = histChart.createDataset(data, splitCnt);
        double[] res = new double[splitCnt];
        double[] tmpData = Arrays.copyOf(data, data.length);
        int curInt = 0;
        int elCount = 0;
        Arrays.sort(tmpData);
        for (int i = 0; i < tmpData.length - 1 ; i++) {
            if(tmpData[i] < min + (double)(curInt+1)*epsilon){
                elCount += 1;
            } else {
                res[curInt] = (double)(elCount) / (double)(data.length);
                System.out.println("моя вероятность" + res[curInt] + "вероятность либы" + testSet.getY(0, curInt));
                curInt += 1;
                elCount = 1;
            }
        }
        res[curInt-1] = (double)(elCount+1) / (double)(data.length);
        System.out.println("моя вероятность" + res[curInt-1] + "вероятность либы" + testSet.getY(0, curInt-1));
        return res;
    }

    public List addSeries() {
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
                System.out.println("bin with " + bin.getBinWidth() + " bin count " + bin.getCount() + " start" + bin.getStartBoundary() + " end" + bin.getEndBoundary());
                bin.incrementCount();
            }
//            (HistogramBin)binList.get(1).
//            for(HistogramBin el:binList){
//                System.out.println(el.getStartBoundary());
//            }

            Map map = new HashMap();
            map.put("key", key);
            map.put("bins", binList);
            map.put("values.length", new Integer(data.length));
            map.put("bin width", new Double(binWidth));
            List list = new ArrayList();
            list.add(map);
            return list;
        }
    }

    private double rMetric(int curInt, int prevInt) {
        return Math.abs(epsilon * (curInt - prevInt)) / Math.abs(max - min);
    }

    public double[] getPerc() {
        double[] res = new double[data.length];
//        int curr = (int) (data[0] / epsilon);
//        int next = 0;
        for (int i = 0; i < data.length - 1; i++) {
//            next = (int) (data[i + 1] / epsilon);
//            res[i] = next - curr;
//            curr = next;
            res[i] = (int) ((data[i + 1] - data[i]) / epsilon);
        }
//        res[data.length - 1] = (int) (data[data.length - 1] / epsilon) - (int) (data[0] / epsilon);
        return res;
    }

    public int getTrack(double[] data) {
        int res = 0;
        for (int i = 0; i < data.length; i++) {
            if (Math.abs(data[i]) > 0){
                res += Math.abs(data[i]);
            }else{
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
