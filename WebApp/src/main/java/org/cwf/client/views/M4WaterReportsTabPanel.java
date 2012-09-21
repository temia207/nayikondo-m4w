package org.cwf.client.views;

import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import org.cwf.client.custom.widget.VerticalTabPanel;
import org.cwf.client.views.reports.ReportsFrame;
import org.cwf.client.views.reports.reporttabs.CoverageTab;
import org.cwf.client.views.reports.reporttabs.FunctionalityTab;
import org.cwf.client.views.reports.reporttabs.ManagementTab;

/**
 *
 * @author victor
 */
public class M4WaterReportsTabPanel extends ContentPanel {

    private VerticalTabPanel panel;
    private ReportsFrame parentView;

    public M4WaterReportsTabPanel(String title) {
        setHeading(title);
        initUi();

    }

    private void initUi() {
        setHeight("500px");
        setHeaderVisible(true);
        panel = new VerticalTabPanel();
        panel.setPlain(true);
        panel.setSize(1200, 450);
        panel.setTabWidth(170);

        VerticalPanel vp = new VerticalPanel();
        vp.setSpacing(10);
        vp.setLayout(new FitLayout());
        //functionality page
        FunctionalityTab functionality = new FunctionalityTab("Functionality");
        panel.add(functionality);

        CoverageTab coverage = new CoverageTab("Coverage");
        panel.add(coverage);

        ManagementTab management = new ManagementTab("Management");
        panel.add(management);
        vp.add(panel);
        add(vp);
    }
}
