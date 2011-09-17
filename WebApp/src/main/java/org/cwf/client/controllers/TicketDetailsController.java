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
import org.cwf.client.service.ProblemServiceAsync;
import org.cwf.client.views.TicketDetailsView;
import org.m4water.server.admin.model.Problem;

/**
 *
 * @author victor
 */
public class TicketDetailsController extends Controller {

    AppMessages appMessages = GWT.create(AppMessages.class);
    public final static EventType TICKET_DETAILS = new EventType();
    private TicketDetailsView ticketDetailsView;
    ProblemServiceAsync ticketService;

    public TicketDetailsController(ProblemServiceAsync aTicketService) {
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

    public void getTickets() {
        ticketService.getProblems(new M4waterAsyncCallback<List<Problem>>() {

            @Override
            public void onSuccess(List<Problem> result) {
                ticketDetailsView.setTickets(result);
            }
        });
    }

    public void saveTicket(Problem ticket) {
        GWT.log("TicketDetailsController  :saveTicket(Problem ticket)");
        ticketService.saveProblem(ticket,new M4waterAsyncCallback<Void>() {

            @Override
            public void onSuccess(Void result) {
                ticketDetailsView.closeWindow();
            }
        }
        );
    }
}
