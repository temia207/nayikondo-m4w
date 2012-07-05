/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cwf.client.views;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.Style.SortDir;
import com.extjs.gxt.ui.client.data.BasePagingLoadConfig;
import com.extjs.gxt.ui.client.data.BasePagingLoader;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.GridEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.MessageBoxEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.Dialog;
import com.extjs.gxt.ui.client.widget.MessageBox;
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
import com.google.gwt.user.client.rpc.AsyncCallback;
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
import com.extjs.gxt.ui.client.data.PagingLoader;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.user.client.Timer;

/**
 *
 * @author victor
 */
public class AvailableWaterpointsView extends ContentPanel implements Refreshable {

    public static final int PAGE_SIZE = 20;
    final AppMessages appMessages = GWT.create(AppMessages.class);
    private Grid<WaterPointModel> grid;
    private ColumnModel cm;
    //type may be new waterpoints or all waterpoints
    private String type;
    private HomeView parentView;
    private ListStore<Subcounty> subcounties, districts, counties, parishes, villages;
    private Button launchBaseline, btnCancelBaseline, exportToExcelBtn;
    private List<WaterPointSummary> waterpointSummaries = new ArrayList<WaterPointSummary>();
    private ListStore<WaterPointModel> store;
    private AdjustablePagingToolBar toolBar;
    private PagingLoader<PagingLoadResult<ModelData>> loader;

    public AvailableWaterpointsView(HomeView view, String type) {
        this.type = type;
        this.parentView = view;
        initializePagingLoader();
        initialize();
    }

