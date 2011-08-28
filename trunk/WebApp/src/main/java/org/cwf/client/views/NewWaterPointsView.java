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
import org.cwf.client.Refreshable;
import org.cwf.client.RefreshableEvent;
import org.cwf.client.RefreshablePublisher;
import org.cwf.client.controllers.HomeController;
import org.cwf.client.model.WaterPointSummary;
import org.m4water.server.admin.model.Waterpoint;

/**
 *
 * @author victor
 */
public class NewWaterPointsView extends ContentPanel implements Refreshable {

    final AppMessages appMessages = GWT.create(AppMessages.class);
    private Grid<WaterPointSummary> grid;
    private ColumnModel cm;
    //type may be new waterpoints or all waterpoints
    private String type;
    private HomeView parentView;

    public NewWaterPointsView(HomeView view, String type) {
        this.type = type;
        this.parentView = view;
        RefreshablePublisher.get().subscribe(RefreshableEvent.Type.WATER_POINT_DATA, this);
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
//        if (type.equals(appMessages.newWaterPoints())) {
//            store.add(WaterPointSummary.getSampleNewWaterPoints());
//            setHeading(appMessages.newWaterPoints());
//        } else if (type.equals(appMessages.allWaterPoints())) {
//            store.add(WaterPointSummary.getSampleAvailableWaterPoints());
//            setHeading(appMessages.allWaterPoints());
//        }

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
                HomeController controller = (HomeController) parentView.getController();
                controller.forwardToEditWaterPoint(summary);

            }
        });
        grid.getAriaSupport().setLabelledBy(getHeader().getId() + "-label");
        add(grid);
        setLayout(new FitLayout());
    }

    @Override
    public void refresh(RefreshableEvent event) {
        System.out.println("-===================================== refresh");
        if (event.getEventType() == RefreshableEvent.Type.WATER_POINT_DATA) {
            ListStore<WaterPointSummary> store1 = grid.getStore();
            List<Waterpoint> waterPoints = event.getData();
            for (Waterpoint point : waterPoints) {
                store1.add(new WaterPointSummary(point.getDate().toString(),point.getReferenceNumber(),point.getDistrict(),point.getSubcounty(),point.getVillage(),point.getLatitude(),point.getLongitude()));
            }
        }
    }
}
