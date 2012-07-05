/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cwf.client.views.verticaltabs;

import com.extjs.gxt.ui.client.Style.Scroll;
import org.cwf.client.custom.widget.VerticalTabItem;
import org.cwf.client.views.HomeView;
import org.cwf.client.views.widgets.WaterpointsTabPanel;

/**
 *
 * @author victor
 */
public class WaterpointsTab extends VerticalTabItem {

    private HomeView parentView;

    public WaterpointsTab(String title, HomeView parentView) {
        super(title);
        addStyleName("pad-text");
        setScrollMode(Scroll.AUTO);
        this.parentView = parentView;
        addMyWidget();

    }

    private void addMyWidget() {
        WaterpointsTabPanel waterPointsView = new WaterpointsTabPanel(parentView);
        waterPointsView.setWidth("100%");
        add(waterPointsView);
    }
}
