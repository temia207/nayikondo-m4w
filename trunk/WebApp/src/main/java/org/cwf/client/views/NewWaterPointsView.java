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
import com.extjs.gxt.ui.client.widget.grid.BufferView;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.filters.DateFilter;
import com.extjs.gxt.ui.client.widget.grid.filters.GridFilters;
import com.extjs.gxt.ui.client.widget.grid.filters.ListFilter;
import com.extjs.gxt.ui.client.widget.grid.filters.StringFilter;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.FlowLayout;
import com.google.gwt.core.client.GWT;
import java.util.ArrayList;
import java.util.List;
import org.cwf.client.AppMessages;
import org.cwf.client.Refreshable;
import org.cwf.client.RefreshableEvent;
import org.cwf.client.RefreshablePublisher;
import org.cwf.client.controllers.HomeController;
import org.cwf.client.model.Subcounty;
import org.cwf.client.model.WaterPointModel;
import org.cwf.client.utils.ProgressIndicator;
import org.cwf.client.views.widgets.CommentView;
import org.m4water.server.admin.model.WaterPointSummary;

/**
 *
 * @author victor
 */
public class NewWaterPointsView extends ContentPanel implements Refreshable {

    final AppMessages appMessages = GWT.create(AppMessages.class);
    private Grid<WaterPointModel> grid;
    private ColumnModel cm;
    //type may be new waterpoints or all waterpoints
    private String type;
    private HomeView parentView;

    public NewWaterPointsView(HomeView view, String type) {
        this.type = type;
        this.parentView = view;
        initialize();
            RefreshablePublisher.get().subscribe(RefreshableEvent.Type.ALL_WATER_POINTS, this);
    }

    private void initialize() {
        List<ColumnConfig> configs = new ArrayList<ColumnConfig>();
        configs.add(new ColumnConfig("date", "Date", 100));
        configs.add(new ColumnConfig("id", "ID", 100));
        configs.add(new ColumnConfig("village", "Village", 100));
        configs.add(new ColumnConfig("parish", "Parish", 100));
        configs.add(new ColumnConfig("subcounty", "Subcounty", 100));
        configs.add(new ColumnConfig("county", "County", 100));
        configs.add(new ColumnConfig("district", "District", 100));
        ListStore<WaterPointModel> store = new ListStore<WaterPointModel>();
        cm = new ColumnModel(configs);
        setBodyBorder(true);
        setButtonAlign(HorizontalAlignment.CENTER);
        setLayout(new FitLayout());
        setSize(600, 300);
        grid = new Grid<WaterPointModel>(store, cm);
        grid.setStyleAttribute("borderTop", "none");
        grid.setAutoExpandColumn("date");
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
        BufferView buffer = new BufferView();
        buffer.setScrollDelay(10);
        buffer.setRowHeight(28);
        grid.setView(buffer);
        add(grid);
        setLayout(new FitLayout());
    }

    private ListStore<Subcounty> subcounties;
    private GridFilters createFilters() {
        DateFilter dateFilter = new DateFilter("date");
        StringFilter villageFilter = new StringFilter("village");
        StringFilter parishFilter = new StringFilter("parish");
        StringFilter subcountyFilter = new StringFilter("subcounty");
        StringFilter countyFilter = new StringFilter("county");
        StringFilter districtFilter = new StringFilter("district");
        subcounties = new ListStore<Subcounty>();
        subcounties.add(Subcounty.getSampleSubcounties());
        ListFilter listFilter = new ListFilter("subcounty", subcounties);
        listFilter.setDisplayProperty("name");
        GridFilters filters = new GridFilters();
        filters.setLocal(true);
        filters.addFilter(dateFilter);
        filters.addFilter(villageFilter);
        filters.addFilter(parishFilter);
        filters.addFilter(subcountyFilter);
        filters.addFilter(countyFilter);
        filters.addFilter(districtFilter);
        filters.addFilter(listFilter);
        return filters;
    }

    private void initGrid(ListStore<WaterPointModel> store) {
        grid = new Grid<WaterPointModel>(store, cm);
        grid.setStyleAttribute("borderTop", "none");
        grid.setAutoExpandColumn("date");
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
        BufferView buffer = new BufferView();
        buffer.setScrollDelay(2);
        buffer.setRowHeight(28);
        grid.addPlugin(createFilters());
        grid.setView(buffer);
        add(grid);
    }

    @Override
    public void refresh(RefreshableEvent event) {
        System.out.println("-===================================== refresh " + event.getEventType().name());
        ProgressIndicator.showProgressBar();
        if ((event.getEventType() == RefreshableEvent.Type.ALL_WATER_POINTS)) {
            final ListStore<WaterPointModel> store1 = grid.getStore();

            final List<WaterPointSummary> waterPoints = event.getData();
            ListStore<WaterPointModel> store = new ListStore<WaterPointModel>();
            for (WaterPointSummary point : waterPoints) {
                store.add(new WaterPointModel(point));
//                System.out.println(point.getWaterPointId());
            }
            remove(grid);
            initGrid(store);
            System.out.println("done retrieving waterpoint summaries");

        }
        ProgressIndicator.hideProgressBar();
    }
}