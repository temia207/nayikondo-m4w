/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cwf.client.views.widgets;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.GridEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.google.gwt.core.client.GWT;
import java.util.ArrayList;
import java.util.List;
import org.cwf.client.AppMessages;
import org.cwf.client.Refreshable;
import org.cwf.client.RefreshableEvent;
import org.cwf.client.RefreshablePublisher;
import org.cwf.client.controllers.HomeController;
import org.cwf.client.model.ProblemSummary;
import org.cwf.client.views.HomeView;
import org.m4water.server.admin.model.Problem;
import org.m4water.server.admin.model.Waterpoint;

/**
 *
 * @author victor
 */
public class TicketDetailsPanel extends ContentPanel implements Refreshable {

    final AppMessages appMessages = GWT.create(AppMessages.class);
    private Grid<ProblemSummary> grid;
    private ColumnModel cm;
//    ticket status
    private String status;
    private ListStore<ProblemSummary> store;

    ;
    private HomeView parentView;

    public TicketDetailsPanel(HomeView view, String status) {
        this.status = status;
        this.parentView = view;
        initialize();
        setTicketSummary(parentView.tickets);
        RefreshablePublisher.get().subscribe(RefreshableEvent.Type.TICKET_UPDATE, this);
    }

    private void initialize() {
        List<ColumnConfig> configs = new ArrayList<ColumnConfig>();
//        configs.add(new ColumnConfig("date", "Date", 100));
        configs.add(new ColumnConfig("id", "ID", 100));
        configs.add(new ColumnConfig("waterpoint", "WaterPoint", 100));
        configs.add(new ColumnConfig("problemdescription", "Description", 100));
        configs.add(new ColumnConfig("district", "District", 100));
        configs.add(new ColumnConfig("subcounty", "Subcounty", 100));
        configs.add(new ColumnConfig("village", "Village", 100));
        //use static tickets for now
        store = new ListStore<ProblemSummary>();
        cm = new ColumnModel(configs);
        setBodyBorder(true);
        setHeading("New Water Points");
        setButtonAlign(HorizontalAlignment.CENTER);
        setLayout(new FitLayout());
        setSize(600, 300);
        grid = new Grid<ProblemSummary>(store, cm);
        grid.setStyleAttribute("borderTop", "none");
        grid.setAutoExpandColumn("id");
        grid.setBorders(false);
        grid.setStripeRows(true);
        grid.setColumnLines(true);
        grid.setColumnReordering(true);
        grid.addListener(Events.RowDoubleClick, new Listener<GridEvent<BeanModel>>() {

            @Override
            public void handleEvent(GridEvent<BeanModel> be) {
                ProblemSummary summary = grid.getSelectionModel().getSelectedItem();
//                System.out.println("selected ===================== " + summary.getMessage());
                parentView.showTicketDetails(summary);

            }
        });
        grid.getAriaSupport().setLabelledBy(getHeader().getId() + "-label");
        add(grid);
        setLayout(new FitLayout());
    }

    private void setTicketSummary(List<Problem> problem) {
        System.out.println("refreshing for ticket from the database");
        for (Problem t : problem) {
            if (status.equals(appMessages.open())) {
                if (t.getProblemStatus().equals(appMessages.open())) {
                    store.add(new ProblemSummary(t));
                }
            } else if (status.equals(appMessages.closed())) {
                if (t.getProblemStatus().equals(appMessages.closed())) {
                    store.add(new ProblemSummary(t));
                }
            } else if (status.equals(appMessages.suspended())) {
                if (t.getProblemStatus().equals(appMessages.suspended())) {
                    store.add(new ProblemSummary(t));
                }
            }
        }

    }

    @Override
    public void refresh(RefreshableEvent event) {
        if (event.getEventType() == RefreshableEvent.Type.TICKET_UPDATE) {
            ListStore<ProblemSummary> store1 = grid.getStore();
            System.out.println("-------------------------------------------------------------");
            List<Problem> tkts = event.getData();
            for (Problem t : tkts) {

                if (status.equalsIgnoreCase(appMessages.open())) {
                    if (t.getProblemStatus().equalsIgnoreCase(appMessages.open())) {
                        store1.add(new ProblemSummary(t));
                    }
                } else if (status.equalsIgnoreCase(appMessages.closed())) {
                    if (t.getProblemStatus().equalsIgnoreCase(appMessages.closed())) {
                        store1.add(new ProblemSummary(t));
                    }
                } else if (status.equalsIgnoreCase(appMessages.suspended())) {
                    if (t.getProblemStatus().equalsIgnoreCase(appMessages.suspended())) {
                        store1.add(new ProblemSummary(t));
                    }
                }
            }
//            grid.getView().refresh(true);

        }
    }
}
