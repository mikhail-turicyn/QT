//import org.jfree.chart.ChartFactory;
//import org.jfree.chart.ChartPanel;
//import org.jfree.chart.ChartUtils;
//import org.jfree.chart.JFreeChart;
//import org.jfree.chart.axis.NumberTickUnit;
//import org.jfree.chart.renderer.xy.VectorRenderer;
//import org.jfree.chart.ui.ApplicationFrame;
//import org.jfree.data.statistics.HistogramDataset;
//import org.jfree.data.xy.XYDataset;
//import org.jfree.data.xy.XYSeries;
//import org.jfree.data.xy.XYSeriesCollection;
//
//import java.io.File;
//import java.io.IOException;
//
//import static org.jfree.data.statistics.HistogramType.RELATIVE_FREQUENCY;
//
//public class XYchart extends Chart {
//    public XYchart(String title,
//                   double[] xData,
//                   double[] yData,
//                   String chartTitle) {
//        this(title, xData, yData, chartTitle,"","",null);
//    }
//    public XYchart(String title,
//                   double[] xData,
//                   double[] yData,
//                   String chartTitle,
//                   String xAxisLabel,
//                   String yAxisLabel,
//                   NumberTickUnit tickUnit) {
//        super(title,xData, yData,xAxisLabel, yAxisLabel,chartTitle,tickUnit);
////        JFreeChart chart = createChart(chartTitle, xAxisLabel, yAxisLabel,createDataset(xData, yData), tickUnit);
//        saveAsFile(chart);
//    }
//}