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
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.FlowLayout;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.FlexTable;
import java.util.List;
import org.cwf.client.AppMessages;
import org.cwf.client.controllers.TicketDetailsController;
import org.cwf.client.model.TicketSummary;
import org.cwf.client.model.TicketTwits;
import org.cwf.client.model.UserSummary;
import org.cwf.client.model.WaterPointSummary;
import org.cwf.client.views.widgets.ParameterWidget;
import org.m4water.server.admin.model.Ticket;

/**
 *
 * @author victor
 */
public class TicketDetailsView extends View {

    AppMessages appMessages = GWT.create(AppMessages.class);
    private Window window;
    private FormPanel summaryPanel;
    private TextField<String> idTextFld, districtTfld, subcountyTfld, villageTfld, reportDateTfld, reporterNumTfld, messageTfld;
    private ComboBox<UserSummary> reassignTicket;
    private FormData formData;
    private FlexTable twitsTable;
    private List<TicketTwits> ticketTwits;
    private ListStore<UserSummary> store;
    private ParameterWidget commentRow;
    private List<Ticket> tickets;

    public TicketDetailsView(Controller controller) {
        super(controller);
    }

    @Override
    protected void initialize() {
        GWT.log("TicketDetailsView : initialize");
        window = new Window();
        window.setHeading(appMessages.ticketDetails());
        window.setHeight("500px");
        window.setWidth("425px");
        window.setPlain(true);
        window.setDraggable(true);
        window.setResizable(true);
        window.setScrollMode(Scroll.AUTO);
        window.setModal(true);
        formData = new FormData("-20");

        createSummaryFieldset();

        window.add(createTwitsFieldset());
        window.add(addCommentPanel());
        commentRow.setVisible(false);
        Button addCommentBtn, saveChangesBtn;
        addCommentBtn = new Button("Add coment");
        addCommentBtn.addSelectionListener(new SelectionListener<ButtonEvent>() {

            @Override
            public void componentSelected(ButtonEvent ce) {
                commentRow.setVisible(true);
//                window.setFocusWidget(commentRow);
            }
        });
        saveChangesBtn = new Button("Save Changes");
        saveChangesBtn.addSelectionListener(new SelectionListener<ButtonEvent>() {

            @Override
            public void componentSelected(ButtonEvent ce) {
                String number = commentRow.getNameTfld().getValue();
                String value = commentRow.getValTfld().getValue();
                int row = twitsTable.getRowCount();
                twitsTable.setWidget(row, 0, new Label(number));
                twitsTable.setWidget(row, 1, new Label(value));
                commentRow.getNameTfld().setValue("");
                commentRow.getValTfld().setValue("");
                commentRow.setVisible(false);

            }
        });
        window.addButton(addCommentBtn);
        window.addButton(saveChangesBtn);
        window.setButtonAlign(HorizontalAlignment.CENTER);
//        window.add(createButtons());
    }

    private void createSummaryFieldset() {
        summaryPanel = new FormPanel();
        summaryPanel.setFrame(true);
        summaryPanel.setHeading("");
        summaryPanel.setWidth(390);
        summaryPanel.setLayout(new FlowLayout());

        FieldSet fieldSet = new FieldSet();
        fieldSet.setHeading(appMessages.ticketSummary());
        fieldSet.setCheckboxToggle(false);

        FormLayout layout = new FormLayout();
        layout.setLabelWidth(75);
        fieldSet.setLayout(layout);

        idTextFld = new TextField<String>();
        idTextFld.setFieldLabel("ID");
        idTextFld.setAllowBlank(false);
        fieldSet.add(idTextFld, formData);

        districtTfld = new TextField<String>();
        districtTfld.setFieldLabel("District");
        fieldSet.add(districtTfld, formData);

        subcountyTfld = new TextField<String>();
        subcountyTfld.setFieldLabel("Subcounty");
        fieldSet.add(subcountyTfld, formData);

        villageTfld = new TextField<String>();
        villageTfld.setFieldLabel("Village");
        fieldSet.add(villageTfld, formData);

        reportDateTfld = new TextField<String>();
        reportDateTfld.setFieldLabel("Report Date");
        fieldSet.add(reportDateTfld, formData);

        reporterNumTfld = new TextField<String>();
        reporterNumTfld.setFieldLabel("Reporter Tel");
        fieldSet.add(reporterNumTfld, formData);

        messageTfld = new TextField<String>();
        messageTfld.setFieldLabel("Problem");
        fieldSet.add(messageTfld);

        //get the store
        store = new ListStore<UserSummary>();
        reassignTicket = new ComboBox<UserSummary>();
        reassignTicket.setFieldLabel("Re Assign To");
        reassignTicket.setDisplayField("name");
        reassignTicket.setTriggerAction(TriggerAction.ALL);
        reassignTicket.setAllowBlank(false);
        reassignTicket.setStore(store);
        reassignTicket.addSelectionChangedListener(new SelectionChangedListener<UserSummary>() {

            @Override
            public void selectionChanged(SelectionChangedEvent<UserSummary> se) {
                //
            }
        });

        fieldSet.add(reassignTicket, formData);

        summaryPanel.add(fieldSet);

        summaryPanel.add(fieldSet);

        window.add(summaryPanel);
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
        store.add(users);
    }

    private void createSummaryTwits(List<TicketTwits> twits, FlexTable table) {
        int row = 1;
        for (TicketTwits twit : twits) {
            table.setWidget(row, 0, new Label(twit.getUser().getId()));
            table.setWidget(row, 1, new Label(twit.getComment()));
            ++row;
        }
    }

    public void showWindow() {
        window.show();
    }

    public void closeWindow() {
        window.hide();
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    private void setWaterPointData(TicketSummary summary) {
        idTextFld.setValue(summary.getId());
        districtTfld.setValue(summary.getDistrict());
        subcountyTfld.setValue(summary.getSubCounty());
        villageTfld.setValue(summary.getVillage());
        reportDateTfld.setValue(summary.getDate());
        reporterNumTfld.setValue(summary.getReporterNumber());
        messageTfld.setValue(summary.getMessage());
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
//            WaterPointSummary waterPointSummary = event.getData();
            ticketTwits = TicketTwits.getSampleTwits();
            TicketDetailsController controller2 = (TicketDetailsController) TicketDetailsView.this.getController();
            controller2.getUsers();
//            controller2.getTickets();
            createSummaryTwits(ticketTwits, twitsTable);
            TicketSummary ticketSummary = event.getData();
            setWaterPointData(ticketSummary);
            showWindow();
        }
    }
}
