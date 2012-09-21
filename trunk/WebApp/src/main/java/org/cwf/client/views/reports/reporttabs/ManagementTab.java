/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cwf.client.views.reports.reporttabs;

import com.extjs.gxt.ui.client.Style.Scroll;
import org.cwf.client.custom.widget.VerticalTabItem;
import org.cwf.client.views.reports.ManagementView;
import org.cwf.client.views.reports.ReportsFrame;

/**
 *
 * @author victor
 */
public class ManagementTab extends VerticalTabItem {

    private ReportsFrame parentView;

    public ManagementTab(String title) {

        super(title);
        addStyleName("pad-text");
        setScrollMode(Scroll.AUTO);
        addMyWidget();
    }

    private void addMyWidget() {
        ManagementView managementView = new ManagementView("Management");
        managementView.setWidth("100%");
        add(managementView);
    }
}
