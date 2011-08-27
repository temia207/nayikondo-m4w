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
import org.cwf.client.AppMessages;
import org.cwf.client.model.TicketSummary;
import org.cwf.client.model.WaterPointSummary;
import org.cwf.client.views.HomeView;

/**
 *
 * @author victor
 */
public class HomeController extends Controller {

    AppMessages appMessages = GWT.create(AppMessages.class);
    public final static EventType HOME = new EventType();
    private HomeView homeView;

    public HomeController() {
        super();
        registerEventTypes(HOME);
    }

    @Override
    protected void initialize() {
        GWT.log("HomeController : initialize");
        homeView = new HomeView(this);
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
}
