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
import org.cwf.client.M4waterAsyncCallback;
import org.cwf.client.service.AssessmentClientServiceAsync;
import org.cwf.client.service.ProblemServiceAsync;
import org.cwf.client.views.ProblemHistoryView;
import org.m4water.server.admin.model.FaultAssessment;
import org.m4water.server.admin.model.Problem;
import org.m4water.server.admin.model.Waterpoint;

/**
 *
 * @author victor
 */
public class ProblemHistoryController extends Controller {

    private ProblemHistoryView problemHistoryView;
    public final static EventType PROBLEM_HISTORY = new EventType();
    ProblemServiceAsync ticketService;
    AssessmentClientServiceAsync assessmentService;

    public ProblemHistoryController(ProblemServiceAsync aTicketService,AssessmentClientServiceAsync aAssessmentService) {
        super();
        ticketService = aTicketService;
        assessmentService = aAssessmentService;
        registerEventTypes(PROBLEM_HISTORY);
    }

    @Override
    public void initialize() {
        GWT.log("ProblemHistoryController  : initialize");
        problemHistoryView = new ProblemHistoryView(this);
    }

    @Override
    public void handleEvent(AppEvent event) {
        GWT.log("ProblemHistoryController  :handle event");
        EventType type = event.getType();
        if (type == PROBLEM_HISTORY) {
            forwardToView(problemHistoryView, event);
        }
    }

    public void getTicketHistory(Waterpoint waterpointId) {
        GWT.log("TicketDetailsController  :getTicket(tring ticketId)");
        ticketService.getProblemHistory(waterpointId, new M4waterAsyncCallback<List<Problem>>() {

            @Override
            public void onSuccess(List<Problem> result) {
                problemHistoryView.setProblemData(result);
            }
        });
    }
        public void getFaultAssessment() {
        GWT.log("TicketDetailsController  :getInspections()");
        assessmentService.getFaultAssessments(new M4waterAsyncCallback<List<FaultAssessment>>() {

            @Override
            public void onSuccess(List<FaultAssessment> result) {
                problemHistoryView.setFault(result);
            }
        });
    }
}
