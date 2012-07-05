/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cwf.client.views.verticaltabs;

import com.extjs.gxt.ui.client.Style.Scroll;
import org.cwf.client.custom.widget.VerticalTabItem;
import org.cwf.client.views.HomeView;
import org.cwf.client.views.TicketsTabPanel;

/**
 *
 * @author victor
 */
public class TicketsTab extends VerticalTabItem {

    private HomeView parentView;

    public TicketsTab(String title, HomeView parentView) {
        super(title);
        addStyleName("pad-text");
        setScrollMode(Scroll.AUTO);
        this.parentView = parentView;
        addMyWidget();

    }

    private void addMyWidget() {
        TicketsTabPanel tickets = new TicketsTabPanel(parentView);
        tickets.setWidth("100%");
        add(tickets);
    }
}
