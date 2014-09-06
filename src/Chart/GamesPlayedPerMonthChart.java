package Chart;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.*;
import org.jfree.chart.labels.StandardXYSeriesLabelGenerator;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Created by philipp on 16.08.2014.
 */
public class GamesPlayedPerMonthChart extends JFrame {

    private Map<Date, Integer> map;
    private ChartPanel panel;
	private JTabbedPane panelz;
    public GamesPlayedPerMonthChart(Map<Date, Integer> map,JTabbedPane panelz)
    {
    	this.panelz = panelz;
        this.map = map;
        XYDataset dataset = createTimeSet();
        JFreeChart chart = createChart(dataset);
        panel = new ChartPanel(chart);
        panelz.addTab("GamesPlayer",panel);
        //panelz.setLocation(0, 0);
        
    }

    private JFreeChart createChart(XYDataset dataset) {

        final JFreeChart chart = ChartFactory.createTimeSeriesChart("TimeChart", "Zeit", "Anzahl", dataset,
                true,                     // include legend
                true,                     // tooltips?
                false                     // URLs?
        );
        XYPlot xyplot = (XYPlot)chart.getPlot();
        DateAxis dateaxis = (DateAxis)xyplot.getDomainAxis();
        dateaxis.setTickUnit(new DateTickUnit(DateTickUnitType.MONTH, 1, new SimpleDateFormat("MMM-yyyy")));
        dateaxis.setVerticalTickLabels(true);
        dateaxis.setMinimumDate(new Date(1287957630L*1000L));
        dateaxis.setMaximumDate(new Date());
        NumberAxis range = (NumberAxis) xyplot.getRangeAxis();
        range.setTickUnit(new NumberTickUnit(10));
        
        XYLineAndShapeRenderer xylineandshaperenderer = (XYLineAndShapeRenderer)xyplot.getRenderer();
        xylineandshaperenderer.setBaseShapesVisible(true);
        xylineandshaperenderer.setSeriesFillPaint(0, Color.red);
        xylineandshaperenderer.setSeriesFillPaint(1, Color.white);
        xylineandshaperenderer.setUseFillPaint(true);
        xylineandshaperenderer.setLegendItemToolTipGenerator(new StandardXYSeriesLabelGenerator("Tooltip {0}"));

        return chart;
    }

    private XYDataset createTimeSet() {

       
    	String Category = "Games Played";

        		TimeSeries series = new TimeSeries("Gaems Played",Month.class);
            	for (Entry<Date, Integer> datemap : map.entrySet())
                {
            		series.add(new Month(datemap.getKey()),datemap.getValue());
                }
       
        
        TimeSeriesCollection collection = new TimeSeriesCollection();
        collection.addSeries(series);
        return collection;
    }
    
    public void reloadChart(List names){
    	if(panelz.getTabCount() !=0)
    	{
    		XYDataset dataset = createTimeSet();
            JFreeChart chart = createChart(dataset);
            panel = new ChartPanel(chart);
            panelz.setComponentAt(2, panel);
       	 this.revalidate();
       	 this.repaint();
    	}
    	
    }


}
