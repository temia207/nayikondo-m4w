/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cwf.client.views;

import org.cwf.client.views.widgets.WaterpointsTabPanel;
import com.extjs.gxt.ui.client.widget.CardPanel;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.layout.CardLayout;
import com.google.gwt.core.client.GWT;
import java.util.ArrayList;
import java.util.List;
import org.cwf.client.AppMessages;
import org.cwf.client.views.reports.FunctionalityView;

/**
 *
 * @author victor
 */
public class CenterHomePageView extends CardPanel {

    AppMessages appMessages = GWT.create(AppMessages.class);
    private CardLayout viewLayout;
    private List<ContentPanel> pages = new ArrayList<ContentPanel>();
    private int activePage = 0;
    private HomeView parentView;

    public CenterHomePageView(HomeView view) {
        this.parentView = view;
        initialize();
    }

    private void initialize() {
        GWT.log("CenterHomePageView : initialize");
        viewLayout = new CardLayout();

        pages = createPages();
        showView();
    }

    protected List<ContentPanel> createPages() {
        List<ContentPanel> containers = new ArrayList<ContentPanel>();
        //home page
        DashBoardView mainView = new DashBoardView();
        mainView.setWidth("100%");
        mainView.setHeading("Home");
//        containers.add(mainView);
        //ticket view
        TicketsTabPanel tickets = new TicketsTabPanel(parentView);
        tickets.setWidth("100%");
        containers.add(tickets);
        //all water points
        WaterpointsTabPanel waterPointsView = new WaterpointsTabPanel(parentView);
        waterPointsView.setWidth("100%");
        containers.add(waterPointsView);

		
        return containers;
    }

    public void showView() {
        setLayout(viewLayout);
        setActiveItem(0);
        setBorders(false);
        for (ContentPanel page : pages) {
            add(page);
        }
    }

    public void setActiveItem(int active) {
        viewLayout.setActiveItem(pages.get(active));
    }
}
