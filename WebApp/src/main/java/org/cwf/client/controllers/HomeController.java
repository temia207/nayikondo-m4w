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
import org.cwf.client.model.ProblemSummary;
import org.cwf.client.model.WaterPointModel;
import org.cwf.client.service.ProblemServiceAsync;
import org.cwf.client.service.WaterPointServiceAsync;
import org.cwf.client.util.ProgressIndicator;
import org.cwf.client.views.HomeView;
import org.m4water.server.admin.model.Problem;
import org.m4water.server.admin.model.WaterPointSummary;
import org.m4water.server.admin.model.Waterpoint;

/**
 *
 * @author victor
 */
public class HomeController extends Controller {

    AppMessages appMessages = GWT.create(AppMessages.class);
    public final static EventType HOME = new EventType();
    private HomeView homeView;
    ProblemServiceAsync ticketService;
    WaterPointServiceAsync waterpointService;

    public HomeController(ProblemServiceAsync aTicketService, WaterPointServiceAsync aWaterPointService) {
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

    public void forwardToEditWaterPoint(String waterPointId) {
        GWT.log("HomeController : forwardToEditWaterPoint");
        Dispatcher dispatcher = Dispatcher.get();
        AppEvent event = new AppEvent(EditWaterPointController.EDIT_WATER_POINT);
        event.setData(waterPointId);
        dispatcher.dispatch(event);
    }

    public void forwardToViewTicketDetails(ProblemSummary summary) {
        GWT.log("HomeController : forwardToViewTicketDetails");
        Dispatcher dispatcher = Dispatcher.get();
        AppEvent event = new AppEvent(TicketDetailsController.TICKET_DETAILS);
        event.setData(summary);
        dispatcher.dispatch(event);
    }

    public void getTickets() {
        GWT.log("HomeController : getTickets()");
        ticketService.getProblems(new M4waterAsyncCallback<List<Problem>>() {

            @Override
            public void onSuccess(List<Problem> result) {
                homeView.setTickets(result);
                RefreshablePublisher.get().publish(new RefreshableEvent(RefreshableEvent.Type.TICKET_UPDATE, result));
            }
        });
    }

    public void getWaterPointSummaries() {
        GWT.log("HomeController : getWaterPointSummaries()");
        ProgressIndicator.showProgressBar();
        waterpointService.getWaterPointSummaries(new M4waterAsyncCallback<List<WaterPointSummary>>() {

            @Override
            public void onSuccess(List<WaterPointSummary> result) {
                GWT.log("HomeController : waterpointsummarries found");
                RefreshablePublisher.get().publish(new RefreshableEvent(RefreshableEvent.Type.ALL_WATER_POINTS, result));
                ProgressIndicator.hideProgressBar();
             }
        });
    }

    public void getWaterPoints() {
        GWT.log("HomeController : getWaterPoints()");
        waterpointService.getWaterPoints(new M4waterAsyncCallback<List<Waterpoint>>() {

            @Override
            public void onSuccess(List<Waterpoint> result) {
                GWT.log("HomeController : waterpoints found");
                RefreshablePublisher.get().publish(new RefreshableEvent(RefreshableEvent.Type.ALL_WATER_POINTS, result));
                RefreshablePublisher.get().publish(new RefreshableEvent(RefreshableEvent.Type.NEW_WATER_POINTS, result));
            }
        });
    }
}
