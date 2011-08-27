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
import org.cwf.client.controllers.HomeController;
import org.cwf.client.model.TicketSummary;

/**
 *
 * @author victor
 */
public class TicketDetailsPanel extends ContentPanel {

    final AppMessages appMessages = GWT.create(AppMessages.class);
    private Grid<TicketSummary> grid;
    private ColumnModel cm;
//    ticket status
    private String status;

    public TicketDetailsPanel(String status) {
        this.status = status;
        initialize();
    }

    private void initialize() {
        List<ColumnConfig> configs = new ArrayList<ColumnConfig>();
        configs.add(new ColumnConfig("date", "Date", 100));
        configs.add(new ColumnConfig("id", "ID", 100));
        configs.add(new ColumnConfig("district", "District", 100));
        configs.add(new ColumnConfig("subcounty", "Subcounty", 100));
        configs.add(new ColumnConfig("village", "Village", 100));
        ListStore<TicketSummary> store = new ListStore<TicketSummary>();
        if (status.contains("Open")) {
            store.add(TicketSummary.getOpenTickets());
        } else if (status.equals("Closed")) {
            store.add(TicketSummary.getClosedTickets());
        } else if (status.equals("Suspended")) {
            store.add(TicketSummary.getSuspendedTickets());
        }

        cm = new ColumnModel(configs);
        setBodyBorder(true);
        setHeading("New Water Points");
        setButtonAlign(HorizontalAlignment.CENTER);
        setLayout(new FitLayout());
        setSize(600, 300);
        grid = new Grid<TicketSummary>(store, cm);
        grid.setStyleAttribute("borderTop", "none");
        grid.setAutoExpandColumn("date");
        grid.setBorders(false);
        grid.setStripeRows(true);
        grid.setColumnLines(true);
        grid.setColumnReordering(true);
        grid.addListener(Events.RowDoubleClick, new Listener<GridEvent<BeanModel>>() {

            @Override
            public void handleEvent(GridEvent<BeanModel> be) {
                TicketSummary summary = grid.getSelectionModel().getSelectedItem();
                System.out.println("selected ===================== " + summary.getDistrict());
                HomeController controller = new HomeController();
                controller.forwardToViewTicketDetails(summary);

            }
        });
        grid.getAriaSupport().setLabelledBy(getHeader().getId() + "-label");
        add(grid);
        setLayout(new FitLayout());
    }
}
