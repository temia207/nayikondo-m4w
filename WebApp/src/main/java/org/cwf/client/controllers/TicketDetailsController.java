/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cwf.client.controllers;

import com.extjs.gxt.ui.client.event.EventType;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.google.gwt.core.client.GWT;
import java.util.List;
import org.cwf.client.AppMessages;
import org.cwf.client.M4waterAsyncCallback;
import org.cwf.client.model.UserSummary;
import org.cwf.client.service.TicketSmsServiceAsync;
import org.cwf.client.views.TicketDetailsView;
import org.m4water.server.admin.model.Ticket;

/**
 *
 * @author victor
 */
public class TicketDetailsController extends Controller {

    AppMessages appMessages = GWT.create(AppMessages.class);
    public final static EventType TICKET_DETAILS = new EventType();
    private TicketDetailsView ticketDetailsView;
    TicketSmsServiceAsync ticketService;

    public TicketDetailsController(TicketSmsServiceAsync aTicketService) {
        super();
        ticketService = aTicketService;
        registerEventTypes(TICKET_DETAILS);
    }

    @Override
    public void initialize() {
        GWT.log("TicketDetailsController  : initialize");
        ticketDetailsView = new TicketDetailsView(this);
    }

    @Override
    public void handleEvent(AppEvent event) {
        GWT.log("TicketDetailsController  :handle event");
        EventType type = event.getType();
        if (type == TICKET_DETAILS) {
            forwardToView(ticketDetailsView, event);
        }
    }
    //in future this should be a call back to get users from the database

    public void getUsers() {
        ticketDetailsView.setUsers(UserSummary.getSampleUsers());
    }
    public void getTickets(){
    ticketService.getTickets(new M4waterAsyncCallback<List<Ticket>>() {

            @Override
            public void onSuccess(List<Ticket> result) {
                ticketDetailsView.setTickets(result);
            }
        });
    }
}
