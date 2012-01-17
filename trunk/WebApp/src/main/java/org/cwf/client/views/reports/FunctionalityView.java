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
import org.cwf.client.controllers.FunctionalityParamController;
import org.cwf.client.controllers.HomeController;
import org.cwf.client.controllers.ReportsController;
import org.cwf.client.views.reports.functionality.BreakdownFrequency;
import org.cwf.client.views.reports.functionality.PerTechnology;
import org.cwf.client.views.reports.functionality.RepairCostsTabItem;
import org.cwf.client.views.reports.functionality.ResponseTimeTabItem;

/**
 *
 * @author victor
 */
public class FunctionalityView  extends ContentPanel{

	final AppMessages appMessages = GWT.create(AppMessages.class);
	private TabPanel tabPanel;
	private ReportsFrame parentView;
	public FunctionalityView(ReportsFrame view,String title) {
		parentView = view;
		initializeUI();
	}

	private void initializeUI() {
        tabPanel = new TabPanel();
        tabPanel.setWidth(1000);
        tabPanel.setAutoHeight(true);
        setHeading(appMessages.functionality());
		TabItem repaircostsTab = new TabItem("Repair Costs");
		repaircostsTab.setLayout(new FitLayout());
		repaircostsTab.addListener(Events.OnClick, new Listener<ComponentEvent>(){

			@Override
			public void handleEvent(ComponentEvent be) {
				((ReportsController)parentView.getController()).forwardToReportsView();
			}
		});
		RepairCostsTabItem page1 = new RepairCostsTabItem();
		page1.setWidth("100%");
		repaircostsTab.add(page1);


		TabItem responseTimeTab = new TabItem("Response Time");
		responseTimeTab.setLayout(new FitLayout());
		responseTimeTab.addListener(Events.Select, new Listener<ComponentEvent>(){

			@Override
			public void handleEvent(ComponentEvent be) {
				((ReportsController)parentView.getController()).forwardToReportsView();
			}
		});
		ResponseTimeTabItem page2 = new ResponseTimeTabItem();
		page2.setWidth("100%");
		responseTimeTab.add(page2);
		RefreshablePublisher.get().subscribe(RefreshableEvent.Type.RESPONSE_TIME,page2);

		TabItem breakDownFreqTab = new TabItem("Breakdown Frequency");
		breakDownFreqTab.setLayout(new FitLayout());
		BreakdownFrequency page3 = new BreakdownFrequency();
		page3.setWidth("100%");
		breakDownFreqTab.add(page3);

		TabItem perTechTab = new TabItem("Functionality Per technology");
		repaircostsTab.setLayout(new FitLayout());
		PerTechnology page4 = new PerTechnology();
		page4.setWidth("100%");
		perTechTab.add(page4);

		addTabItem(repaircostsTab);
		addTabItem(responseTimeTab);
		addTabItem(breakDownFreqTab);
		addTabItem(perTechTab);
		add(tabPanel);
	}


	public void addTabItem(TabItem item) {
//		item.setLayout(new FitLayout());
		tabPanel.add(item);
	}

}
