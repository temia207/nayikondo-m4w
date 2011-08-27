/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cwf.client.views;

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
import org.cwf.client.model.WaterPointSummary;

/**
 *
 * @author victor
 */
public class NewWaterPointsView extends ContentPanel {

    final AppMessages appMessages = GWT.create(AppMessages.class);
    private Grid<WaterPointSummary> grid;
    private ColumnModel cm;
    //type may be new waterpoints or all waterpoints
    private String type;

    public NewWaterPointsView(String type) {
        this.type = type;
        initialize();
    }

    private void initialize() {
        List<ColumnConfig> configs = new ArrayList<ColumnConfig>();
        configs.add(new ColumnConfig("date", "Date", 100));
        configs.add(new ColumnConfig("id", "ID", 100));
        configs.add(new ColumnConfig("district", "District", 100));
        configs.add(new ColumnConfig("subcounty", "Subcounty", 100));
        configs.add(new ColumnConfig("village", "Village", 100));
        configs.add(new ColumnConfig("latitude", "Latitude", 100));
        configs.add(new ColumnConfig("longitude", "Longitude", 100));
        ListStore<WaterPointSummary> store = new ListStore<WaterPointSummary>();
        if (type.equals(appMessages.newWaterPoints())) {
            store.add(WaterPointSummary.getSampleNewWaterPoints());
            setHeading(appMessages.newWaterPoints());
        } else if (type.equals(appMessages.allWaterPoints())) {
            store.add(WaterPointSummary.getSampleAvailableWaterPoints());
            setHeading(appMessages.allWaterPoints());
        }

        cm = new ColumnModel(configs);
        setBodyBorder(true);
        setButtonAlign(HorizontalAlignment.CENTER);
        setLayout(new FitLayout());
        setSize(600, 300);
        grid = new Grid<WaterPointSummary>(store, cm);
        grid.setStyleAttribute("borderTop", "none");
        grid.setAutoExpandColumn("date");
        grid.setBorders(false);
        grid.setStripeRows(true);
        grid.setColumnLines(true);
        grid.setColumnReordering(true);
        grid.addListener(Events.RowDoubleClick, new Listener<GridEvent<BeanModel>>() {

            @Override
            public void handleEvent(GridEvent<BeanModel> be) {
                WaterPointSummary summary = grid.getSelectionModel().getSelectedItem();
                System.out.println("selected ===================== " + summary.getDistrict());
                HomeController controller = new HomeController();
                controller.forwardToEditWaterPoint(summary);

            }
        });
        grid.getAriaSupport().setLabelledBy(getHeader().getId() + "-label");
        add(grid);
        setLayout(new FitLayout());
    }
}
