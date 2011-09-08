package org.cwf.client.views;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.View;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.util.Padding;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.custom.Portal;
import com.extjs.gxt.ui.client.widget.custom.Portlet;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.VBoxLayout;
import com.extjs.gxt.ui.client.widget.layout.VBoxLayout.VBoxLayoutAlign;
import com.extjs.gxt.ui.client.widget.layout.VBoxLayoutData;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Label;
import java.util.ArrayList;
import java.util.List;
import org.cwf.client.AppMessages;
import org.cwf.client.IndexEntryPoint;
import org.cwf.client.Refreshable;
import org.cwf.client.RefreshableEvent;
import org.cwf.client.controllers.HomeController;
import org.cwf.client.model.ProblemSummary;
import org.m4water.server.admin.model.Problem;

/**
 *
 * @author victor
 */
public class HomeView extends View implements Refreshable {

    final AppMessages appMessages = GWT.create(AppMessages.class);
    private ContentPanel cpWest;
    private ContentPanel cpCenter;
    private Portlet portlet;
    public List<Problem> tickets = new ArrayList<Problem>();
    private CenterHomePageView centerpanel;

    public HomeView(Controller controller) {
        super(controller);
    }

    @Override
    public void initialize() {
        initUi();
    }

    public void initUi() {
        GWT.log("Home view: init UI");
        centerpanel = new CenterHomePageView(this);
        ContentPanel cp = new ContentPanel();
        cp.setHeading("Home View");
        cp.setSize(600, 500);
        cp.setLayout(new BorderLayout());
        cpWest = new ContentPanel();
        cpWest.setHeaderVisible(false);
        VBoxLayout westLayout = new VBoxLayout();
        westLayout.setPadding(new Padding(5));
        westLayout.setVBoxLayoutAlign(VBoxLayoutAlign.STRETCH);
        cpWest.setLayout(westLayout);
        BorderLayoutData west = new BorderLayoutData(LayoutRegion.WEST, 150, 100, 250);
        west.setMargins(new Margins(5));
        west.setSplit(true);
        cpWest.add(addLeftMenu("Home"));
        cpWest.add(addLeftMenu("New water Points"));
        cpWest.add(addLeftMenu("Water Points"));
        cpWest.add(addLeftMenu("Tickets"));
        cpWest.add(addLeftMenu("Reports"));
        cpWest.add(addLeftMenu("Users"));
        cpWest.add(addLeftMenu("Settings"));
        //==========
        cp.add(cpWest, west);
        cpCenter = new ContentPanel();
        cpCenter.setHeaderVisible(false);
        cpCenter.setLayout(new FitLayout());
        cpCenter.add(centerpanel);
        BorderLayoutData center = new BorderLayoutData(LayoutRegion.CENTER);
        center.setMargins(new Margins(5));
        cp.add(cpCenter, center);

        VBoxLayoutData vBoxData = new VBoxLayoutData(5, 5, 5, 5);
        vBoxData.setFlex(1);
//        container.add(cp, new FlowData(10));
        portlet = new Portlet(new FitLayout());
//        portlet.setHeading("CWF home");
        portlet.setHeaderVisible(false);
        portlet.add(cp);
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
            HomeController controller2 = (HomeController) HomeView.this.getController();
            controller2.getTickets();
            controller2.getWaterPoints();
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

    public void showTicketDetails(ProblemSummary summary) {
        HomeController controller2 = (HomeController) HomeView.this.getController();
        controller2.forwardToViewTicketDetails(summary);
    }

    public FocusPanel addLeftMenu(String name) {
        final FocusPanel panel = new FocusPanel();
        panel.setSize("200px", "20px");
        panel.addStyleName("focusPanel");
        final Label label = new Label(name);
        label.addStyleName("leftmenu-text");
        panel.add(label);
        label.addMouseOverHandler(new MouseOverHandler() {

            @Override
            public void onMouseOver(MouseOverEvent event) {
                label.setStyleName("leftMenuover");
//                panel.setStyleName("focusPanel-over");
            }
        });
        label.addMouseOutHandler(new MouseOutHandler() {

            @Override
            public void onMouseOut(MouseOutEvent event) {
                label.removeStyleName("leftMenuover");
                label.addStyleName("leftmenu-text");
            }
        });
        label.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                String txt = label.getText();
                if (txt.equals("New water Points")) {
                    centerpanel.setActiveItem(0);
                } else if (txt.equals("Water Points")) {
                    centerpanel.setActiveItem(1);
                } else if (txt.equals("Tickets")) {
                    centerpanel.setActiveItem(2);

                }
            }
        });
        return panel;
    }

    @Override
    public void refresh(RefreshableEvent event) {
        if (event.getEventType() == RefreshableEvent.Type.TICKET_UPDATE) {
            System.out.println("tttttttttttttttttttttttttttttttttttttttttttttttttttt");
        }
    }
}
