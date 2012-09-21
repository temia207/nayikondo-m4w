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
import org.cwf.client.views.reports.coverage.DistrictComparison;
import org.cwf.client.views.reports.coverage.ParishComparison;
import org.cwf.client.views.reports.coverage.SubcountyComparison;
import org.cwf.client.views.reports.management.ServiceRepairs;
import org.cwf.client.views.reports.management.WUCManagementView;

/**
 *
 * @author victor
 */
public class ManagementView extends ContentPanel{

	final AppMessages appMessages = GWT.create(AppMessages.class);
	private TabPanel tabPanel;
	private ReportsFrame parentView;

	public ManagementView(String title) {
		initializeUI(title);
	}

	private void initializeUI(String title) {
		tabPanel = new TabPanel();
		tabPanel.setWidth(1000);
		tabPanel.setAutoHeight(true);
		setHeading(title);

		TabItem wucmanagement = new TabItem("WUC Management");
		wucmanagement.setLayout(new FitLayout());
		wucmanagement.addListener(Events.OnClick, new Listener<ComponentEvent>() {

			@Override
			public void handleEvent(ComponentEvent be) {
//				((ReportsController) parentView.getController()).forwardToReportsView();
			}
		});
		WUCManagementView page1 = new WUCManagementView ();
		page1.setWidth("100%");
		wucmanagement.add(page1);
		RefreshablePublisher.get().subscribe(RefreshableEvent.Type.WUC_MANAGEMENT, page1);

		TabItem servicerepairs = new TabItem("Service And Repairs");
		servicerepairs.setLayout(new FitLayout());
		servicerepairs.addListener(Events.Select, new Listener<ComponentEvent>() {

			@Override
			public void handleEvent(ComponentEvent be) {
//				((ReportsController) parentView.getController()).forwardToReportsView();
			}
		});
		ServiceRepairs  page2 = new ServiceRepairs();
		page2.setWidth("100%");
		servicerepairs.add(page2);
		RefreshablePublisher.get().subscribe(RefreshableEvent.Type.RESPONSE_TIME, page2);


		addTabItem(wucmanagement);
		addTabItem(servicerepairs);
		add(tabPanel);
	}

	public void addTabItem(TabItem item) {
		tabPanel.add(item);
	}
}
