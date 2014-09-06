package Chart;

import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.Dataset;

/**
 * Created by philipp.hentschel on 04.09.2014.
 */
public interface DataChart {
     JFreeChart createChart(Dataset dataset);
     CategoryDataset createDataSet();
}
