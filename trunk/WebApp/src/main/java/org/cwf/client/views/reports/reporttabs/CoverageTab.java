/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cwf.client.views.reports.reporttabs;

import com.extjs.gxt.ui.client.Style.Scroll;
import org.cwf.client.custom.widget.VerticalTabItem;
import org.cwf.client.views.reports.CoverageView;
import org.cwf.client.views.reports.ReportsFrame;

/**
 *
 * @author victor
 */
public class CoverageTab extends VerticalTabItem {

    private ReportsFrame parentView;

    public CoverageTab(String title) {
        super(title);
        addStyleName("pad-text");
        setScrollMode(Scroll.AUTO);
        addMyWidget();
    }

    private void addMyWidget() {
        CoverageView coverageView = new CoverageView("Coverage");
        coverageView.setWidth("100%");
        add(coverageView);

    }
}
