/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cwf.client.controllers;

import com.extjs.gxt.ui.client.event.EventType;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.google.gwt.core.client.GWT;
import org.cwf.client.AppMessages;
import org.cwf.client.M4waterAsyncCallback;
import org.cwf.client.RefreshableEvent;
import org.cwf.client.RefreshablePublisher;
import org.cwf.client.service.SettingServiceAsync;
import org.cwf.client.service.WaterPointServiceAsync;
import org.cwf.client.views.EditNewWaterPointsView;
import org.m4water.server.admin.model.SettingGroup;
import org.m4water.server.admin.model.Waterpoint;

/**
 *
 * @author victor
 */
public class EditNewWaterPointController extends Controller {

    AppMessages appMessages = GWT.create(AppMessages.class);
    public final static EventType EDIT_NEW_WATER_POINT = new EventType();
    private EditNewWaterPointsView editNewWaterpointView;
    WaterPointServiceAsync waterpointService;
    SettingServiceAsync settingService;

    public EditNewWaterPointController(WaterPointServiceAsync aWaterPointAsync, SettingServiceAsync aSettingAsync) {
        super();
        this.waterpointService = aWaterPointAsync;
        this.settingService = aSettingAsync;
        registerEventTypes(EDIT_NEW_WATER_POINT);
    }

    @Override
    protected void initialize() {
        GWT.log("EditWaterPointController  : initialize");
        editNewWaterpointView= new EditNewWaterPointsView(this);
    }

    @Override
    public void handleEvent(AppEvent event) {
         GWT.log("EditNewWaterPointController  :handle event");
        EventType type = event.getType();
        if (type == EDIT_NEW_WATER_POINT) {
            forwardToView(editNewWaterpointView, event);
        }
    }

    public void saveWaterPoint(Waterpoint waterPoint){
        GWT.log("HomeController : saveWaterPoint(Waterpoint waterPoint)");
        waterpointService.saveWaterPoint(waterPoint, new M4waterAsyncCallback<Void>() {

            @Override
            public void onSuccess(Void result) {
                editNewWaterpointView.closeWindow();
            }
        });
    }
    public void saveSettingGroup(SettingGroup group){
    GWT.log("HomeController: save setting group");
    settingService.saveSettingGroup(group, new M4waterAsyncCallback<Void>() {

            @Override
            public void onSuccess(Void result) {
		getNewWaterPoints();
                editNewWaterpointView.closeWindow();
            }
        });
    }

    public  void exportSettingGroupToWaterPoint(SettingGroup group){
	GWT.log("HomeController: exportSettingGroupToWaterPoint(SettingGroup group)");
	settingService.exportSettingGroupToWaterPoint(group, new M4waterAsyncCallback<Void>() {

	    @Override
	    public void onSuccess(Void result) {
                RefreshablePublisher.get().publish(new RefreshableEvent(RefreshableEvent.Type.RELOAD_WATERPOINTS, result));
		editNewWaterpointView.closeWindow();
	    }
	});
    }

    public void getNewWaterPoints() {
        GWT.log("HomeController : getNewWaterPoints()");
        settingService.getSettingGroup("waterpoints", new M4waterAsyncCallback<SettingGroup>() {

            @Override
            public void onSuccess(SettingGroup result) {
                GWT.log("HomeController : waterpointsummarries found");
                RefreshablePublisher.get().publish(new RefreshableEvent(RefreshableEvent.Type.NEW_WATER_POINTS, result));
            }
        });
    }

}
