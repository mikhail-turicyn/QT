import org.jfree.chart.util.Args;
import org.jfree.data.statistics.HistogramBin;

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
        setSplitCnt(splitCnt);
    }

    public FractalEstimation(double[] data, double epsilon){
        this.data = data;
        this.epsilon = epsilon;
        this.splitCnt = (int)((getMaximum()-getMinimum())/epsilon);
    }

    public void setSplitCnt(int splitCnt) {
        this.splitCnt = splitCnt;
        max = getMaximum();
        min = getMinimum();
        this.epsilon = (max - min) / splitCnt;

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
        Arrays.sort(tmpData);
        for (double el : tmpData) {
            if(el <= min + (double)(curInt+1)*epsilon){
                 res[curInt] += 1;
             } else {
                res[curInt] = res[curInt]/data.length;
                curInt += 1;
                res[curInt] += 1;
             }
        }
        res[curInt] = res[curInt]/data.length;
        return res;
    }

    public void addSeries(Comparable key, double[] values, int bins) {

        Args.nullNotPermitted(key, "key");
        Args.nullNotPermitted(values, "values");
        if (bins < 1) {
            throw new IllegalArgumentException(
                    "The 'bins' value must be at least 1.");
        }
        double binWidth = (max - min) / bins;

        double lower = min;
        double upper;
        List<HistogramBin> binList = new ArrayList<>(bins);
        for (int i = 0; i < bins; i++) {
            HistogramBin bin;
            // make sure bins[bins.length]'s upper boundary ends at maximum
            // to avoid the rounding issue. the bins[0] lower boundary is
            // guaranteed start from min
            if (i == bins - 1) {
                bin = new HistogramBin(lower, max);
            }
            else {
                upper = min + (i + 1) * binWidth;
                bin = new HistogramBin(lower, upper);
                lower = upper;
            }
            binList.add(bin);
        }
        // fill the bins
        for (double value : values) {
            int binIndex = bins - 1;
            if (value < max) {
                double fraction = (value - min) / (max - min);
                if (fraction < 0.0) {
                    fraction = 0.0;
                }
                binIndex = (int) (fraction * bins);
                // rounding could result in binIndex being equal to bins
                // which will cause an IndexOutOfBoundsException - see bug
                // report 1553088
                if (binIndex >= bins) {
                    binIndex = bins - 1;
                }
            }
            HistogramBin bin = binList.get(binIndex);
            bin.incrementCount();
        }
        // generic map for each series
        Map<String, Object> map = new HashMap<>();
        map.put("key", key);
        map.put("bins", binList);
        map.put("values.length", values.length);
        map.put("bin width", binWidth);
        this.list.add(map);
//        fireDatasetChanged();
    }

    private double rMetric(int curInt, int prevInt) {
//        System.out.println();
        return Math.abs(epsilon * (curInt - prevInt)) / Math.abs(max - min);
    }

    public double[] getPerc() {
        double[] res = new double[data.length];
        int curr = (int) (data[0] / epsilon);
        int next = 0;
        for (int i = 0; i < data.length - 1; i++) {
            next = (int) (data[i + 1] / epsilon);
            res[i] = next - curr;
            curr = next;
        }
        res[data.length - 1] = (int) (data[data.length - 1] / epsilon) - (int) (data[0] / epsilon);
        return res;
    }

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

}
