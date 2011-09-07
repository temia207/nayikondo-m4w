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
import org.cwf.client.model.TicketSummary;
import org.cwf.client.views.HomeView;
import org.m4water.server.admin.model.Ticket;
import org.m4water.server.admin.model.Waterpoint;

/**
 *
 * @author victor
 */
public class TicketDetailsPanel extends ContentPanel implements Refreshable {

    final AppMessages appMessages = GWT.create(AppMessages.class);
    private Grid<TicketSummary> grid;
    private ColumnModel cm;
//    ticket status
    private String status;
    private ListStore<TicketSummary> store;

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
        configs.add(new ColumnConfig("district", "District", 100));
        configs.add(new ColumnConfig("subcounty", "Subcounty", 100));
        configs.add(new ColumnConfig("village", "Village", 100));
        //use static tickets for now
        store = new ListStore<TicketSummary>();
        cm = new ColumnModel(configs);
        setBodyBorder(true);
        setHeading("New Water Points");
        setButtonAlign(HorizontalAlignment.CENTER);
        setLayout(new FitLayout());
        setSize(600, 300);
        grid = new Grid<TicketSummary>(store, cm);
        grid.setStyleAttribute("borderTop", "none");
        grid.setAutoExpandColumn("id");
        grid.setBorders(false);
        grid.setStripeRows(true);
        grid.setColumnLines(true);
        grid.setColumnReordering(true);
        grid.addListener(Events.RowDoubleClick, new Listener<GridEvent<BeanModel>>() {

            @Override
            public void handleEvent(GridEvent<BeanModel> be) {
                TicketSummary summary = grid.getSelectionModel().getSelectedItem();
                System.out.println("selected ===================== " + summary.getMessage());
                parentView.showTicketDetails(summary);

            }
        });
        grid.getAriaSupport().setLabelledBy(getHeader().getId() + "-label");
        add(grid);
        setLayout(new FitLayout());
    }
//needs to first fix waterpoint null value returned from hibernate

    private void setTicketSummary(List<Ticket> tickets) {
        for (Ticket t : tickets) {
            Waterpoint source = t.getWaterpoint();
            store.add(new TicketSummary("19/5/2011",source.getReferenceNumber(), source.getDistrict(), source.getSubcounty(), source.getVillage(),t.getCreatorTel(),t.getMessage()));
        }

    }

    @Override
    public void refresh(RefreshableEvent event) {
        if (event.getEventType() == RefreshableEvent.Type.TICKET_UPDATE) {
            ListStore<TicketSummary> store1 = grid.getStore();
            System.out.println("-------------------------------------------------------------");
            List<Ticket> tkts = event.getData();
            for (Ticket t : tkts) {
                Waterpoint source = t.getWaterpoint();
                store1.add(new TicketSummary("19/5/2011",source.getReferenceNumber(), source.getDistrict(), source.getSubcounty(), source.getVillage(),t.getCreatorTel(),source.getStatus()));
            }
//            grid.getView().refresh(true);

        }
    }
}
