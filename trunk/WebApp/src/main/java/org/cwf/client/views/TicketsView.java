/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cwf.client.views;

import org.cwf.client.views.widgets.TicketDetailsPanel;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.TabPanel;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.google.gwt.core.client.GWT;
import org.cwf.client.AppMessages;

/**
 *
 * @author victor
 */
public class TicketsView extends ContentPanel {

    final AppMessages appMessages = GWT.create(AppMessages.class);
    private TabPanel ticketPanel;

    public TicketsView() {
        initialize();
    }

    private void initialize() {
        ticketPanel = new TabPanel();
        ticketPanel.setWidth(600);
        ticketPanel.setAutoHeight(true);
        TabItem openTickets = new TabItem("Open");
        openTickets.setLayout(new FitLayout());
        TicketDetailsPanel detailsPanel = new TicketDetailsPanel();
        detailsPanel.setHeaderVisible(false);
        openTickets.add(detailsPanel);
        TabItem closedTickets = new TabItem("Closed");
        TabItem suspendedTickets = new TabItem("Suspended");
        ticketPanel.add(openTickets);
        ticketPanel.add(closedTickets);
        ticketPanel.add(suspendedTickets);
        add(ticketPanel);
        setLayout(new FitLayout());
    }
}
