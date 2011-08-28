/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cwf.client.controllers;

import com.extjs.gxt.ui.client.event.EventType;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.google.gwt.core.client.GWT;
import java.util.List;
import org.cwf.client.AppMessages;
import org.cwf.client.M4waterAsyncCallback;
import org.cwf.client.RefreshableEvent;
import org.cwf.client.RefreshablePublisher;
import org.cwf.client.model.TicketSummary;
import org.cwf.client.model.WaterPointSummary;
import org.cwf.client.service.TicketSmsServiceAsync;
import org.cwf.client.service.WaterPointServiceAsync;
import org.cwf.client.views.HomeView;
import org.m4water.server.admin.model.Ticket;
import org.m4water.server.admin.model.Waterpoint;

/**
 *
 * @author victor
 */
public class HomeController extends Controller {

    AppMessages appMessages = GWT.create(AppMessages.class);
    public final static EventType HOME = new EventType();
    private HomeView homeView;
    TicketSmsServiceAsync ticketService;
    WaterPointServiceAsync waterpointService;

    public HomeController(TicketSmsServiceAsync aTicketService, WaterPointServiceAsync aWaterPointService) {
        super();
        ticketService = aTicketService;
        waterpointService = aWaterPointService;
        registerEventTypes(HOME);
    }

    @Override
    protected void initialize() {
        GWT.log("HomeController : initialize");
        homeView = new HomeView(this);
        RefreshablePublisher.get().subscribe(RefreshableEvent.Type.TICKET_UPDATE, homeView);
    }

    @Override
    public void handleEvent(AppEvent event) {
        GWT.log("HomeController:handle event");
        EventType type = event.getType();
        if (type == HOME) {
            forwardToView(homeView, event);
        }
    }

    public void forwardToEditWaterPoint(WaterPointSummary summary) {
        GWT.log("HomeController : forwardToEditWaterPoint");
        Dispatcher dispatcher = Dispatcher.get();
        AppEvent event = new AppEvent(EditWaterPointController.EDIT_WATER_POINT);
        event.setData(summary);
        dispatcher.dispatch(event);
    }

    public void forwardToViewTicketDetails(TicketSummary summary) {
        GWT.log("HomeController : forwardToViewTicketDetails");
        Dispatcher dispatcher = Dispatcher.get();
        AppEvent event = new AppEvent(TicketDetailsController.TICKET_DETAILS);
        event.setData(summary);
        dispatcher.dispatch(event);
    }

    public void getTickets() {
        ticketService.getTickets(new M4waterAsyncCallback<List<Ticket>>() {

            @Override
            public void onSuccess(List<Ticket> result) {
                homeView.setTickets(result);
                RefreshablePublisher.get().publish(new RefreshableEvent(RefreshableEvent.Type.TICKET_UPDATE, result));
            }
        });
    }

    public void getWaterPoints() {
        waterpointService.getWaterPoints(new M4waterAsyncCallback<List<Waterpoint>>() {

            @Override
            public void onSuccess(List<Waterpoint> result) {
               RefreshablePublisher.get().publish(new RefreshableEvent(RefreshableEvent.Type.WATER_POINT_DATA, result));
            }
        });
    }
}
