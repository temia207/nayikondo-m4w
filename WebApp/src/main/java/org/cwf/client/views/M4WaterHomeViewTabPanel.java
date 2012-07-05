/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cwf.client.views;

import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import org.cwf.client.custom.widget.VerticalTabPanel;
import org.cwf.client.views.verticaltabs.ReportsTab;
import org.cwf.client.views.verticaltabs.TicketsTab;
import org.cwf.client.views.verticaltabs.UsersTab;
import org.cwf.client.views.verticaltabs.WaterpointsTab;

/**
 *
 * @author victor
 */
public class M4WaterHomeViewTabPanel extends ContentPanel {

    private VerticalTabPanel panel;
    private HomeView parentView;

    public M4WaterHomeViewTabPanel(HomeView parentView) {
        this.parentView = parentView;
        initUi();
    }

    private void initUi() {
        setHeight("500px");
        setHeaderVisible(false);
        panel = new VerticalTabPanel();
        panel.setPlain(true);
        panel.setSize(1300, 450);
        panel.setTabWidth(170);

        VerticalPanel vp = new VerticalPanel();
        vp.setSpacing(10);
        vp.setLayout(new FitLayout());

        TicketsTab tickets = new TicketsTab("Ticketss", parentView);
        panel.add(tickets);

        WaterpointsTab teacherEnrollmentFrm = new WaterpointsTab("Waterpoint", parentView);
        teacherEnrollmentFrm.setScrollMode(Scroll.AUTO);
        panel.add(teacherEnrollmentFrm);

        ReportsTab reportsTab = new ReportsTab("Reports", parentView);
        panel.add(reportsTab);

        UsersTab usersTab = new UsersTab("Users", parentView);
        panel.add(usersTab);

        vp.add(panel);
        add(vp);
    }
}
