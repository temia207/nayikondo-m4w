/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cwf.client.views;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedListener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.View;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.Label;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.extjs.gxt.ui.client.widget.form.FieldSet;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.FlowLayout;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import org.cwf.client.AppMessages;
import org.cwf.client.controllers.TicketDetailsController;
import org.cwf.client.model.ProblemSummary;
import org.cwf.client.model.StatusSummary;
import org.cwf.client.model.UserSummary;
import org.cwf.client.views.widgets.ParameterWidget;
import org.m4water.server.admin.model.FaultAssessment;
import org.m4water.server.admin.model.Problem;
import org.m4water.server.admin.model.ProblemLog;

/**
 *
 * @author victor
 */
public class TicketDetailsView extends View {

    AppMessages appMessages = GWT.create(AppMessages.class);
    private Window window;
    private FormPanel summaryPanel;
    private TextField<String> idTextFld, districtTfld, subcountyTfld, villageTfld, reportDateTfld, reporterNumTfld, messageTfld, dwoCommentTFld;
    private FormData formData;
    private FlexTable twitsTable;
    private ListStore<StatusSummary> statusStore;
    private ParameterWidget commentRow;
    private List<Problem> tickets;
    private Problem ticket;
    private FieldSet inspectionFldset;
    private HashMap<String, TextField<String>> faultAssessmentFields;
    private Set problemLogs;
    private Button closeTicketBtn, shelveTicketBtn, historyBtn;

    public TicketDetailsView(Controller controller) {
        super(controller);
    }

    @Override
    protected void initialize() {
        GWT.log("TicketDetailsView : initialize");
        window = new Window();
        window.setHeading(appMessages.assessmentAndRepairs());
        window.setHeight("500px");
        window.setWidth("425px");
        window.setPlain(true);
        window.setDraggable(true);
        window.setResizable(true);
        window.setScrollMode(Scroll.AUTO);
        window.setModal(true);
        formData = new FormData("-20");

        createLayoutWindow();
        window.add(addCommentPanel());
        commentRow.setVisible(false);
        closeTicketBtn = new Button("Close Ticket");
        closeTicketBtn.addSelectionListener(new SelectionListener<ButtonEvent>() {

            @Override
            public void componentSelected(ButtonEvent ce) {
                closeWindow();
                ((TicketDetailsController) TicketDetailsView.this.getController()).forwardToCommentsView(ticket, appMessages.closed());
            }
        });
        shelveTicketBtn = new Button("Shelve Ticket");
        shelveTicketBtn.addSelectionListener(new SelectionListener<ButtonEvent>() {

            @Override
            public void componentSelected(ButtonEvent ce) {
                closeWindow();
                ((TicketDetailsController) TicketDetailsView.this.getController()).forwardToCommentsView(ticket, appMessages.suspended());

            }
        });


        historyBtn = new Button("View History");
        historyBtn.addSelectionListener(new SelectionListener<ButtonEvent>() {

            @Override
            public void componentSelected(ButtonEvent ce) {
                closeWindow();
                ((TicketDetailsController) TicketDetailsView.this.getController()).forwardToHistoryView(ticket.getWaterpoint());

            }
        });

        window.addButton(closeTicketBtn);
        window.addButton(shelveTicketBtn);
        window.addButton(historyBtn);
        window.setButtonAlign(HorizontalAlignment.CENTER);
    }
    private FlexTable ticketsDataTable;

