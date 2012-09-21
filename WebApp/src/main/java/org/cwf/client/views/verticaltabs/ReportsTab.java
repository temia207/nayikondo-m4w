/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cwf.client.views.verticaltabs;

import com.extjs.gxt.ui.client.Style.Scroll;
import org.cwf.client.custom.widget.VerticalTabItem;
import org.cwf.client.views.HomeView;
import org.cwf.client.views.M4WaterReportsTabPanel;

/**
 *
 * @author victor
 */
public class ReportsTab extends VerticalTabItem {

    private HomeView parentView;

    public ReportsTab(String title, HomeView parentView) {
        super(title);
        addStyleName("pad-text");
        setScrollMode(Scroll.AUTO);
        this.parentView = parentView;
        addMyWidget();
    }

    private void addMyWidget() {
        M4WaterReportsTabPanel panel = new M4WaterReportsTabPanel("M4water Reports");
        add(panel);
    }
}
