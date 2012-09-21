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
import org.cwf.client.service.ReportServiceAsync;
import org.cwf.client.views.reports.ReportsFrame;
import org.m4water.server.admin.model.reports.DistrictComparisons;
import org.m4water.server.admin.model.reports.WucManagement;

/**
 *
 * @author victor
 */
public class ReportsController extends Controller {

    AppMessages appMessages = GWT.create(AppMessages.class);
    public final static EventType RESPONSE_TIME = new EventType();
    private ReportServiceAsync reportsAsync;
    private ReportsFrame reportsFrameView;

    public ReportsController(ReportServiceAsync aResponseTimeAsync) {
        super();
        this.reportsAsync = aResponseTimeAsync;
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
        GWT.log("ReportsController : forwardToReportsDetails");
        Dispatcher dispatcher = Dispatcher.get();
        AppEvent event = new AppEvent(FunctionalityParamController.FUNCTIONALITY_PARAM);
        dispatcher.dispatch(event);
    }

    public void getDistrictSummaries() {
        GWT.log("ReportsController:getDistrictSummaries");
        reportsAsync.getDistrictSummaries(new M4waterAsyncCallback<List<DistrictComparisons>>() {

            @Override
            public void onSuccess(List<DistrictComparisons> result) {
                RefreshablePublisher.get().publish(new RefreshableEvent(RefreshableEvent.Type.DISTRICT_SUMMARIES, result));
            }
        });
    }

    public void getWucManagementReport() {
        GWT.log("ReportsController:getWucmanagementReport");
        reportsAsync.getWucManagementReport(new M4waterAsyncCallback<List<WucManagement>>() {

            @Override
            public void onSuccess(List<WucManagement> result) {
                RefreshablePublisher.get().publish(new RefreshableEvent(RefreshableEvent.Type.WUC_MANAGEMENT, result));
            }
        });
    }
}
