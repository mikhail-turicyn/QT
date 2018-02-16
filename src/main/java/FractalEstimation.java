public final class FractalEstimation {

    private FractalEstimation(){}

    private static double rMetric(double rCur, double rPrev, double size){
        return Math.abs(rCur-rPrev)/Math.abs(size);
    }
    private static double probability(double[] data, int a){
        return 0;
    }

    public static double bEntropy(double[] data, int splitCnt){
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data.length; j++) {
                final double v = 1 - probability(data, 100);
            }
        }
        return 0;
    }
    private double getMinMaxIDs(double[] values){
        int maxID = 0;
        int minID = 0;
        for (int i = 0; i < values.length; i++) {
            if (values[i] > values[maxID]){
                maxID = i;
            } else if(values[i] < values[minID]){
                minID = i;
            }
        }
        return 0;
    }
    private double getMinimum(double[] values) {
        if (values != null && values.length >= 1) {
            double min = 1.7976931348623157E308D;

            for(int i = 0; i < values.length; ++i) {
                if (values[i] < min) {
                    min = values[i];
                }
            }

            return min;
        } else {
            throw new IllegalArgumentException("Null or zero length 'values' argument.");
        }
    }

    private double getMaximum(double[] values) {
        if (values != null && values.length >= 1) {
            double max = -1.7976931348623157E308D;

            for(int i = 0; i < values.length; ++i) {
                if (values[i] > max) {
                    max = values[i];
                }
            }

            return max;
        } else {
            throw new IllegalArgumentException("Null or zero length 'values' argument.");
        }
    }



}
