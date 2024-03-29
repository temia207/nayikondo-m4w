/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.cwf.client.views.widgets;

import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.TabPanel;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.google.gwt.core.client.GWT;
import org.cwf.client.AppMessages;
import org.cwf.client.RefreshableEvent;
import org.cwf.client.views.AvailableWaterpointsView;
import org.cwf.client.views.HomeView;
import org.cwf.client.RefreshablePublisher;
import org.cwf.client.views.NewWaterPointsView;

/**
 *
 * @author victor
 */
public class WaterpointsTabPanel extends ContentPanel {

    final AppMessages appMessages = GWT.create(AppMessages.class);
    private TabPanel waterpointsPanel;
    private HomeView parentView;

    public WaterpointsTabPanel(HomeView view) {
        this.parentView = view;
        initialize();
    }

    private void initialize() {
        waterpointsPanel = new TabPanel();
        waterpointsPanel.setWidth(600);
//        waterpointsPanel.setAutoHeight(true);
        waterpointsPanel.setHeight(340);
        setHeading(appMessages.allWaterPoints()+":to filter values right click the colum->filters");

        TabItem newWaterpoints = new TabItem(appMessages.newWaterPoints());
        newWaterpoints.setLayout(new FitLayout());
        NewWaterPointsView waterpoints1 = new NewWaterPointsView(parentView,appMessages.newWaterPoints());
        waterpoints1.setWidth("100%");
        waterpoints1.setHeading(appMessages.allWaterPoints());
        RefreshablePublisher.get().subscribe(RefreshableEvent.Type.NEW_WATER_POINTS,waterpoints1);
        newWaterpoints.add(waterpoints1);

        TabItem baseLineForReview = new TabItem(appMessages.baseLineNotDone());
        baseLineForReview.setLayout(new FitLayout());
        AvailableWaterpointsView waterpoints2  = new AvailableWaterpointsView(parentView,appMessages.baseLineNotDone());
        waterpoints2.setWidth("100%");
        waterpoints2.setHeading(appMessages.allWaterPoints());
        RefreshablePublisher.get().subscribe(RefreshableEvent.Type.BASELINE_NOT_DONE,waterpoints2);
        RefreshablePublisher.get().subscribe(RefreshableEvent.Type.WATERPOINT_CHANGES,waterpoints2);
        baseLineForReview.add(waterpoints2);

        TabItem baselineComplete = new TabItem(appMessages.baseLineDataComplete());
        baselineComplete.setLayout(new FitLayout());
        AvailableWaterpointsView waterpoints3  = new AvailableWaterpointsView(parentView,appMessages.baseLineDataComplete());
        waterpoints3.setWidth("100%");
        waterpoints3.setHeading(appMessages.allWaterPoints());
        RefreshablePublisher.get().subscribe(RefreshableEvent.Type.BASELINE_COMPLETE,waterpoints3);
        baselineComplete.add(waterpoints3);

        TabItem pendingBaseline = new TabItem(appMessages.pendingBaseline());
        pendingBaseline.setLayout(new FitLayout());
        AvailableWaterpointsView waterpoints4  = new AvailableWaterpointsView(parentView,appMessages.pendingBaseline());
        waterpoints4.setWidth("100%");
        waterpoints4.setHeading(appMessages.pendingBaseline());
        RefreshablePublisher.get().subscribe(RefreshableEvent.Type.BASELINE_PENDING,waterpoints4);
        RefreshablePublisher.get().subscribe(RefreshableEvent.Type.WATERPOINT_CHANGES,waterpoints4);
        pendingBaseline.add(waterpoints4);

        waterpointsPanel.add(baselineComplete);
        waterpointsPanel.add(baseLineForReview);
        waterpointsPanel.add(newWaterpoints);
        waterpointsPanel.add(pendingBaseline);
        add(waterpointsPanel);
        setLayout(new FitLayout());
    }
}
