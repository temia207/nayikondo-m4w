/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cwf.client.views;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.Style.SelectionMode;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.GridEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.grid.BufferView;
import com.extjs.gxt.ui.client.widget.grid.CheckBoxSelectionModel;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridSelectionModel;
import com.extjs.gxt.ui.client.widget.grid.filters.DateFilter;
import com.extjs.gxt.ui.client.widget.grid.filters.GridFilters;
import com.extjs.gxt.ui.client.widget.grid.filters.ListFilter;
import com.extjs.gxt.ui.client.widget.grid.filters.StringFilter;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import java.util.ArrayList;
import java.util.List;
import org.cwf.client.AppMessages;
import org.cwf.client.Refreshable;
import org.cwf.client.RefreshableEvent;
import org.cwf.client.RefreshablePublisher;
import org.cwf.client.controllers.HomeController;
import org.cwf.client.model.Subcounty;
import org.cwf.client.model.WaterPointModel;
import org.cwf.client.util.Utilities;
import org.cwf.client.utils.ProgressIndicator;
import org.m4water.server.admin.model.WaterPointSummary;

/**
 *
 * @author victor
 */
public class AvailableWaterpointsView extends ContentPanel implements Refreshable {

    final AppMessages appMessages = GWT.create(AppMessages.class);
    private Grid<WaterPointModel> grid;
    private ColumnModel cm;
    //type may be new waterpoints or all waterpoints
    private String type;
    private HomeView parentView;
    private ListStore<Subcounty> subcounties, districts, counties, parishes, villages;
    private Button launchBaseline;
    private List<WaterPointSummary> waterpointSummaries = new ArrayList<WaterPointSummary>();
    private ListStore<WaterPointModel> store;

    public AvailableWaterpointsView(HomeView view, String type) {
        this.type = type;
        this.parentView = view;
        initialize();
    }

    private void initialize() {
        List<ColumnConfig> configs = new ArrayList<ColumnConfig>();
        CheckBoxSelectionModel<WaterPointModel> sm = new CheckBoxSelectionModel<WaterPointModel>();
//        FasterCheckBoxSelectionModel<WaterPointModel> sm = new FasterCheckBoxSelectionModel<WaterPointModel>();
//        sm.setSelectionMode(SelectionMode.SIMPLE);
        configs.add(sm.getColumn());
        configs.add(new ColumnConfig("date", "Date", 100));
        configs.add(new ColumnConfig("id", "ID", 100));
        configs.add(new ColumnConfig("village", "Village", 100));
        configs.add(new ColumnConfig("parish", "Parish", 100));
        configs.add(new ColumnConfig("subcounty", "Subcounty", 100));
        configs.add(new ColumnConfig("county", "County", 100));
        configs.add(new ColumnConfig("district", "District", 100));
        store = new ListStore<WaterPointModel>();
        cm = new ColumnModel(configs);
        setBodyBorder(true);
        setButtonAlign(HorizontalAlignment.CENTER);
        setLayout(new FitLayout());
        setSize(600, 290);
        grid = new Grid<WaterPointModel>(store, cm);
        grid.setStyleAttribute("borderTop", "none");
        grid.setAutoWidth(true);
        grid.setBorders(false);
        grid.setStripeRows(true);
        grid.setColumnLines(true);
        grid.setColumnReordering(true);
        grid.addListener(Events.RowDoubleClick, new Listener<GridEvent<BeanModel>>() {

            @Override
            public void handleEvent(GridEvent<BeanModel> be) {
                WaterPointModel summary = grid.getSelectionModel().getSelectedItem();
                System.out.println("selected ===================== " + summary.getDistrict());
                HomeController controller = (HomeController) parentView.getController();
                controller.forwardToEditWaterPoint(summary.getId());

            }
        });
        grid.getAriaSupport().setLabelledBy(getHeader().getId() + "-label");
        grid.addPlugin(createFilters());
        grid.setSelectionModel(sm);
        grid.addPlugin(sm);
        BufferView buffer = new BufferView();
        buffer.setScrollDelay(10);
        buffer.setRowHeight(28);
        grid.setView(buffer);
        add(grid);
        launchBaseline = new Button("Carry out Base Line");
        launchBaseline.addSelectionListener(new SelectionListener<ButtonEvent>() {

            @Override
            public void componentSelected(ButtonEvent ce) {
                //launch baseline work flow
                WaterPointModel point = grid.getSelectionModel().getSelectedItem();
                HomeController controller = (HomeController) parentView.getController();
                controller.launchBaseline(point.getId());
                RefreshablePublisher.get().publish(new RefreshableEvent(RefreshableEvent.Type.WATERPOINT_CHANGES,point.getWaterPointSummary()));
                store.remove(grid.getSelectionModel().getSelectedItem());
                grid.setSelectionModel(new GridSelectionModel<WaterPointModel>());
            }
        });
        addButton(launchBaseline);
        launchBaseline.hide();
        setLayout(new FitLayout());
    }

