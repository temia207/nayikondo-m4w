/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cwf.client.views;

import com.extjs.gxt.ui.client.widget.CardPanel;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.layout.CardLayout;
import com.google.gwt.core.client.GWT;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author victor
 */
public class CenterHomePageView extends CardPanel {

    private CardLayout viewLayout;
    private List<ContentPanel> pages = new ArrayList<ContentPanel>();
    private int activePage = 0;

    public CenterHomePageView() {
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
        NewWaterPointsView newpoint = new NewWaterPointsView();
        newpoint.setWidth("100%");
        containers.add(newpoint);
        TicketsView tickets = new TicketsView();
        tickets.setWidth("100%");
        containers.add(tickets);
        return containers;
    }

//    protected abstract void display(int activePage, List<LayoutContainer> pages);
    public void showView() {
        setLayout(viewLayout);
        setActiveItem(0);
        setBorders(false);
        for (ContentPanel page : pages) {
            add(page);
        }
    }

    public void setActiveItem(int active) {
//        display(activePage, pages);
        viewLayout.setActiveItem(pages.get(active));
    }
}
