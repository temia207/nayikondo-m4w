/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cwf.client.controllers;

import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.event.EventType;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.cwf.client.AppMessages;
import org.cwf.client.IndexEntryPoint;
import org.cwf.client.M4waterAsyncCallback;
import org.cwf.client.RefreshableEvent;
import org.cwf.client.RefreshablePublisher;
import org.cwf.client.model.ProblemSummary;
import org.cwf.client.model.NewWaterpointSummary;
import org.cwf.client.model.WaterPointModel;
import org.cwf.client.service.ProblemServiceAsync;
import org.cwf.client.service.ReportServiceAsync;
import org.cwf.client.service.SettingServiceAsync;
import org.cwf.client.service.WaterPointServiceAsync;
import org.cwf.client.service.YawlServiceAsync;
import org.cwf.client.util.PagingUtil;
import org.cwf.client.util.ProgressIndicator;
import org.cwf.client.views.HomeView;
import org.m4water.server.admin.model.Exportable;
import org.m4water.server.admin.model.Problem;
import org.m4water.server.admin.model.SettingGroup;
import org.m4water.server.admin.model.User;
import org.m4water.server.admin.model.WaterPointSummary;
import org.m4water.server.admin.model.Waterpoint;
import org.m4water.server.admin.model.paging.FilterComparison;
import org.m4water.server.admin.model.paging.FilterConfig;
import org.m4water.server.admin.model.paging.PagingLoadResult;
import org.m4water.server.admin.model.reports.ResponseTime;

/**
 *
 * @author victor
 */
public class HomeController extends Controller {

    AppMessages appMessages = GWT.create(AppMessages.class);
    public final static EventType HOME = new EventType();
    public static int NEW_WATER_POINTS = 0;
    public static int ALL_WATERPOINTS = 1;
    public static int BASELINE_PENDING = 2;
    public static int BASELINE_DONE = 3;
    public static int BASELINE_NOT_DONE = 4;
    private HomeView homeView;
    ProblemServiceAsync ticketService;
    WaterPointServiceAsync waterpointService;
    YawlServiceAsync yawlService;
    SettingServiceAsync settingService;
    ReportServiceAsync reportServiceAsync;

