package org.cwf.client.views;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.View;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.custom.Portal;
import com.extjs.gxt.ui.client.widget.custom.Portlet;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.VBoxLayoutData;
import com.google.gwt.core.client.GWT;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.cwf.client.AppMessages;
import org.cwf.client.IndexEntryPoint;
import org.cwf.client.Refreshable;
import org.cwf.client.RefreshableEvent;
import org.cwf.client.RefreshablePublisher;
import org.cwf.client.controllers.HomeController;
import org.cwf.client.model.ProblemSummary;
import org.m4water.server.admin.model.Problem;
import org.m4water.server.admin.model.User;
import org.m4water.server.admin.model.WaterPointSummary;

/**
 *
 * @author victor
 */
public class HomeView extends View implements Refreshable{

	final AppMessages appMessages = GWT.create(AppMessages.class);
	public  String districtName;
	private Portlet portlet;
	public List<Problem> tickets = new ArrayList<Problem>();
	public List<WaterPointSummary> waterPointSummary = new ArrayList<WaterPointSummary>();
        private M4WaterHomeViewTabPanel verticalTabPanel;
	public Date baselineSetDate;
	public User loggedinUser;

	public HomeView(Controller controller) {
		super(controller);
		RefreshablePublisher.get().subscribe(RefreshableEvent.Type.RELOAD_WATERPOINTS, HomeView.this);
		RefreshablePublisher.get().subscribe(RefreshableEvent.Type.RELOAD, HomeView.this);
	}

	@Override
	public void initialize() {
		initUi();
	}

	public void initUi() {
		GWT.log("Home view: init UI");
                verticalTabPanel = new M4WaterHomeViewTabPanel(this);
                verticalTabPanel.setWidth("100%");
		VBoxLayoutData vBoxData = new VBoxLayoutData(5, 5, 5, 5);
		vBoxData.setFlex(1);
		portlet = new Portlet(new FitLayout());
		portlet.setHeaderVisible(false);
		portlet.add(verticalTabPanel);
		portlet.setScrollMode(Scroll.AUTOY);
		portlet.setSize(725, 100);
		portlet.addStyleName("portlet-border");
	}

	@Override
	protected void handleEvent(AppEvent event) {
		GWT.log("Home view: Handle event");
		if (event.getType() == HomeController.HOME) {
			Portal portal = Registry.get(IndexEntryPoint.PORTAL);
			portal.add(portlet, 0);
			maximisePortlet(portlet);
			loggedinUser = event.getData();
			districtName = loggedinUser.getSubcounty().getCounty().getDistrict().getName();
			verticalTabPanel.setHeading(verticalTabPanel.getHeading() + ":" + districtName + " District");
			HomeController controller2 = (HomeController) HomeView.this.getController();
			controller2.getBaselineSetDate();
			controller2.getTickets();
			controller2.getNewWaterPoints();
		}

	}

	private void maximisePortlet(Portlet portlet) {
		GWT.log("Home view: maximisePortlet");
		Portal p = (Portal) portlet.getParent().getParent();
		int height = p.getHeight()-20;
		portlet.setHeight(height);
	}

	public void setTickets(List<Problem> tickets) {
		this.tickets = tickets;
	}

	public void setBaselineSetDate(Date date) {
		this.baselineSetDate = date;
	}

	public void showTicketDetails(ProblemSummary summary) {
		HomeController controller2 = (HomeController) HomeView.this.getController();
		controller2.forwardToViewTicketDetails(summary);
	}

	@Override
	public void refresh(RefreshableEvent event) {
		if (event.getEventType() == RefreshableEvent.Type.TICKET_UPDATE) {
			System.out.println("tttttttttttttttttttttttttttttttttttttttttttttttttttt");
		} else if (event.getEventType() == RefreshableEvent.Type.RELOAD_WATERPOINTS) {
			GWT.log("HomeView Refresh:Reloading all waterpoints");
			HomeController controller2 = (HomeController) HomeView.this.getController();
//			controller2.getWaterPointSummaries(loggedinUser.getSubcounty().getCounty().getDistrict().getName());
			controller2.getBaselineSetDate();
			controller2.getNewWaterPoints();
		} else if (event.getEventType() == RefreshableEvent.Type.RELOAD) {
			GWT.log("HomeView Refresh:Refreshing the UI");
			HomeController controller2 = (HomeController) HomeView.this.getController();
			controller2.getTickets();
//			controller2.getWaterPointSummaries(loggedinUser.getSubcounty().getCounty().getDistrict().getName());
			controller2.getBaselineSetDate();
			controller2.getNewWaterPoints();
		}
	}

}