    private void initialize() {
        List<ColumnConfig> configs = new ArrayList<ColumnConfig>();
        CheckBoxSelectionModel<WaterPointModel> sm = new CheckBoxSelectionModel<WaterPointModel>();
        configs.add(sm.getColumn());
        configs.add(new ColumnConfig("date", "Date", 100));
        configs.add(new ColumnConfig("id", "ID", 100));
        configs.add(new ColumnConfig("village", "Village", 100));
        configs.add(new ColumnConfig("parish", "Parish", 100));
        configs.add(new ColumnConfig("subcounty", "Subcounty", 100));
        configs.add(new ColumnConfig("county", "County", 100));
        configs.add(new ColumnConfig("district", "District", 100));
        store = new ListStore<WaterPointModel>(loader);
        cm = new ColumnModel(configs);
        setBodyBorder(true);
        setButtonAlign(HorizontalAlignment.CENTER);
        setLayout(new FitLayout());
        setSize(600, 340);

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
//        grid.addPlugin(createFilters());
        grid.setSelectionModel(sm);
        grid.addPlugin(sm);
        BufferView buffer = new BufferView();
        buffer.setScrollDelay(10);
        buffer.setRowHeight(28);
        grid.setView(buffer);

        Scheduler.get().scheduleDeferred(new ScheduledCommand() {

            @Override
            public void execute() {
                // load the first set of data
                PagingLoadConfig config = new BasePagingLoadConfig(0, PAGE_SIZE);
                loader.load(config);
            }
        });
        add(grid);
        setBottomComponent(toolBar);
        launchBaseline = new Button("Carry out Base Line");
        launchBaseline.addSelectionListener(new SelectionListener<ButtonEvent>() {

            @Override
            public void componentSelected(ButtonEvent ce) {

                MessageBox.confirm(appMessages.cancel(), appMessages.areYouSure(), new Listener<MessageBoxEvent>() {

                    @Override
                    public void handleEvent(MessageBoxEvent be) {
                        if (be.getButtonClicked().getItemId().equals(Dialog.YES)) {
                            //launch baseline work flow
                            WaterPointModel point = grid.getSelectionModel().getSelectedItem();
                            HomeController controller = (HomeController) parentView.getController();
                            controller.launchBaseline(point.getId());
                            RefreshablePublisher.get().publish(new RefreshableEvent(RefreshableEvent.Type.WATERPOINT_CHANGES, point.getWaterPointSummary()));
                            store.remove(grid.getSelectionModel().getSelectedItem());
                            grid.setSelectionModel(new GridSelectionModel<WaterPointModel>());
                        }
                    }
                });
            }
        });

        btnCancelBaseline = new Button("Cancel Baseline");
        btnCancelBaseline.addSelectionListener(new SelectionListener<ButtonEvent>() {

            @Override
            public void componentSelected(ButtonEvent ce) {


                MessageBox.confirm(appMessages.cancel(), appMessages.areYouSure(), new Listener<MessageBoxEvent>() {

                    @Override
                    public void handleEvent(MessageBoxEvent be) {
                        if (be.getButtonClicked().getItemId().equals(Dialog.YES)) {
                            cancelBaseline();
                        }
                    }
                });
            }
        });
        exportToExcelBtn = new Button("Export To Excel");
        exportToExcelBtn.addSelectionListener(new SelectionListener<ButtonEvent>() {

            @Override
            public void componentSelected(ButtonEvent ce) {
                try {
                    int actionId = HomeController.ALL_WATERPOINTS;
                    if (type.equals(appMessages.baseLineDataComplete())) {
                        actionId = HomeController.BASELINE_DONE;
                    } else if (type.equals(appMessages.baseLineNotDone())) {
                        actionId = HomeController.BASELINE_NOT_DONE;
                    } else if (type.equals(appMessages.pendingBaseline())) {
                        actionId = HomeController.BASELINE_PENDING;
                    }
                    action(actionId);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        exportToExcelBtn.setType("submit");
//        if (type.equalsIgnoreCase(appMessages.baseLineNotDone())) {
//            addButton(launchBaseline);
//        } else if (type.equalsIgnoreCase(appMessages.newWaterPoints())) {
//            launchBaseline.hide();
//        } else if (type.equalsIgnoreCase(appMessages.baseLineDataComplete())) {
//            launchBaseline.setText("Redo Baseline");
//            addButton(launchBaseline);
//        } else if (type.equalsIgnoreCase(appMessages.pendingBaseline())) {
//            addButton(btnCancelBaseline);
//        }
        addButton(exportToExcelBtn);
        setLayout(new FitLayout());
    }

    private GridFilters createFilters() {
        DateFilter dateFilter = new DateFilter("date");
        ListFilter districtFilter = new ListFilter("district", districts);
        districtFilter.setDisplayProperty("name");
        ListFilter countyFilter = new ListFilter("county", counties);
        countyFilter.setDisplayProperty("name");
        StringFilter subcountyFilter = new StringFilter("subcounty");
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
        if ((event.getEventType() == RefreshableEvent.Type.WATERPOINT_CHANGES)) {
            GWT.log("Refreshable event ===== " + RefreshableEvent.Type.WATERPOINT_CHANGES);
            if (type.equalsIgnoreCase(appMessages.pendingBaseline())) {
                WaterPointSummary summary = event.getData();
                ListStore<WaterPointModel> store1 = grid.getStore();
                store1.add(new WaterPointModel(summary));
            }
        }
//        toolBar.refresh();
        ProgressIndicator.hideProgressBar();
    }

    private void cancelBaseline() {
        WaterPointModel selectedItem = grid.getSelectionModel().getSelectedItem();
        String baselinePending = selectedItem.getWaterPointSummary().getBaselinePending();
        int caseId = 0;
        try {
            caseId = Integer.parseInt(baselinePending);
        } catch (Exception ex) {
            MessageBox.alert("Warning", "Baseling Cannot be Canceled because the Yawl ID Is Not Available", null);
        }
        HomeController controller = (HomeController) parentView.getController();
        controller.cancelCase(caseId + "");
    }

    private void action(int id) {
        HomeController controller = (HomeController) parentView.getController();
        controller.exportEditable(id, "newwaterpoint.xls");
    }

    private void initializePagingLoader() {

        toolBar = new AdjustablePagingToolBar(PAGE_SIZE);
        loader = new BasePagingLoader<PagingLoadResult<ModelData>>(new RpcProxy<PagingLoadResult<WaterPointModel>>() {

            @Override
            protected void load(Object loadConfig, final AsyncCallback<PagingLoadResult<WaterPointModel>> callback) {
                final PagingLoadConfig pagingLoadConfig = (PagingLoadConfig) loadConfig;
//               // we need to manually set page size because we allow
                pagingLoadConfig.setLimit(toolBar.getPageSize());
                Scheduler.get().scheduleDeferred(new ScheduledCommand() {

                    @Override
                    public void execute() {
                        fetchData(pagingLoadConfig, callback);
                    }
                });
            }
        });
        loader.setRemoteSort(true);
        loader.setSortField("district");
        loader.setSortDir(SortDir.ASC);
        toolBar.bind(loader);
    }

    private void fetchData(final PagingLoadConfig pagingLoadConfig, final AsyncCallback<PagingLoadResult<WaterPointModel>> callback) {
        final HomeController controller = (HomeController) parentView.getController();
//wait for homeview to get baseline date which will be used in filtering waterpoints
        Timer t = new Timer() {

            @Override
            public void run() {
                boolean nulldate = false;
                while (!nulldate) {
                    if (parentView.baselineSetDate != null) {
                        String baselineStatus = "";
                        if (type.equalsIgnoreCase(appMessages.pendingBaseline())) {
                            baselineStatus = "T";
                        } else if (type.equalsIgnoreCase(appMessages.baseLineDataComplete())) {
                            baselineStatus = "C";
                        } else if (type.equalsIgnoreCase(appMessages.baseLineNotDone())) {
                            baselineStatus = "F";
                        }
//                                controller.getPagedWaterPointSummaries(pagingLoadConfig, parentView.districtName,
//                                        baselineStatus, parentView.baselineSetDate, callback);

                        nulldate = true;
                    }
                }
            }
        };
        t.schedule(1000);
    }
}
