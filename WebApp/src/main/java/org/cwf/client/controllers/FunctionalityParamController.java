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
import org.cwf.client.RefreshableEvent;
import org.cwf.client.RefreshablePublisher;
import org.cwf.client.service.ReportServiceAsync;
import org.cwf.client.views.reports.functionality.FunctionalityParameter;
import org.m4water.server.admin.model.reports.ResponseTime;

/**
 *
 * @author victor
 */
public class FunctionalityParamController extends Controller {

	AppMessages appMessages = GWT.create(AppMessages.class);
	public final static EventType FUNCTIONALITY_PARAM = new EventType();
	private FunctionalityParameter functionalityparemeter;
	private ReportServiceAsync reportsAsync;

	public FunctionalityParamController(ReportServiceAsync aResponseTimeAsync) {
		super();
		this.reportsAsync = aResponseTimeAsync;
		registerEventTypes(FUNCTIONALITY_PARAM);
	}

	@Override
	protected void initialize() {
		GWT.log("FunctionalityParamController : initialize");
		functionalityparemeter = new FunctionalityParameter(this);
	}

	@Override
	public void handleEvent(AppEvent event) {
		GWT.log("FunctionalityParamController:handle event");
		EventType type = event.getType();
		if (type == FUNCTIONALITY_PARAM) {
			forwardToView(functionalityparemeter, event);
		}
	}

	public void getResponseTime(String year, String district) {
		GWT.log("HomeController : getResponseTime()");
		reportsAsync.getResponseTimes(year, district, new M4waterAsyncCallback<List<ResponseTime>>() {

			@Override
			public void onSuccess(List<ResponseTime> result) {
				RefreshablePublisher.get().publish(new RefreshableEvent(RefreshableEvent.Type.RESPONSE_TIME,result));
			}
		});
	}
}
