package org.cwf.client.views.reports.coverage;

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
import org.cwf.client.model.DistrictComparisonSummary;
import org.cwf.client.views.reports.ReportsFrame;
import org.m4water.server.admin.model.User;
import org.m4water.server.admin.model.reports.DistrictComparisons;

/**
 *
 * @author victor
 */
public class SubcountyComparison extends ContentPanel implements Refreshable {

	final AppMessages appMessages = GWT.create(AppMessages.class);
	private Grid<DistrictComparisonSummary> grid;
	private ColumnModel cm;
	private ListStore<DistrictComparisonSummary> store;
	private ReportsFrame parentView;

	public SubcountyComparison(ReportsFrame parent) {
		this.parentView = parent;
		initialize();
	}

	private void initialize() {
		FieldSet responseFieldSet = new FieldSet();
		responseFieldSet.setHeading("District Comparisons");
		responseFieldSet.setCheckboxToggle(false);

		FormLayout layout = new FormLayout();
		layout.setLabelWidth(75);
		responseFieldSet.setLayout(layout);

		List<ColumnConfig> configs = new ArrayList<ColumnConfig>();
//		configs.add(new ColumnConfig("district", "District", 100));
		configs.add(new ColumnConfig("subcounty", "Subcounty", 200));
		configs.add(new ColumnConfig("bh", "Boreholes", 200));
		configs.add(new ColumnConfig("sw", "Shallow Wells", 200));
		configs.add(new ColumnConfig("yt", "Public Taps", 100));
		configs.add(new ColumnConfig("ps", "Protected Springs", 100));
		configs.add(new ColumnConfig("total", "Total", 100));
		store = new ListStore<DistrictComparisonSummary>();
		cm = new ColumnModel(configs);
		setBodyBorder(true);
		setHeading("District Comparisons");
		setButtonAlign(HorizontalAlignment.CENTER);
		setLayout(new FitLayout());
		setSize(600, 300);
		grid = new Grid<DistrictComparisonSummary>(store, cm);
		grid.setStyleAttribute("borderTop", "none");
		grid.setAutoWidth(true);
		grid.setBorders(false);
		grid.setStripeRows(true);
		grid.setColumnLines(true);
		grid.setColumnReordering(true);
		grid.addListener(Events.RowDoubleClick, new Listener<GridEvent<BeanModel>>() {

			@Override
			public void handleEvent(GridEvent<BeanModel> be) {
				DistrictComparisonSummary summary = grid.getSelectionModel().getSelectedItem();

			}
		});
		grid.getAriaSupport().setLabelledBy(getHeader().getId() + "-label");
		responseFieldSet.add(grid);
		add(grid);
		setLayout(new FitLayout());
	}

	@Override
	public void refresh(RefreshableEvent event) {
		if (event.getEventType() == RefreshableEvent.Type.DISTRICT_SUMMARIES) {
			ListStore<DistrictComparisonSummary> store1 = grid.getStore();
			if (store1.getCount() > 0) {
				store1.removeAll();
			}
			List<DistrictComparisons> summaries = getSummaries((List<DistrictComparisons>) event.getData(),parentView.loggedUser);
			for (DistrictComparisons d : summaries) {
				final String district = d.getDistrict();
				final String subcounty = d.getSubcounty();
				final String boreholes = d.getBoreholes();
				final String shallowWells = d.getShallowWells();
				final String publicTaps = d.getPublicTaps();
				final String protectedSprings = d.getProtectedSprings();
				int total = Integer.parseInt(boreholes)+Integer.parseInt(shallowWells)+Integer.parseInt(publicTaps)+
						Integer.parseInt(protectedSprings);
				DistrictComparisonSummary summary = new DistrictComparisonSummary(district, subcounty, boreholes,
						shallowWells, publicTaps, protectedSprings,String.valueOf(total));
				store1.add(summary);
			}
		}
	}

	private List<DistrictComparisons> getSummaries(List<DistrictComparisons> list,User user) {
		List<DistrictComparisons> result = new ArrayList<DistrictComparisons>();
		for (DistrictComparisons comparison : list) {
			if (comparison.getDistrict().equals(user.getSubcounty().getCounty().getDistrict().getName())) {
				result.add(comparison);
			}
		}
		return result;
	}
}
