import org.jfree.chart.util.Args;
import org.jfree.data.statistics.HistogramBin;
import org.jfree.data.statistics.HistogramDataset;

import java.util.*;

public class FractalEstimation {
    double spread;
    private double epsilon;
    private double[] data;
    private double[] prob;
    private int splitCnt;
    private double max;
    private double min;

    public FractalEstimation(double[] data, int splitCnt){
        this.data = data;
        this.max = getMaximum();
        this.min = getMinimum();
        this.spread = max - min;
        setSplitCnt(splitCnt);
    }

    public FractalEstimation(double[] data, double epsilon){
        this.data = data;
        this.max = getMaximum();
        this.min = getMinimum();
        this.spread = max - min;
        setEpsilon(epsilon);
    }

    double bEntropy() {
        double tmpVal;
        double res = 0;
        for (int i = 0; i < splitCnt; i++) {
            tmpVal = 0;
            for (int j = 0; j < splitCnt; j++) {
                tmpVal += (1.0 - rMetric(i, j)) * prob[j];
            }
            res += prob[i] * Math.log(tmpVal);
        }
        return res/ Math.log(epsilon);
    }

    double testEntropy() {
        double tmpVal = 0;
        double res = 0;
        for (int i = 0; i < splitCnt; i++) {
            for (int j = 0; j < splitCnt; j++) {
                tmpVal += (1.0 - rMetric(i, j)) * prob[j];
            }
            res += prob[i] * Math.log(tmpVal);
        }
        return res / Math.log(epsilon);
    }

    double bEntropyInf() {
        double tmpVal;
        double res = 0;
        for (int i = 0; i < splitCnt; i++) {
            tmpVal = 0;
            for (int j = 0; j < splitCnt; j++) {
                tmpVal += (1.0 - Math.abs(prob[i] - prob[j])) * prob[j];
            }
            res += prob[i] * Math.log(tmpVal);
        }
        return res / Math.log(epsilon);
    }

    public double[] getProbArray() {
        double[] res = new double[splitCnt];
        Map series = addSeries();
        List<HistogramBin> binList = (ArrayList) series.get("bins");
        int i = 0;
        for (HistogramBin bin : binList) {
            res[i] = (double) bin.getCount() / (double) series.get("values.length");
            i++;
        }
        return res;
    }

    public double[] TestProbArray2() {
        HistChart histChart = new HistChart("temp");
        HistogramDataset testSet = histChart.createDataset(data, splitCnt);
        double[] res = new double[splitCnt];
        Map series = addSeries();
        List<HistogramBin> binList = (ArrayList) series.get("bins");
        int curInt = 0;
        for (HistogramBin bin : binList) {
            res[curInt] = (double) bin.getCount() / (double) series.get("values.length");
//            System.out.println("моя вероятность" + res[curInt] + "вероятность либы" + testSet.getY(0, curInt));
            curInt++;
        }
        return res;
    }

    public strictfp double[] myGetProbArray() {
        double[] res = new double[splitCnt];
        double[] tmpData = Arrays.copyOf(data, data.length);
        int curInt = 0;
        int elCount = 0;
        Arrays.sort(tmpData);
        for (int i = 0; i < tmpData.length - 1; i++) {
            if (tmpData[i] < min + (double) (curInt + 1) * epsilon) {
                elCount += 1;
            } else {
                res[curInt] = (double) (elCount) / (double) (data.length);
                curInt += 1;
                elCount = 1;
            }
        }
        curInt -= 1;
        res[curInt] = (double) (elCount + 1) / (double) (data.length);
        return res;
    }

    public Map addSeries() {
        Comparable key = "bar";
        Args.nullNotPermitted(key, "key");
        Args.nullNotPermitted(data, "values");
        if (splitCnt < 1) {
            throw new IllegalArgumentException("The 'bins' value must be at least 1.");
        } else {
            double binWidth = spread / (double) splitCnt;
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
                    double fraction = (data[i] - min) / spread;
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
            return map;
        }
    }

    public List<HistogramBin> addListSeries() {
        if (splitCnt < 1) {
            throw new IllegalArgumentException("The 'bins' value must be at least 1.");
        } else {
            double binWidth = spread / (double) splitCnt;
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
                    double fraction = (data[i] - min) / spread;
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
//            map.put("key", key);
//            map.put("bins", binList);
//            map.put("values.length", (double) data.length);
            map.put("bin width", binWidth);
            return binList;
        }
    }

    private double rMetric(int curInt, int prevInt) {
        return Math.abs(epsilon * (double) (curInt - prevInt)) / spread;
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

    public int getTrack(double[] percolationArray) {
        int res = 0;
        for (int i = 0; i < percolationArray.length; i++) {
            if (Math.abs(percolationArray[i]) > 0) {
                res += Math.abs(percolationArray[i]);
            }else{
                res += 1;
            }
        }
        return res;
    }

    public int getAgregates(double[] percolationArray) {
        List resList = new ArrayList();
        int res = 0;
        for (int i = 0; i < percolationArray.length; i++) {
            if (Math.abs(percolationArray[i]) > 0) {
                res += Math.abs(percolationArray[i]);
            } else {
                res += 1;
            }
            double binWidth = spread / (double) splitCnt;
            double lower = min;
            List binList = new ArrayList(splitCnt);

//            int i;
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
        }
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

    public double getEpsilon() {
        return epsilon;
    }

    public void setEpsilon(double epsilon) {
        this.epsilon = epsilon;
        this.splitCnt = (int) (spread / epsilon);
        this.prob = getProbArray();
    }

    public int getSplitCnt() {
        return splitCnt;
    }

    public void setSplitCnt(int splitCnt) {
        this.splitCnt = splitCnt;
        this.epsilon = spread / splitCnt;
        this.prob = getProbArray();
    }
}
