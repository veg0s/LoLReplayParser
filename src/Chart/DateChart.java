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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Created by philipp on 16.08.2014.
 */
public class DateChart extends JFrame {

    private Map<String, Map<Date, Integer>> map;
    private ChartPanel panel;
	private JTabbedPane panelz;
    public DateChart(Map<String, Map<Date, Integer>> map,JTabbedPane panelz)
    {
    	this.panelz = panelz;
        this.map = map;
        XYDataset dataset = createTimeSet(new ArrayList());
        JFreeChart chart = createChart(dataset);
        panel = new ChartPanel(chart);
        panelz.addTab("TimeChart",panel);
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
        range.setTickUnit(new NumberTickUnit(1));
        
        XYLineAndShapeRenderer xylineandshaperenderer = (XYLineAndShapeRenderer)xyplot.getRenderer();
        xylineandshaperenderer.setBaseShapesVisible(true);
        xylineandshaperenderer.setSeriesFillPaint(0, Color.red);
        xylineandshaperenderer.setSeriesFillPaint(1, Color.white);
        xylineandshaperenderer.setUseFillPaint(true);
        xylineandshaperenderer.setLegendItemToolTipGenerator(new StandardXYSeriesLabelGenerator("Tooltip {0}"));

        return chart;
    }

    private XYDataset createTimeSet(List champs) {
        List<TimeSeries> timeseriesMap = new ArrayList<TimeSeries>();
       
    	String Category = "Champs";
        for (Map.Entry<String,Map<Date,Integer>> entry : map.entrySet())
        {
        	if(champs.contains(entry.getKey().toString()) || champs.size() == 0)
        	{
        		TimeSeries series = new TimeSeries(entry.getKey(),Month.class);
            	for (Entry<Date, Integer> datemap : entry.getValue().entrySet())
                {
            		series.add(new Month(datemap.getKey()),datemap.getValue());
                }
            	timeseriesMap.add(series);
        	}
        	
        }
       
        
        TimeSeriesCollection collection = new TimeSeriesCollection();
        for(TimeSeries series : timeseriesMap)
        	collection.addSeries(series);
        return collection;
    }
    
    public void reloadChart(List names)
    {
    	if(panelz.getTabCount() !=0)
    	{
    		XYDataset dataset = createTimeSet(names);
            JFreeChart chart = createChart(dataset);
            panel = new ChartPanel(chart);
            panelz.setComponentAt(1, panel);
       	 this.revalidate();
       	 this.repaint();
    	}
    	
    }


}
