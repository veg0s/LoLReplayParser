package Chart;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.Dataset;

import javax.swing.*;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by philipp on 16.08.2014.
 */
public class Chart extends JFrame implements DataChart {

    private Map<String,Integer> map;

    public Chart(Map<String,Integer> map,JTabbedPane panelz)
    {
        this.map = sortByValues(map);
        CategoryDataset dataset = createDataSet();
        JFreeChart chart = createChart(dataset);
        ChartPanel panel = new ChartPanel(chart);
        panelz.addTab("BarChart",panel);
        //panelz.setLocation(0, 0);
        
    }

    public JFreeChart createChart(Dataset dataset) {

        final JFreeChart chart = ChartFactory.createBarChart(
                "Champs",         // chart title
                "Champs",               // domain axis label
                "Played",                  // range axis label
                (CategoryDataset) dataset,                  // data
                PlotOrientation.VERTICAL, // orientation
                true,                     // include legend
                true,                     // tooltips?
                false                     // URLs?
        );

        return chart;
    }

    public CategoryDataset createDataSet() {
        String Category = "Champs";
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Map.Entry<String, Integer> entry : map.entrySet())
        {
            dataset.addValue(entry.getValue(),entry.getKey(),Category);
        }
        return dataset;
    }

    public static  <K, V extends Comparable<V>> Map<K, V> sortByValues(final Map<K, V> map) {
        Comparator<K> valueComparator = new Comparator<K>() {
            public int compare(K k1, K k2) {
                int compare = map.get(k2).compareTo(map.get(k1));
                if (compare == 0) return 1;
                else return compare;
            }
        };
        Map<K, V> sortedByValues = new TreeMap<K, V>(valueComparator);
        sortedByValues.putAll(map);
        return sortedByValues;
    }
}