    private void initGrid(ListStore<WaterPointModel> store) {
        grid = new Grid<WaterPointModel>(store, cm);
        grid.setStyleAttribute("borderTop", "none");
        grid.setAutoWidth(true);
        grid.setBorders(false);
        grid.setStripeRows(true);
        grid.setColumnLines(true);
        grid.setColumnReordering(true);
        grid.addListener(Events.RowDoubleClick, new Listener<GridEvent<BeanModel>>() {

            @Override
            public void handleEvent(GridEvent<BeanModel> be) {
                WaterPointModel summary = grid.getSelectionModel().getSelectedItem();
                System.out.println("selected ===================== " + summary.getDistrict());
                HomeController controller = (HomeController) parentView.getController();
                controller.forwardToEditWaterPoint(summary.getId());

            }
        });
        grid.getAriaSupport().setLabelledBy(getHeader().getId() + "-label");
        grid.addPlugin(createFilters());
        BufferView buffer = new BufferView();
        buffer.setScrollDelay(2);
        buffer.setRowHeight(28);
        grid.setView(buffer);
        add(grid);
    }

    private GridFilters createFilters() {
        DateFilter dateFilter = new DateFilter("date");
        ListFilter districtFilter = new ListFilter("district", districts);
        districtFilter.setDisplayProperty("name");
        ListFilter countyFilter = new ListFilter("county", counties);
        countyFilter.setDisplayProperty("name");
        ListFilter subcountyFilter = new ListFilter("subcounty", subcounties);
        subcountyFilter.setDisplayProperty("name");
        StringFilter villageFilter = new StringFilter("village");
        StringFilter parishFilter = new StringFilter("parish");
        StringFilter waterPointFilter = new StringFilter("id");
        //
        GridFilters filters = new GridFilters();
        filters.setLocal(true);
        filters.addFilter(dateFilter);
        filters.addFilter(waterPointFilter);
        filters.addFilter(villageFilter);
        filters.addFilter(parishFilter);
        filters.addFilter(subcountyFilter);
        filters.addFilter(countyFilter);
        filters.addFilter(districtFilter);
        filters.addFilter(subcountyFilter);
        return filters;
    }

    @Override
    public void refresh(RefreshableEvent event) {
        System.out.println("===================================== refresh "
                + event.getEventType().name() + " type of view " + type);
        ProgressIndicator.showProgressBar();
        GWT.log("Rereshing--- view = " + type);
        if ((event.getEventType() == RefreshableEvent.Type.ALL_WATER_POINTS)) {
            final List<WaterPointSummary> summary = event.getData();
            waterpointSummaries = summary;
            ListStore<WaterPointModel> store1 = grid.getStore();
            if (store1.getCount() > 0) {
                store1.removeAll();
            }
            subcounties = new ListStore<Subcounty>();
            districts = new ListStore<Subcounty>();
            counties = new ListStore<Subcounty>();
            parishes = new ListStore<Subcounty>();
            villages = new ListStore<Subcounty>();
            for (WaterPointSummary point : summary) {
                if (type.equalsIgnoreCase(appMessages.pendingBaseline())) {
                    if (point.getBaselinePending().equalsIgnoreCase("T")) {
                        store1.add(new WaterPointModel(point));
                    }
                } else if (type.equalsIgnoreCase(appMessages.newWaterPoints())) {
                    if (point.getBaselineDate() == null) {
                        store1.add(new WaterPointModel(point));
                    }
                } else if (type.equalsIgnoreCase(appMessages.baseLineNotDone())) {
                    if ((point.getBaselineDate() != null) && (point.getBaselineDate().before(parentView.baselineSetDate))) {
                        store1.add(new WaterPointModel(point));
                    }
                } else if (type.equalsIgnoreCase(appMessages.baseLineDataComplete())) {
                    if ((point.getBaselineDate() != null) && (point.getBaselineDate().after(parentView.baselineSetDate) || point.getBaselineDate().equals(parentView.baselineSetDate))) {
                        store1.add(new WaterPointModel(point));
                    }
                }
            }
            districts.add(Utilities.filterSubcounties(summary, "district"));
            counties.add(Utilities.filterSubcounties(summary, "county"));
            subcounties.add(Utilities.filterSubcounties(summary, "subcounty"));
            parishes.add(Utilities.filterSubcounties(summary, "parish"));
            villages.add(Utilities.filterSubcounties(summary, "village"));
            if (type.equalsIgnoreCase(appMessages.baseLineNotDone())) {
                launchBaseline.show();
            } else if (type.equalsIgnoreCase(appMessages.newWaterPoints())) {
                launchBaseline.show();
            } else if (type.equalsIgnoreCase(appMessages.baseLineDataComplete())) {
                launchBaseline.setText("Redo Baseline");
                launchBaseline.show();
            }
            System.out.println("done retrieving waterpoint summaries");

        } else if ((event.getEventType() == RefreshableEvent.Type.WATERPOINT_CHANGES)) {
            GWT.log("Refreshable event ===== "+RefreshableEvent.Type.WATERPOINT_CHANGES);
            if (type.equalsIgnoreCase(appMessages.pendingBaseline())) {
                WaterPointSummary summary = event.getData();
                ListStore<WaterPointModel> store1 = grid.getStore();
                store1.add(new WaterPointModel(summary));
            }
        }
        ProgressIndicator.hideProgressBar();
    }
}
