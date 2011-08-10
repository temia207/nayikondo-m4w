/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cwf.client.views;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
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
import org.cwf.client.model.WaterPointSummary;

/**
 *
 * @author victor
 */
public class NewWaterPointsView extends ContentPanel {

    final AppMessages appMessages = GWT.create(AppMessages.class);
    private Grid<WaterPointSummary> grid;
    private ColumnModel cm;

    public NewWaterPointsView() {
        initialize();
    }

    private void initialize() {
        List<ColumnConfig> configs = new ArrayList<ColumnConfig>();
        configs.add(new ColumnConfig("date", "Date", 100));
        configs.add(new ColumnConfig("id", "ID", 100));
        configs.add(new ColumnConfig("district", "District", 100));
        configs.add(new ColumnConfig("subcounty", "Subcounty", 100));
        configs.add(new ColumnConfig("village", "Village", 100));
        ListStore<WaterPointSummary> store = new ListStore<WaterPointSummary>();
        store.add(WaterPointSummary.getSampleNewWaterPoints());

        cm = new ColumnModel(configs);
        setBodyBorder(true);
        setHeading("New Water Points");
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
        grid.getAriaSupport().setLabelledBy(getHeader().getId() + "-label");
        add(grid);
        setLayout(new FitLayout());
    }
}
