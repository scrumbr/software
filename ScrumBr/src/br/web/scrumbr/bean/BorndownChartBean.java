package br.web.scrumbr.bean;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

@ManagedBean
public class BorndownChartBean {
	 
//	    private LineChartModel lineModel1;
//	     
//	    @PostConstruct
//	    public void init() {
//	        createLineModels();
//	    }
//	 
//	    public LineChartModel getLineModel1() {
//	        return lineModel1;
//	    }
//	 
//	     
//	    private void createLineModels() {
//	        lineModel1 = initLinearModel();
//	        lineModel1.setTitle("Burndown chart");
//	        lineModel1.setLegendPosition("e");
//	        lineModel1.setAnimate(true);
//	        Axis yAxis = lineModel1.getAxis(AxisType.Y);
//	        Axis xAxis = lineModel1.getAxis(AxisType.X);
//	        
//	        xAxis.setMin(10);
//	        yAxis.setMin(0);
//	        yAxis.setMax(10);
//	    }
//	     
//	    private LineChartModel initLinearModel() {
//	        LineChartModel model = new LineChartModel();
//	 
//	        LineChartSeries series1 = new LineChartSeries();
//	        series1.setLabel("Linha ideal");
//	 
//	        series1.set(0, 10);
//	        series1.set(10, 0);
//	 
//	        LineChartSeries series2 = new LineChartSeries();
//	        series2.setLabel("Status da Sprint");
//	 
//	        series2.set("10/10/15", 10);
//	        series2.set(2, 1);
//	        series2.set(3, 3);
//	        series2.set(4, 6);
//	        series2.set(5, 8);
//	        model.addSeries(series1);
//	        model.addSeries(series2);
//	         
//	        return model;
//	    }
//	
	private LineChartModel dateModel;
	 
    @PostConstruct
    public void init() {
        createDateModel();
    }
 
    public LineChartModel getDateModel() {
        return dateModel;
    }
     
    private void createDateModel() {
        dateModel = new LineChartModel();
        LineChartSeries series1 = new LineChartSeries();
        series1.setLabel("Series 1");
 
        series1.set("2013-12-20", 100);
        series1.set("2014-02-14", 0);
 
        LineChartSeries series2 = new LineChartSeries();
        series2.setLabel("Series 2");
 
        series2.set("2013-12-20", 80);
        series2.set("2014-01-06", 73);
        series2.set("2014-01-12", 24);
        series2.set("2014-01-18", 12);
        series2.set("2014-01-24", 74);
        series2.set("2014-01-30", 12);
 
        dateModel.addSeries(series1);
        dateModel.addSeries(series2);
         
        dateModel.setTitle("Gráfico Burndown Chart");
        dateModel.setZoom(true);
        dateModel.getAxis(AxisType.Y).setLabel("Horas da Sprint");
        dateModel.setAnimate(true);
        
        dateModel.getAxis(AxisType.Y).setMax(100);
        dateModel.getAxis(AxisType.Y).setMin(0);
        dateModel.getAxis(AxisType.X).setMin(100);
        
        DateAxis axis = new DateAxis("Datas da Sprint");
        axis.setTickAngle(-50);
        axis.setMax("2014-02-01");
        axis.setTickFormat("%b %#d, %y");
         
        dateModel.getAxes().put(AxisType.X, axis);
    }
}