    public HomeController(ProblemServiceAsync aTicketService, WaterPointServiceAsync aWaterPointService,
            YawlServiceAsync aYawlService, SettingServiceAsync aSettingService, ReportServiceAsync aResponseTimeAsync) {
        super();
        ticketService = aTicketService;
        waterpointService = aWaterPointService;
        yawlService = aYawlService;
        settingService = aSettingService;
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

    public void forwardToEditNewWaterPoint(NewWaterpointSummary model) {
        GWT.log("HomeController : forwardToEditWaterPoint(WaterPointModel model)");
        Dispatcher dispatcher = Dispatcher.get();
        AppEvent event = new AppEvent(EditNewWaterPointController.EDIT_NEW_WATER_POINT);
        event.setData(model);
        dispatcher.dispatch(event);
    }

    public void forwardToViewTicketDetails(ProblemSummary summary) {
        GWT.log("HomeController : forwardToViewTicketDetails");
        Dispatcher dispatcher = Dispatcher.get();
        AppEvent event = new AppEvent(TicketDetailsController.TICKET_DETAILS);
        event.setData(summary);
        dispatcher.dispatch(event);
    }

    public void forwardToReportsView(User user) {
        GWT.log("HomeController : forwardToViewTicketDetails");
        Dispatcher dispatcher = Dispatcher.get();
        AppEvent event = new AppEvent(ReportsController.RESPONSE_TIME);
        event.setData(user);
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

//    public void getWaterPointSummaries(String district) {
//        GWT.log("HomeController : getWaterPointSummaries()");
//        ProgressIndicator.showProgressBar();
//        waterpointService.getWaterPointSummaries(district, new M4waterAsyncCallback<List<WaterPointSummary>>() {
//
//            @Override
//            public void onSuccess(List<WaterPointSummary> result) {
//                GWT.log("HomeController : waterpointsummarries found");
//                //this is still too slow yet we dont want to use paging..
//                RefreshablePublisher.get().publish(new RefreshableEvent(RefreshableEvent.Type.ALL_WATER_POINTS, result));
//                ProgressIndicator.hideProgressBar();
//            }
//        });
//    }

    public void getNewWaterPoints() {
        GWT.log("HomeController : getNewWaterPoints()");
        settingService.getSettingGroup("waterpoints", new M4waterAsyncCallback<SettingGroup>() {

            @Override
            public void onSuccess(SettingGroup result) {
                GWT.log("HomeController : waterpointsummarries found");
                RefreshablePublisher.get().publish(new RefreshableEvent(RefreshableEvent.Type.NEW_WATER_POINTS, result));
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
//                RefreshablePublisher.get().publish(new RefreshableEvent(RefreshableEvent.Type.NEW_WATER_POINTS, result));
            }
        });
    }

    public void getBaselineSetDate() {
        GWT.log("HomeController : getBaselineSetDate()");
        waterpointService.getBaselineSetDate(new M4waterAsyncCallback<Date>() {

            @Override
            public void onSuccess(Date result) {
                homeView.setBaselineSetDate(result);
            }
        });
    }

    public void launchBaseline(final String waterPointId) {
        ProgressIndicator.showProgressBar();
        yawlService.launchWaterPointBaseline(waterPointId, new M4waterAsyncCallback<Void>() {

            @Override
            public void onSuccess(Void result) {
                ProgressIndicator.hideProgressBar();
                MessageBox.alert("Info", "Workflow Lanched For ID: " + waterPointId, null);
            }

            @Override
            public void onFailurePostProcessing(Throwable throwable) {
                ProgressIndicator.hideProgressBar();
            }
        });
    }

    public void cancelCase(final String caseId) {
        ProgressIndicator.showProgressBar();
        yawlService.cancelCase(caseId, new M4waterAsyncCallback<Void>() {

            @Override
            public void onSuccess(Void result) {
                ProgressIndicator.hideProgressBar();
                MessageBox.alert("Info", "Baseline Review Canceled: Yawl CaseID:" + caseId, null);
            }

            @Override
            public void onFailurePostProcessing(Throwable throwable) {
                ProgressIndicator.hideProgressBar();
            }
        });
    }

    public void updateLocalWaterPointSummarries(final String id, final List<WaterPointSummary> summaries) {
        Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {

            @Override
            public void execute() {
                ProgressIndicator.showProgressBar();
                for (WaterPointSummary summary : summaries) {
                    if (summary.getWaterPointId().equalsIgnoreCase(id)) {
                        summary.setBaselinePending("T");
                    }
                }
                RefreshablePublisher.get().publish(new RefreshableEvent(RefreshableEvent.Type.ALL_WATER_POINTS, summaries));
                ProgressIndicator.hideProgressBar();
            }
        });
    }

    public void getResponseTime(String year, String district) {
        GWT.log("HomeController : getResponseTime()");
        reportServiceAsync.getResponseTimes(year, district, new M4waterAsyncCallback<List<ResponseTime>>() {

            @Override
            public void onSuccess(List<ResponseTime> result) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        });
    }

    public void exportEditable(int type, final String fileName) {

        ProgressIndicator.showProgressBar();
        try {

            String url = "actions/itemexport?";
            url += "type=" + type;
            url += "&filename=" + fileName;

            IndexEntryPoint.openWindow(url);

            ProgressIndicator.hideProgressBar();
        } catch (Exception ex) {
            MessageBox.alert(appMessages.error(), "Problem encounterd while exporting item", null);
            ex.printStackTrace();
            ProgressIndicator.hideProgressBar();
        }
    }
//
//    /*
//     * gets waterpoint summarries accoring baseline status
//     * eg prividing a value "T" will return baseline pending,"F"
//     * will return those which have not carried out baseline
//     * else will return those with baseline done.
//     */
//    public void getWaterPointSummaries(String district, final String baseLineDone) {
//        GWT.log("HomeController : getWaterPointSummaries()");
//        ProgressIndicator.showProgressBar();
//        waterpointService.getWaterPointSummaries(district, baseLineDone, new M4waterAsyncCallback<List<WaterPointSummary>>() {
//
//            @Override
//            public void onSuccess(List<WaterPointSummary> result) {
//                GWT.log("HomeController : waterpointsummarries found");
//                //this is still too slow yet we dont want to use paging..
//                if (baseLineDone.equals("T")) {
//                    RefreshablePublisher.get().publish(new RefreshableEvent(RefreshableEvent.Type.BASELINE_PENDING, result));
//                } else if (baseLineDone.equals("F")) {
//                    RefreshablePublisher.get().publish(new RefreshableEvent(RefreshableEvent.Type.BASELINE_NOT_DONE, result));
//                } else {
//                    RefreshablePublisher.get().publish(new RefreshableEvent(RefreshableEvent.Type.BASELINE_COMPLETE, result));
//                }
//                ProgressIndicator.hideProgressBar();
//            }
//        });
//    }

    public void getPagedWaterPointSummaries(PagingLoadConfig pagingLoadConfig, String district,
            final String baseLineDone,Date baselineDate,
            final AsyncCallback<com.extjs.gxt.ui.client.data.PagingLoadResult<WaterPointModel>> callback) {

        org.m4water.server.admin.model.paging.PagingLoadConfig searchPagingLoadConfig = PagingUtil.createPagingLoadConfig(pagingLoadConfig);
        GWT.log("getWaterPointSummaries(String district, final String baseLineDone)"+baseLineDone);
        if (district != null) {
            searchPagingLoadConfig.addFilter(new FilterConfig("district", district,
                    FilterComparison.EQUAL_TO));
        }
        if (baseLineDone.equalsIgnoreCase("T")) {
            //pending baseline
            searchPagingLoadConfig.addFilter(new FilterConfig("baselinePending", baseLineDone,
                    FilterComparison.EQUAL_TO));
        }else if (baseLineDone.equalsIgnoreCase("C")&&  baselineDate!= null) {
            //baseline complete
            searchPagingLoadConfig.addFilter(new FilterConfig("baselineDate", baselineDate,
                    FilterComparison.GREATER_THAN));
        }else if (baseLineDone.equalsIgnoreCase("F")) {
            //baseline not done
            searchPagingLoadConfig.addFilter(new FilterConfig("baselineDate", baselineDate,
                    FilterComparison.LESS_THAN));
            searchPagingLoadConfig.addFilter(new FilterConfig("baselinePending", "F",
                    FilterComparison.EQUAL_TO));
        }
        waterpointService.getWaterPointSummaries(district, baseLineDone, searchPagingLoadConfig, new M4waterAsyncCallback<PagingLoadResult<WaterPointSummary>>() {

            @Override
            public void onSuccess(PagingLoadResult<WaterPointSummary> result) {
                callback.onSuccess(new BasePagingLoadResult<WaterPointModel>(convertWaterpointResults(result), result.getOffset(), result.getTotalLength()));
            }
        });
    }

    private List<WaterPointModel> convertWaterpointResults(PagingLoadResult<WaterPointSummary> result) {
        List<WaterPointModel> results = new ArrayList<WaterPointModel>();
        List<WaterPointSummary> waterpoints = result.getData();
        for (WaterPointSummary p : waterpoints) {
            results.add(new WaterPointModel(p));
        }
        return results;
    }
}
