/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cwf.client.views.reports;

import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.View;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.google.gwt.core.client.GWT;
import org.cwf.client.AppMessages;
import org.cwf.client.views.M4WaterReportsTabPanel;
import org.m4water.server.admin.model.User;

/**
 *
 * @author victor
 */
public class ReportsFrame extends View{

    protected final AppMessages appMessages = GWT.create(AppMessages.class);
    private String heading;
    private Window window;
    public int mainWindowHeight;
    public int mainWindowWidth;
    public User loggedUser;
    private M4WaterReportsTabPanel panel;

    public ReportsFrame(Controller controller) {
        super(controller);
    }

    @Override
    protected void initialize() {
        panel = new M4WaterReportsTabPanel("M4WATER Reports");
        window = new Window();
        window.setModal(true);
        window.setPlain(true);
        window.setMaximizable(true);
        window.setDraggable(true);
        window.setResizable(true);
        window.setScrollMode(Scroll.AUTOY);
        window.setLayout(new FitLayout());
        window.add(panel);
    }

    public void showWindow(String heading, int width, int height) {
        GWT.log("ReportsFrame : createWindow");
        window.setSize(width, height);
        window.show();
    }

    @Override
    protected void handleEvent(AppEvent event) {
        int width = com.google.gwt.user.client.Window.getClientWidth() - 50;
        int height = com.google.gwt.user.client.Window.getClientHeight() - 50;
        loggedUser = event.getData();
        showWindow("M4WATER Reports", width, height);
    }

}
