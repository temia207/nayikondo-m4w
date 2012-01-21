/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cwf.client.views.reports.functionality;

import com.extjs.gxt.charts.client.Chart;
import com.extjs.gxt.charts.client.model.ChartModel;
import com.extjs.gxt.charts.client.model.axis.XAxis;
import com.extjs.gxt.charts.client.model.axis.YAxis;
import com.extjs.gxt.charts.client.model.charts.BarChart;
import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.form.FieldSet;
import com.extjs.gxt.ui.client.widget.layout.FitData;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.google.gwt.core.client.GWT;
import org.cwf.client.AppMessages;
import org.cwf.client.IndexEntryPoint;

/**
 *
 * @author victor
 */
public class BreakdownFrequency extends ContentPanel {

	final AppMessages appMessages = GWT.create(AppMessages.class);

	public BreakdownFrequency() {
		initialize();
	}

	private void initialize() {
		setBodyBorder(true);
		setHeading("Repair Costs");
		setButtonAlign(HorizontalAlignment.CENTER);
		setLayout(new FitLayout());
		setSize(600, 300);
		add(getChart());
	}


	private int numSegments = 5;
	private LayoutContainer getChart() {
		FieldSet fs = new FieldSet();
		fs.setHeading("Chart");
		fs.setLayout(new FitLayout());

		String url = !IndexEntryPoint.isExplorer() ? "../../" : "";
		url += "gxt/chart/open-flash-chart.swf";

		final Chart chart = new Chart(url);
		chart.setHeight(250);
		chart.setBorders(true);
		fs.add(chart, new FitData(0, 0, 20, 0));
		chart.setChartModel(getChartModel(numSegments));
//		adjustUpdateSpeed(updateSpeed);
		return fs;
	}

	public ChartModel getChartModel(int segments) {
		ChartModel cm = new ChartModel("Frequency Of BreakDowns", "font-size: 14px; font-family: Verdana;");
		cm.setBackgroundColour("#ffffff");
		XAxis xa = new XAxis();
		xa.setLabels("Jan", "Feb", "Ma", "Apr", "May", "June", "July", "Aug", "Sept", "Oct", "Nov", "Dec");
		cm.setXAxis(xa);
		YAxis ya = new YAxis();
		ya.setRange(0, 1000, 100);
		cm.setYAxis(ya);
		BarChart materialLine = new BarChart();
		int init2 = 200;
		materialLine.setColour("#ff0000");
		materialLine.addValues(init2);
		materialLine.addValues(init2+100);
		materialLine.addValues(init2+90);
		materialLine.addValues(init2+300);
		materialLine.addValues(init2+10);
		materialLine.addValues(init2+700);
		materialLine.addValues(init2+100);
		materialLine.addValues(init2+300);
		materialLine.addValues(init2+400);
		materialLine.addValues(init2+500);
		materialLine.addValues(init2+250);
		materialLine.addValues(init2+450);
		cm.addChartConfig(materialLine);
		return cm;

	}
}
