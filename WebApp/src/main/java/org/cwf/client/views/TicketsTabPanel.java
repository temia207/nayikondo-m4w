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
import org.cwf.client.RefreshableEvent;
import org.cwf.client.RefreshablePublisher;

/**
 *
 * @author victor
 */
public class TicketsTabPanel extends ContentPanel {

    final AppMessages appMessages = GWT.create(AppMessages.class);
    private TabPanel ticketPanel;
    private HomeView parentView;

    public TicketsTabPanel(HomeView view) {
        this.parentView = view;
        initialize();
    }

    private void initialize() {
        ticketPanel = new TabPanel();
        ticketPanel.setWidth("100%");
        ticketPanel.setAutoHeight(true);
        setHeading(appMessages.ticketSummary());

        TabItem openTickets = new TabItem("Open");
        openTickets.setLayout(new FitLayout());
        TicketDetailsPanel openTicketsItem = new TicketDetailsPanel(parentView,appMessages.open());
        openTicketsItem.setHeaderVisible(false);
        RefreshablePublisher.get().subscribe(RefreshableEvent.Type.TICKET_UPDATE,openTicketsItem);
        openTickets.add(openTicketsItem);

        TabItem closedTickets = new TabItem("Closed");
        closedTickets.setLayout(new FitLayout());
        TicketDetailsPanel closedTicketsItem = new TicketDetailsPanel(parentView,appMessages.closed());
        closedTicketsItem.setHeaderVisible(false);
        RefreshablePublisher.get().subscribe(RefreshableEvent.Type.TICKET_UPDATE,closedTicketsItem);
        closedTickets.add(closedTicketsItem);

        TabItem suspendedTickets = new TabItem("Suspended");
        suspendedTickets.setLayout(new FitLayout());
        TicketDetailsPanel suspendedTicketsItem = new TicketDetailsPanel(parentView,appMessages.suspended());
        suspendedTicketsItem.setHeaderVisible(false);
        RefreshablePublisher.get().subscribe(RefreshableEvent.Type.TICKET_UPDATE,suspendedTicketsItem);
        suspendedTickets.add(suspendedTicketsItem);

        ticketPanel.add(openTickets);
        ticketPanel.add(closedTickets);
        ticketPanel.add(suspendedTickets);
        add(ticketPanel);
        setLayout(new FitLayout());
    }
}
