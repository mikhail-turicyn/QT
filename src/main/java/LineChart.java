import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.data.category.DefaultCategoryDataset;

public class LineChart extends ApplicationFrame {
    public LineChart(double[] data, String applicationTitle, String chartTitle) {
        super(applicationTitle);
        JFreeChart lineChart = ChartFactory.createLineChart(
                chartTitle,
                "t", "x(t)",
                createDataset(data),
                PlotOrientation.VERTICAL,
                true, true, false);

        ChartPanel chartPanel = new ChartPanel(lineChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
        setContentPane(chartPanel);
//        int width = 11640;    /* Width of the image */
//        int height = 11480;   /* Height of the image */
//        File lineChartFile = new File( "LineChart.jpeg" );
//        try {
//            ChartUtils.saveChartAsJPEG(lineChartFile ,lineChart, width ,height);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    private DefaultCategoryDataset createDataset(double[] data) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i = 0; i < data.length; i++) {
            dataset.addValue(data[i], "value", "" + i);
        }
        return dataset;
    }
}
