package org.cwf.client.views;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.View;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.google.gwt.user.client.ui.Label;
import com.extjs.gxt.ui.client.widget.custom.Portal;
import com.extjs.gxt.ui.client.widget.custom.Portlet;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.VBoxLayoutData;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
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
import org.cwf.client.views.widgets.CenterLayoutPanel;
import org.m4water.server.admin.model.Problem;
import org.m4water.server.admin.model.User;
import org.m4water.server.admin.model.WaterPointSummary;

/**
 *
 * @author victor
 */
public class HomeView extends View implements Refreshable,ClickHandler {

	final AppMessages appMessages = GWT.create(AppMessages.class);
	private CenterLayoutPanel centerLayoutPanel;
	private ContentPanel cpWest;
	private ContentPanel cpCenter;
	private Portlet portlet;
	public List<Problem> tickets = new ArrayList<Problem>();
	public List<WaterPointSummary> waterPointSummary = new ArrayList<WaterPointSummary>();
	private CenterHomePageView centerpanel;
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
		centerLayoutPanel = new CenterLayoutPanel("Home View");
		centerpanel = new CenterHomePageView(this);
		centerLayoutPanel.addCenterPannel(centerpanel);
		centerLayoutPanel.getWestPanel().add(centerLayoutPanel.addLeftMenu(appMessages.tickets(), this));
		centerLayoutPanel.getWestPanel().add(centerLayoutPanel.addLeftMenu(appMessages.allWaterPoints(), this));
		centerLayoutPanel.getWestPanel().add(centerLayoutPanel.addLeftMenu(appMessages.reports(),this));
		centerLayoutPanel.getWestPanel().add(centerLayoutPanel.addLeftMenu(appMessages.users(),this));
		centerLayoutPanel.getWestPanel().add(centerLayoutPanel.addLeftMenu(appMessages.settings(),this));
		VBoxLayoutData vBoxData = new VBoxLayoutData(5, 5, 5, 5);
		vBoxData.setFlex(1);
//        container.add(cp, new FlowData(10));
		portlet = new Portlet(new FitLayout());
//        portlet.setHeading("CWF home");
		portlet.setHeaderVisible(false);
		portlet.add(centerLayoutPanel);
		portlet.setScrollMode(Scroll.AUTOY);
		portlet.setSize(725, 200);
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
			centerLayoutPanel.setHeading(centerLayoutPanel.getHeading() + ":" + loggedinUser.getSubcounty().getCounty().getDistrict().getName() + " District");
			HomeController controller2 = (HomeController) HomeView.this.getController();
			controller2.getTickets();
			controller2.getWaterPointSummaries(loggedinUser.getSubcounty().getCounty().getDistrict().getName());
			controller2.getBaselineSetDate();
			controller2.getNewWaterPoints();
		}

	}

	private void maximisePortlet(Portlet portlet) {
		GWT.log("Home view: maximisePortlet");
		Portal p = (Portal) portlet.getParent().getParent();
		int height = p.getHeight() - 20;
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
			controller2.getWaterPointSummaries(loggedinUser.getSubcounty().getCounty().getDistrict().getName());
			controller2.getBaselineSetDate();
			controller2.getNewWaterPoints();
		} else if (event.getEventType() == RefreshableEvent.Type.RELOAD) {
			GWT.log("HomeView Refresh:Refreshing the UI");
			HomeController controller2 = (HomeController) HomeView.this.getController();
			controller2.getTickets();
			controller2.getWaterPointSummaries(loggedinUser.getSubcounty().getCounty().getDistrict().getName());
			controller2.getBaselineSetDate();
			controller2.getNewWaterPoints();
		}
	}

	@Override
	public void onClick(ClickEvent event) {
				String txt = ((Label)event.getSource()).getText();
                if (txt.equals(appMessages.allWaterPoints())) {
                    centerpanel.setActiveItem(1);
                } else if (txt.equals("Tickets")) {
                    centerpanel.setActiveItem(0);
                } else if(txt.equals("Reports")){
//					((HomeController) HomeView.this.getController()).forwardToReportsView();
				}
	}
}
