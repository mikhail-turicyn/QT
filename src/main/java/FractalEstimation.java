import org.jfree.chart.util.Args;
import org.jfree.data.statistics.HistogramBin;

import java.util.*;

public class FractalEstimation {
    private double epsilon;
    private double[] data;
    private int splitCnt;
    private double max;
    private double min;
    private List<Map<String, Object>> list;

    public FractalEstimation(double[] data, int splitCnt){
        this.data = data;
        this.splitCnt = splitCnt;
        max = getMaximum();
        min = getMinimum();
        this.epsilon = (max - min)/splitCnt;
    }

    public FractalEstimation(double[] data, double epsilon){
        this.data = data;
        this.epsilon = epsilon;
        this.splitCnt = (int)((getMaximum()-getMinimum())/epsilon);
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
        return res/ Math.log(epsilon);
    }

    private double[] getProbArray() {
        double[] res = new double[splitCnt];
        int curInt = 0;
        Arrays.sort(data);
        for (double el:data) {
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
        return epsilon * (curInt - prevInt) / (max - min);
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
