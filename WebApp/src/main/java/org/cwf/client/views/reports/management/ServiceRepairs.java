package org.cwf.client.views.reports.management;

import org.cwf.client.views.reports.coverage.*;
import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.GridEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.form.FieldSet;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.google.gwt.core.client.GWT;
import java.util.ArrayList;
import java.util.List;
import org.cwf.client.AppMessages;
import org.cwf.client.Refreshable;
import org.cwf.client.RefreshableEvent;
import org.cwf.client.model.ResponseTimeSummary;
import org.cwf.client.views.reports.ReportsFrame;
import org.m4water.server.admin.model.reports.ResponseTime;

/**
 *
 * @author victor
 */
public class ServiceRepairs extends ContentPanel implements Refreshable {

	final AppMessages appMessages = GWT.create(AppMessages.class);
	private Grid<ResponseTimeSummary> grid;
	private ColumnModel cm;
	private ListStore<ResponseTimeSummary> store;
	private ReportsFrame parentView;

	public ServiceRepairs() {
		initialize();
	}

	private void initialize() {
		FieldSet responseFieldSet = new FieldSet();
		responseFieldSet.setHeading("Service And Repairs");
		responseFieldSet.setCheckboxToggle(false);

		FormLayout layout = new FormLayout();
		layout.setLabelWidth(75);
		responseFieldSet.setLayout(layout);

		List<ColumnConfig> configs = new ArrayList<ColumnConfig>();
		configs.add(new ColumnConfig("month", "Month", 100));
		configs.add(new ColumnConfig("fixed", "WaterPoints Fixed", 200));
		configs.add(new ColumnConfig("pending", "Waterpoints Pending", 200));
		configs.add(new ColumnConfig("responsetime", "Average Response Time", 200));
		configs.add(new ColumnConfig("deviation", "Deviation", 100));
		store = new ListStore<ResponseTimeSummary>();
		cm = new ColumnModel(configs);
		setBodyBorder(true);
		setHeading("Service And Repairs");
		setButtonAlign(HorizontalAlignment.CENTER);
		setLayout(new FitLayout());
		setSize(600, 300);
		grid = new Grid<ResponseTimeSummary>(store, cm);
		grid.setStyleAttribute("borderTop", "none");
		grid.setAutoWidth(true);
		grid.setBorders(false);
		grid.setStripeRows(true);
		grid.setColumnLines(true);
		grid.setColumnReordering(true);
		grid.addListener(Events.RowDoubleClick, new Listener<GridEvent<BeanModel>>() {

			@Override
			public void handleEvent(GridEvent<BeanModel> be) {
				ResponseTimeSummary summary = grid.getSelectionModel().getSelectedItem();

			}
		});
		grid.getAriaSupport().setLabelledBy(getHeader().getId() + "-label");
		responseFieldSet.add(grid);
		add(grid);
		setLayout(new FitLayout());
	}

	@Override
	public void refresh(RefreshableEvent event) {
		if (event.getEventType() == RefreshableEvent.Type.RESPONSE_TIME) {
			ListStore<ResponseTimeSummary> store1 = grid.getStore();
            if (store1.getCount() > 0) {
                store1.removeAll();
            }
			List<ResponseTime> responseTimes = event.getData();
			for (ResponseTime r : responseTimes) {
				ResponseTimeSummary summary = new ResponseTimeSummary(r.getMonth(),
						r.getWaterPointsFixed(), r.getWaterPointsPending(),
						r.getAverageResponseTime(), r.getStandardDeviation());
				store1.add(summary);
			}
		}
	}
}
