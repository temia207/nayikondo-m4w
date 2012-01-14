package org.cwf.client.views.reports.functionality;

import com.extjs.gxt.charts.client.Chart;
import com.extjs.gxt.charts.client.model.ChartModel;
import com.extjs.gxt.charts.client.model.axis.XAxis;
import com.extjs.gxt.charts.client.model.axis.YAxis;
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
		ChartModel cm = new ChartModel("Cost of repairs per district", "font-size: 14px; font-family: Verdana;");
		cm.setBackgroundColour("#ffffff");
		XAxis xa = new XAxis();
		xa.setLabels("Jan", "Feb", "Ma", "Apr", "May", "June", "July", "Aug", "Sept", "Oct", "Nov", "Dec");
		cm.setXAxis(xa);
		YAxis ya = new YAxis();
		ya.setRange(0, 1000000, 100000);
		cm.setYAxis(ya);
		AreaChart labourLine = new AreaChart();
		labourLine.setFillAlpha(0.3f);
		labourLine.setColour("#ff0000");
		labourLine.setFillColour("#ff0000");
		labourLine.setText("cost of Labour");
		int init = 50000;
		for (int n = 0; n < 12; n++) {
			init = init + 50000;
			labourLine.addValues(init);
		}
		cm.addChartConfig(labourLine);
		AreaChart materialLine = new AreaChart();
		materialLine.setFillAlpha(0.3f);
		materialLine.setColour("#00aa00");
		materialLine.setFillColour("#00aa00");
		int floor = Random.nextInt(3);
		double grade = (Random.nextInt(4) + 1) / 10.0;
		int init2 = 10000;
		materialLine.addValues(init2);
		materialLine.addValues(init2+10000);
		materialLine.addValues(init2+900000);
		materialLine.addValues(init2+10000);
		materialLine.addValues(init2+10000);
		materialLine.addValues(init2+70000);
		materialLine.addValues(init2-10000);
		materialLine.addValues(init2-10000);
		materialLine.addValues(init2-10000);
		materialLine.addValues(init2+50000);
		materialLine.addValues(init2+30000);
		materialLine.addValues(init2+10000);
		materialLine.setText("cost of materials");
		cm.addChartConfig(materialLine);
		return cm;

	}
}
