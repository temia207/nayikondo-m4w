/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cwf.client.views;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.GridEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.View;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.google.gwt.core.client.GWT;
import java.util.ArrayList;
import java.util.List;
import org.cwf.client.AppMessages;
import org.cwf.client.controllers.ProblemHistoryController;
import org.cwf.client.model.ProblemSummary;
import org.m4water.server.admin.model.FaultAssessment;
import org.m4water.server.admin.model.Problem;
import org.m4water.server.admin.model.Waterpoint;

/**
 *
 * @author victor
 */
public class ProblemHistoryView extends View {

    final AppMessages appMessages = GWT.create(AppMessages.class);
    private Grid<ProblemSummary> grid;
    private ColumnModel cm;
    private String status;
    private ListStore<ProblemSummary> store;
    private ContentPanel cp;
    private Window window;

    public ProblemHistoryView(Controller controller) {
        super(controller);
    }

    @Override
    protected void initialize() {
        window = new Window();
        window.setHeading("Problem History");
        window.setHeight("500px");
        window.setSize(630, 340);
        window.setPlain(true);
        window.setDraggable(true);
        window.setResizable(true);
        window.setScrollMode(Scroll.AUTO);
        window.setModal(true);
        cp = new ContentPanel();
        List<ColumnConfig> configs = new ArrayList<ColumnConfig>();
        configs.add(new ColumnConfig("id", "Ticket ID", 100));
        configs.add(new ColumnConfig("date", "Date", 100));
        configs.add(new ColumnConfig("waterpoint", "WaterPoint", 100));
        configs.add(new ColumnConfig("problemdescription", "Fault", 100));
        configs.add(new ColumnConfig("repairs", "Repairs", 100));
        configs.add(new ColumnConfig("dwocomments", "DWO Comments", 100));
        //use static tickets for now
        store = new ListStore<ProblemSummary>();
        cm = new ColumnModel(configs);
        cp.setBodyBorder(true);
        cp.setHeading("");
        cp.setButtonAlign(HorizontalAlignment.CENTER);
        cp.setLayout(new FitLayout());
        cp.setSize(600, 300);
        grid = new Grid<ProblemSummary>(store, cm);
        grid.setStyleAttribute("borderTop", "none");
        grid.setAutoWidth(true);
        grid.setBorders(false);
        grid.setStripeRows(true);
        grid.setColumnLines(true);
        grid.setColumnReordering(true);
        grid.addListener(Events.RowDoubleClick, new Listener<GridEvent<BeanModel>>() {

            @Override
            public void handleEvent(GridEvent<BeanModel> be) {
                ProblemSummary summary = grid.getSelectionModel().getSelectedItem();

            }
        });
        grid.getAriaSupport().setLabelledBy(cp.getHeader().getId() + "-label");
        cp.add(grid);
        cp.setLayout(new FitLayout());
        window.add(cp);
    }

    public void setProblemData(List<Problem> result) {
        store.removeAll();
        for (Problem t : result) {
            store.add(new ProblemSummary(t));
        }
    }

    public void setFault(List<FaultAssessment> result) {
        //
    }

    public void showWindow() {
        window.show();
    }

    public void closeWindow() {
        window.hide();
    }

    @Override
    protected void handleEvent(AppEvent event) {
        Waterpoint waterpointId = event.getData();
        ((ProblemHistoryController) ProblemHistoryView.this.getController()).getTicketHistory(waterpointId);
        showWindow();
    }
}
