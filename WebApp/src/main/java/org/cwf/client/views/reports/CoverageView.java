/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cwf.client.views.reports;

import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.TabPanel;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.google.gwt.core.client.GWT;
import org.cwf.client.AppMessages;
import org.cwf.client.RefreshableEvent;
import org.cwf.client.RefreshablePublisher;
import org.cwf.client.controllers.ReportsController;
import org.cwf.client.views.reports.coverage.DistrictComparison;
import org.cwf.client.views.reports.coverage.ParishComparison;
import org.cwf.client.views.reports.coverage.SubcountyComparison;

/**
 *
 * @author victor
 */
public class CoverageView extends ContentPanel {

	final AppMessages appMessages = GWT.create(AppMessages.class);
	private TabPanel tabPanel;
	private ReportsFrame parentView;

	public CoverageView(String title) {
		initializeUI(title);
	}

	private void initializeUI(String title) {
		tabPanel = new TabPanel();
		tabPanel.setWidth(1000);
		tabPanel.setAutoHeight(true);
		setHeading(title);

		TabItem districtSummary = new TabItem("District Comparisons");
		districtSummary.setLayout(new FitLayout());
		districtSummary.addListener(Events.OnClick, new Listener<ComponentEvent>() {

			@Override
			public void handleEvent(ComponentEvent be) {
//				((ReportsController) parentView.getController()).forwardToReportsView();
			}
		});
		DistrictComparison page1 = new DistrictComparison();
		page1.setWidth("100%");
		districtSummary.add(page1);
		RefreshablePublisher.get().subscribe(RefreshableEvent.Type.DISTRICT_SUMMARIES,page1);


		TabItem subcountySummary = new TabItem("Subcounty Comparisons");
		subcountySummary.setLayout(new FitLayout());
		subcountySummary.addListener(Events.Select, new Listener<ComponentEvent>() {

			@Override
			public void handleEvent(ComponentEvent be) {
//				((ReportsController) parentView.getController()).forwardToReportsView();
			}
		});
		SubcountyComparison page2 = new SubcountyComparison();
		page2.setWidth("100%");
		subcountySummary.add(page2);
		RefreshablePublisher.get().subscribe(RefreshableEvent.Type.DISTRICT_SUMMARIES,page2);

		TabItem parishSummary = new TabItem("Parish Comparisons");
		parishSummary.setLayout(new FitLayout());
		ParishComparison page3 = new ParishComparison();
		page3.setWidth("100%");
		parishSummary.add(page3);
		RefreshablePublisher.get().subscribe(RefreshableEvent.Type.DISTRICT_SUMMARIES,page3);
		
		addTabItem(districtSummary);
		addTabItem(subcountySummary);
		addTabItem(parishSummary);
		add(tabPanel);
	}

	public void addTabItem(TabItem item) {
		tabPanel.add(item);
	}
}
