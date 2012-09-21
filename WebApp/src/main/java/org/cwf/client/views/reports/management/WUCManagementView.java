package org.cwf.client.views.reports.management;

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
import org.cwf.client.model.WaterManagementSummary;
import org.cwf.client.views.reports.ReportsFrame;
import org.m4water.server.admin.model.reports.ResponseTime;
import org.m4water.server.admin.model.reports.WucManagement;

/**
 *
 * @author victor
 */
public class WUCManagementView extends ContentPanel implements Refreshable {

	final AppMessages appMessages = GWT.create(AppMessages.class);
	private Grid<WaterManagementSummary> grid;
	private ColumnModel cm;
	private ListStore<WaterManagementSummary> store;
	private ReportsFrame parentView;

	public WUCManagementView() {
		initialize();
	}

	private void initialize() {
		FieldSet responseFieldSet = new FieldSet();
		responseFieldSet.setHeading("WUC Management");
		responseFieldSet.setCheckboxToggle(false);

		FormLayout layout = new FormLayout();
		layout.setLabelWidth(75);
		responseFieldSet.setLayout(layout);

		List<ColumnConfig> configs = new ArrayList<ColumnConfig>();
		configs.add(new ColumnConfig("subcounty", "Subcounty", 100));
		configs.add(new ColumnConfig("sources", "Sources", 200));
		configs.add(new ColumnConfig("wucEstablished", "WUC Established", 200));
		configs.add(new ColumnConfig("wucTrained", "WUC Trained", 200));
		configs.add(new ColumnConfig("noOfWomen", "Women", 100));
		configs.add(new ColumnConfig("womenInKeyPositions", "Women in KeyPositions", 200));
		store = new ListStore<WaterManagementSummary>();
		cm = new ColumnModel(configs);
		setBodyBorder(true);
		setHeading("WUC Management");
		setButtonAlign(HorizontalAlignment.CENTER);
		setLayout(new FitLayout());
		setSize(600, 300);
		grid = new Grid<WaterManagementSummary>(store, cm);
		grid.setStyleAttribute("borderTop", "none");
		grid.setAutoWidth(true);
		grid.setBorders(false);
		grid.setStripeRows(true);
		grid.setColumnLines(true);
		grid.setColumnReordering(true);
		grid.addListener(Events.RowDoubleClick, new Listener<GridEvent<BeanModel>>() {

			@Override
			public void handleEvent(GridEvent<BeanModel> be) {
				WaterManagementSummary summary = grid.getSelectionModel().getSelectedItem();

			}
		});
		grid.getAriaSupport().setLabelledBy(getHeader().getId() + "-label");
		responseFieldSet.add(grid);
		add(grid);
		setLayout(new FitLayout());
	}

	@Override
	public void refresh(RefreshableEvent event) {
		if (event.getEventType() == RefreshableEvent.Type.WUC_MANAGEMENT) {
			ListStore<WaterManagementSummary> store1 = grid.getStore();
            if (store1.getCount() > 0) {
                store1.removeAll();
            }
			List<WucManagement> responseTimes = event.getData();
			for (WucManagement w : responseTimes) {
				WaterManagementSummary summary = new WaterManagementSummary(w.getSubcounty(),
						w.getParish(), w.getSources(),
						w.getWucEstablished(), w.getWucTrained(),w.getNoOfWomen(),w.getWomenInKeyPositions());
				store1.add(summary);
			}
		}
	}
}
