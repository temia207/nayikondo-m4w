/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cwf.client.views.reports;

import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.View;
import com.extjs.gxt.ui.client.widget.CardPanel;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.layout.CardLayout;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Label;
import java.util.ArrayList;
import java.util.List;
import org.cwf.client.AppMessages;
import org.cwf.client.controllers.ReportsController;
import org.cwf.client.views.widgets.CenterLayoutPanel;
import org.m4water.server.admin.model.User;

/**
 *
 * @author victor
 */
public class ReportsFrame extends View implements ClickHandler {

	protected final AppMessages appMessages = GWT.create(AppMessages.class);
	private String heading;
	private Window window;
	public int mainWindowHeight;
	public int mainWindowWidth;
	private CardLayout wizardLayout;
	private CardPanel cardPanel;
	private List<ContentPanel> pages = new ArrayList<ContentPanel>();
	private CenterLayoutPanel centerLayoutPanel;
	public User loggedUser;

	public ReportsFrame(Controller controller) {
		super(controller);
	}

	@Override
	protected void initialize() {
		centerLayoutPanel = new CenterLayoutPanel("M4WATER Reports");
		wizardLayout = new CardLayout();
		cardPanel = new CardPanel();
		pages = createPages();
		window = new Window();
		window.setModal(true);
		window.setPlain(true);
		window.setMaximizable(true);
		window.setDraggable(true);
		window.setResizable(true);
		window.setScrollMode(Scroll.AUTOY);
		window.setLayout(new FitLayout());
		cardPanel.setLayout(wizardLayout);
		cardPanel.setBorders(false);
		for (ContentPanel page : pages) {
			cardPanel.add(page);
		}
//		window.add(cp);
		cardPanel.setActiveItem(pages.get(0));
//		centerpanel = new CenterHomePageView(this);
		centerLayoutPanel.addCenterPannel(cardPanel);
		centerLayoutPanel.getWestPanel().add(centerLayoutPanel.addLeftMenu(appMessages.functionality(), this));
		centerLayoutPanel.getWestPanel().add(centerLayoutPanel.addLeftMenu(appMessages.coverage(), this));
		centerLayoutPanel.getWestPanel().add(centerLayoutPanel.addLeftMenu(appMessages.management(), this));
		window.add(centerLayoutPanel);
	}

	public void showWindow(String heading, int width, int height) {
		GWT.log("ReportsFrame : createWindow");
		window.setSize(width, height);
		window.show();
	}

	protected List<ContentPanel> createPages() {
		List<ContentPanel> containers = new ArrayList<ContentPanel>();
		//functionality page
		FunctionalityView functionality = new FunctionalityView(this, "Functionality");
		functionality.setWidth("100%");
		containers.add(functionality);
		CoverageView coverageView = new CoverageView(this, "Coverage");
		coverageView.setWidth("100%");
		containers.add(coverageView);

		ManagementView managementView = new ManagementView(this,"Management");
		managementView.setWidth("100%");
		containers.add(managementView);
		return containers;
	}

	@Override
	protected void handleEvent(AppEvent event) {
		int width = com.google.gwt.user.client.Window.getClientWidth() - 50;
		int height = com.google.gwt.user.client.Window.getClientHeight() - 50;
		loggedUser = event.getData();
		showWindow("M4WATER Reports", width, height);
	}

	@Override
	public void onClick(ClickEvent event) {
		String txt = ((Label) event.getSource()).getText();
		if (txt.equals(appMessages.functionality())) {
			wizardLayout.setActiveItem(pages.get(0));
		} else if (txt.equals(appMessages.coverage())) {
			wizardLayout.setActiveItem(pages.get(1));
			((ReportsController)ReportsFrame.this.getController()).getDistrictSummaries();
		} else if (txt.equals(appMessages.management())) {
			wizardLayout.setActiveItem(pages.get(2));
			((ReportsController)ReportsFrame.this.getController()).getWucManagementReport();
		}
	}
}
