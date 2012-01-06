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
import org.cwf.client.service.ResponseTimeServiceAsync;
import org.cwf.client.views.reports.ReportsFrame;
import org.cwf.client.views.reports.functionality.FunctionalityParameter;
import org.m4water.server.admin.model.reports.ResponseTime;

/**
 *
 * @author victor
 */
public class ReportsController extends Controller {

	AppMessages appMessages = GWT.create(AppMessages.class);
	public final static EventType RESPONSE_TIME = new EventType();
	private ResponseTimeServiceAsync responseTimeAsync;
//	private FunctionalityParameter functionalityparemeter;
	private ReportsFrame reportsFrameView;

	public ReportsController(ResponseTimeServiceAsync aResponseTimeAsync) {
		super();
		this.responseTimeAsync = aResponseTimeAsync;
		registerEventTypes(RESPONSE_TIME);
	}

	@Override
	protected void initialize() {
		GWT.log("ReportsController : initialize");
		reportsFrameView = new ReportsFrame(this);
	}

	@Override
	public void handleEvent(AppEvent event) {
		GWT.log("HomeController:handle event");
		EventType type = event.getType();
		if (type == RESPONSE_TIME) {
			forwardToView(reportsFrameView, event);
		}
	}

    public void forwardToReportsView() {
        GWT.log("HomeController : forwardToViewTicketDetails");
        Dispatcher dispatcher = Dispatcher.get();
        AppEvent event = new AppEvent(ReportsController.RESPONSE_TIME);
        dispatcher.dispatch(event);
    }

	public void getResponseTime(String year, String district) {
		GWT.log("HomeController : getResponseTime()");
		responseTimeAsync.getResponseTimes(year, district, new M4waterAsyncCallback<List<ResponseTime>>() {

			@Override
			public void onSuccess(List<ResponseTime> result) {
				RefreshablePublisher.get().publish(new RefreshableEvent(RefreshableEvent.Type.RESPONSE_TIME,result));
			}
		});
	}
}
