package org.cwf.client.views.reports.functionality;

import com.extjs.gxt.charts.client.Chart;
import com.extjs.gxt.charts.client.model.ChartModel;
import com.extjs.gxt.charts.client.model.axis.XAxis;
import com.extjs.gxt.charts.client.model.charts.AreaChart;
import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.form.FieldSet;
import com.extjs.gxt.ui.client.widget.layout.FitData;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Random;
import org.cwf.client.AppMessages;
import org.cwf.client.IndexEntryPoint;

/**
 *
 * @author victor
 */
public class RepairCostsTabItem extends ContentPanel {

	final AppMessages appMessages = GWT.create(AppMessages.class);
//	private AreaChart areaChart;

	public RepairCostsTabItem() {
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
	private Command updateCmd;
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
		ChartModel cm = new ChartModel("Growth per Region", "font-size: 14px; font-family: Verdana;");
		cm.setBackgroundColour("#ffffff");
		XAxis xa = new XAxis();
		xa.setLabels("J", "F", "M", "A", "M", "J", "J", "A", "S", "O", "N", "D");
		cm.setXAxis(xa);
		AreaChart area1 = new AreaChart();
		area1.setFillAlpha(0.3f);
		area1.setColour("#ff0000");
		area1.setFillColour("#ff0000");
		for (int n = 0; n < 12; n++) {
			if (n % 3 != 0 && n != 11) {
				area1.addNullValue();
			} else {
				area1.addValues(n * Random.nextDouble());
			}
		}
		cm.addChartConfig(area1);
		AreaChart area2 = new AreaChart();
		area2.setFillAlpha(0.3f);
		area2.setColour("#00aa00");
		area2.setFillColour("#00aa00");
		int floor = Random.nextInt(3);
		double grade = (Random.nextInt(4) + 1) / 10.0;
		for (int n = 0; n < 12; n++) {
			if (n % 2 != 0 && n != 11) {
				area2.addNullValue();
			} else {
				area2.addValues(n * grade + floor);
			}
		}
		cm.addChartConfig(area2);
		return cm;

	}
}