    private void createLayoutWindow() {
        summaryPanel = new FormPanel();
        summaryPanel.setFrame(true);
        summaryPanel.setHeading("");
        summaryPanel.setWidth("98%");
        summaryPanel.setLayout(new FlowLayout());
        ticketsDataTable = new FlexTable();
        ticketsDataTable.setWidth("98%");
        ticketsDataTable.setWidget(0, 0, getTicketDetailFldSet());
        ticketsDataTable.setWidget(1, 0, createTwitsFieldset());
        ticketsDataTable.getFlexCellFormatter().setRowSpan(0, 1, 2);
        ticketsDataTable.getFlexCellFormatter().setWidth(0, 0, "40%");
        ticketsDataTable.getFlexCellFormatter().setWidth(0, 1, "50%");
        ticketsDataTable.getFlexCellFormatter().setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_TOP);
        ticketsDataTable.getFlexCellFormatter().setVerticalAlignment(0, 1, HasVerticalAlignment.ALIGN_TOP);
        ticketsDataTable.getFlexCellFormatter().setVerticalAlignment(1, 0, HasVerticalAlignment.ALIGN_TOP);
        summaryPanel.add(ticketsDataTable);
        window.add(summaryPanel);
        window.setWidth("82%");
    }

    private FieldSet getTicketDetailFldSet() {

        FieldSet ticketDetailsFldset = new FieldSet();
        ticketDetailsFldset.setHeading(appMessages.ticketSummary());
        ticketDetailsFldset.setCheckboxToggle(false);

        FormLayout layout = new FormLayout();
        layout.setLabelWidth(75);
        ticketDetailsFldset.setLayout(layout);

        idTextFld = new TextField<String>();
        idTextFld.setFieldLabel("ID");
        idTextFld.setAllowBlank(false);
        ticketDetailsFldset.add(idTextFld, formData);
        idTextFld.setReadOnly(true);

        districtTfld = new TextField<String>();
        districtTfld.setFieldLabel("District");
        ticketDetailsFldset.add(districtTfld, formData);
        districtTfld.setReadOnly(true);

        subcountyTfld = new TextField<String>();
        subcountyTfld.setFieldLabel("Subcounty");
        ticketDetailsFldset.add(subcountyTfld, formData);
        subcountyTfld.setReadOnly(true);

        villageTfld = new TextField<String>();
        villageTfld.setFieldLabel("Village");
        ticketDetailsFldset.add(villageTfld, formData);
        villageTfld.setReadOnly(true);

        reportDateTfld = new TextField<String>();
        reportDateTfld.setFieldLabel("Report Date");
        ticketDetailsFldset.add(reportDateTfld, formData);
        reportDateTfld.setReadOnly(true);

        reporterNumTfld = new TextField<String>();
        reporterNumTfld.setFieldLabel("Reporter Tel");
        ticketDetailsFldset.add(reporterNumTfld, formData);
        reporterNumTfld.setReadOnly(true);

        messageTfld = new TextField<String>();
        messageTfld.setFieldLabel("Problem Description");
        ticketDetailsFldset.add(messageTfld);
        messageTfld.setReadOnly(true);

        dwoCommentTFld = new TextField<String>();
        dwoCommentTFld.setFieldLabel("DWO Comment");
        ticketDetailsFldset.add(dwoCommentTFld);
        dwoCommentTFld.setReadOnly(true);
        return ticketDetailsFldset;
    }

    public void setAssessmentFields(List<FaultAssessment> fields) {
       inspectionFldset = new FieldSet();
	    inspectionFldset.setHeading(appMessages.assessmentAndRepairs());
	    inspectionFldset.setCheckboxToggle(false);

	    FormLayout layout = new FormLayout();
	    layout.setLabelWidth(200);
	    inspectionFldset.setLayout(layout);
	    faultAssessmentFields = new HashMap<String, TextField<String>>();
	    if (!fields.isEmpty()) {
		    FaultAssessment x = fields.get(fields.size() - 1);
		    String id = "" + x.getAssessmentId();
//            inspectionFldset.add(addAssessmentFld("Id", id), formData);
		    inspectionFldset.add(addAssessmentFld("Problem", x.getProblem().getProblemDescsription()), formData);
		    inspectionFldset.add(addAssessmentFld("Faults", x.getFaults()), formData);
		    inspectionFldset.add(addAssessmentFld("Date", x.getDate().toString()), formData);
		    inspectionFldset.add(addAssessmentFld("Assessed By", x.getAssessedBy()), formData);
		    inspectionFldset.add(addAssessmentFld("Assessor Telephone", x.getAssessedBy()), formData);
		    inspectionFldset.add(addAssessmentFld("Types of Repaires Needed", x.getTypeOfRepairesNeeded()), formData);
		    inspectionFldset.add(addAssessmentFld("Is the problem Fixed?", x.getProblemFixed().equals("T") ? "Yes" : "No"), formData);
		    inspectionFldset.add(addAssessmentFld("If Not fixed why?", x.getReasonNotFixed()), formData);
		    TextArea repairsDone = new TextArea();
		    repairsDone.setFieldLabel("Repairs Done");
		    repairsDone.setValue(x.getRepairsDone());
		    repairsDone.setReadOnly(true);
		    inspectionFldset.add(repairsDone, formData);
		    inspectionFldset.add(addAssessmentFld("Cost of Materials", x.getCostOfMaterials()), formData);
		    inspectionFldset.add(addAssessmentFld("Cost of Labour", x.getCostOfLabour()), formData);
		    inspectionFldset.add(addAssessmentFld("Recommendations", x.getRecommendations()), formData);
		    inspectionFldset.add(addAssessmentFld("Name Of HPM", x.getUserId()), formData);
	    } else {
		    Label lable = new Label("There is no assessment done for this fault");
		    inspectionFldset.add(lable);
	    }
	    ticketsDataTable.setWidget(0, 1, inspectionFldset);
	    ticketsDataTable.getFlexCellFormatter().setRowSpan(0, 1, 2);
	    ticketsDataTable.getFlexCellFormatter().setWidth(0, 1, "50%");
	    ticketsDataTable.getFlexCellFormatter().setVerticalAlignment(0, 1, HasVerticalAlignment.ALIGN_TOP);
    }

    private FieldSet createTwitsFieldset() {
        FieldSet fieldSet = new FieldSet();
        fieldSet.setHeading(appMessages.ticketTweets());
        fieldSet.setCheckboxToggle(false);

        FormLayout layout = new FormLayout();
        layout.setLabelWidth(75);
        fieldSet.setLayout(layout);

        twitsTable = new FlexTable();
        twitsTable.setWidget(0, 0, new Label("Number"));
        twitsTable.setWidget(0, 1, new Label("Comment"));
        twitsTable.setWidth("100%");

        fieldSet.add(twitsTable);
        return fieldSet;
    }

    public void setUsers(List<UserSummary> users) {
        //
    }

    private void save(Problem ticket) {
//
    }

    private void createSummaryTwits(Set twits, FlexTable table) {
        int row = 1;
        for (Object twit : twits) {
            table.setWidget(row, 0, new Label(((ProblemLog) twit).getSenderNo()));
            table.setWidget(row, 1, new Label(((ProblemLog) twit).getIssue()));
            ++row;
        }
    }

    private TextField<String> addAssessmentFld(String name, String value) {
        TextField<String> assessmentQuiz = new TextField<String>();
        assessmentQuiz.setFieldLabel(name);
        assessmentQuiz.setAllowBlank(true);
        assessmentQuiz.setValue(value);
        faultAssessmentFields.put(name, assessmentQuiz);
        assessmentQuiz.setReadOnly(true);
        return assessmentQuiz;

    }

    public void showWindow() {
        window.show();
    }

    public void closeWindow() {
        window.hide();
    }

    public void setTickets(List<Problem> tickets) {
        this.tickets = tickets;
    }

    private void setProblemData(ProblemSummary summary) {
        ticket = summary.getProblem();
        idTextFld.setValue(String.valueOf(summary.getId()));
        districtTfld.setValue(summary.getDistrict());
        subcountyTfld.setValue(summary.getSubCounty());
        villageTfld.setValue(summary.getVillage());
        reportDateTfld.setValue(summary.getDate().toString());
        ProblemLog problemLog = null;
        problemLogs = summary.getProblem().getProblemLogs();
        for (Object object : problemLogs) {
            if (problemLog == null) {
                problemLog = (ProblemLog) object;
                problemLogs.add((ProblemLog) object);
            } else {
                problemLogs.add((ProblemLog) object);
            }
        }
        reporterNumTfld.setValue(problemLog != null ? problemLog.getSenderNo() : "");
        messageTfld.setValue(problemLog != null ? problemLog.getIssue() : summary.getProblemDescription());
        createSummaryTwits(problemLogs, twitsTable);
        if (summary.getProblemStatus().equalsIgnoreCase(appMessages.closed())) {
            closeTicketBtn.hide();
        } else if (summary.getProblemStatus().equalsIgnoreCase(appMessages.suspended())) {
            shelveTicketBtn.hide();
            closeTicketBtn.show();
        }

    }

    private FlexTable addCommentPanel() {
        FlexTable panel = new FlexTable();
        commentRow = new ParameterWidget();
        panel.setWidget(0, 0, commentRow.getNameTfld());
        panel.setWidget(0, 1, new Label(""));
        panel.setWidget(0, 2, commentRow.getValTfld());
        return panel;
    }

    @Override
    protected void handleEvent(AppEvent event) {
        GWT.log("TicketDetailsView : handleEvent");
        if (event.getType() == TicketDetailsController.TICKET_DETAILS) {
            TicketDetailsController controller2 = (TicketDetailsController) TicketDetailsView.this.getController();
            controller2.getUsers();
//            controller2.getFaultAssessments();
            ProblemSummary ticketSummary = event.getData();
            setProblemData(ticketSummary);
	    setAssessmentFields(new ArrayList<FaultAssessment>(ticketSummary.getProblem().getFaultAssessments()));
            showWindow();
        }
    }
}
