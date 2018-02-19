import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class FractalEstimation {
    private double epsilon;
    private double[] data;
    int splitCnt;

    private FractalEstimation(){}

    public FractalEstimation(double[] data, int splitCnt){
        this.data = data;
        this.splitCnt = splitCnt;
        this.epsilon = (getMaximum()-getMinimum())/splitCnt;
    }

    public FractalEstimation(double[] data, double epsilon){
        this.data = data;
        this.epsilon = epsilon;
        this.splitCnt = (int)((getMaximum()-getMinimum())/epsilon);
    }

    private double probability(){
        for (int i = 0; i < splitCnt; i++) {
            
        }
        return 0;
    }

    public double bEntropy(){
        double tmpVal = 0;
        double res = 0;
        for (int i = 0; i < splitCnt; i++) {
            for (int j = 0; j < splitCnt; j++) {
                tmpVal = (1 - rMetric( data[i] , data[j], data.length)) * getP( j);
            }
            res += getP(i) * Math.log(tmpVal);
        }
        return res/ Math.log(epsilon);
    }
    private List getProbArray(ArrayList data, int i){
        List res = new ArrayList();
        data.stream()
                .sorted()
                .forEach(el-> System.out.println(el));
        return res;
    }

    private double getP( int i){return 0;}

    private double rMetric(double rCur, double rPrev, double size){
        return Math.abs(rCur-rPrev)/Math.abs(size);
    }

    private double getMinimum() {
        if (data != null && data.length >= 1) {
            double min = 1.7976931348623157E308D;

            for(int i = 0; i < data.length; ++i) {
                if (data[i] < min) {
                    min = data[i];
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

            for(int i = 0; i < data.length; ++i) {
                if (data[i] > max) {
                    max = data[i];
                }
            }
            return max;
        } else {
            throw new IllegalArgumentException("Null or zero length 'data' argument.");
        }
    }



}
