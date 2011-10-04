/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.cwf.client.views;

import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.TabPanel;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.google.gwt.core.client.GWT;
import org.cwf.client.AppMessages;

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
        waterpointsPanel.setAutoHeight(true);
        setHeading(appMessages.allWaterPoints()+":to filter values right click the colum->filters");

        TabItem baseLineNotdoneTab = new TabItem(appMessages.baseLineNotDone());
        baseLineNotdoneTab.setLayout(new FitLayout());
        AvailableWaterpointsView waterpoints1 = new AvailableWaterpointsView(parentView,appMessages.baseLineNotDone());
        waterpoints1.setWidth("100%");
        waterpoints1.setHeading(appMessages.allWaterPoints());
        baseLineNotdoneTab.add(waterpoints1);

        TabItem baseLineForReview = new TabItem(appMessages.baseLineForReview());
        baseLineForReview.setLayout(new FitLayout());
        AvailableWaterpointsView waterpoints2  = new AvailableWaterpointsView(parentView,appMessages.baseLineForReview());
        waterpoints2.setWidth("100%");
        waterpoints2.setHeading(appMessages.allWaterPoints());
        baseLineForReview.add(waterpoints2);

        TabItem baselineComplete = new TabItem(appMessages.baseLineDataComplete());
        baselineComplete.setLayout(new FitLayout());
        AvailableWaterpointsView waterpoints3  = new AvailableWaterpointsView(parentView,appMessages.baseLineDataComplete());
        waterpoints3.setWidth("100%");
        waterpoints3.setHeading(appMessages.allWaterPoints());
        baselineComplete.add(waterpoints3);

        waterpointsPanel.add(baseLineNotdoneTab);
        waterpointsPanel.add(baseLineForReview);
        waterpointsPanel.add(baselineComplete);
        add(waterpointsPanel);
        setLayout(new FitLayout());
    }
}
