/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cwf.client.controllers;

import com.extjs.gxt.ui.client.event.EventType;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.google.gwt.core.client.GWT;
import org.cwf.client.AppMessages;
import org.cwf.client.M4waterAsyncCallback;
import org.cwf.client.service.ProblemServiceAsync;
import org.cwf.client.views.widgets.CommentView;
import org.m4water.server.admin.model.Problem;

/**
 *
 * @author victor
 */
public class CommentController extends Controller {

    private CommentView commentPanel;
    AppMessages appMessages = GWT.create(AppMessages.class);
    public final static EventType COMMENT = new EventType();
    ProblemServiceAsync ticketService;
    public CommentController(ProblemServiceAsync aTicketService) {
        super();
        ticketService = aTicketService;
        registerEventTypes(COMMENT);
    }

    @Override
    public void initialize() {
        GWT.log("CommentController  : initialize");
        commentPanel = new CommentView(this, "DWO Comment",appMessages.close());
    }

    @Override
    public void handleEvent(AppEvent event) {
        GWT.log("CommentController  :handle event");
        EventType type = event.getType();
        if (type == COMMENT) {
            forwardToView(commentPanel, event);
        }
    }

    public void saveTicket(Problem ticket) {
        GWT.log("TicketDetailsController  :saveTicket(Problem ticket)");
        ticketService.saveProblem(ticket, new M4waterAsyncCallback<Void>() {

            @Override
            public void onSuccess(Void result) {
                commentPanel.closeWindow();
            }
        });
    }
}
