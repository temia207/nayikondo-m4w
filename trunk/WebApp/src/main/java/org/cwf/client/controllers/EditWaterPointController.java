package org.cwf.client.controllers;

import com.extjs.gxt.ui.client.event.EventType;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.google.gwt.core.client.GWT;
import java.util.List;
import org.cwf.client.AppMessages;
import org.cwf.client.M4waterAsyncCallback;
import org.cwf.client.model.WaterPointModel;
import org.cwf.client.service.WaterPointServiceAsync;
import org.cwf.client.views.EditWaterPointView;
import org.m4water.server.admin.model.Waterpoint;

/**
 *
 * @author victor
 */
public class EditWaterPointController extends Controller {

    AppMessages appMessages = GWT.create(AppMessages.class);
    public final static EventType EDIT_WATER_POINT = new EventType();
    private EditWaterPointView editWaterPointView;
    WaterPointServiceAsync waterpointService;

    public EditWaterPointController(WaterPointServiceAsync aWaterPointService) {
        super();
        waterpointService = aWaterPointService;
        registerEventTypes(EDIT_WATER_POINT);
    }

    @Override
    protected void initialize() {
        GWT.log("EditWaterPointController  : initialize");
        editWaterPointView = new EditWaterPointView(this);
    }

    @Override
    public void handleEvent(AppEvent event) {
        GWT.log("EditWaterPointController  :handle event");
        EventType type = event.getType();
        if (type == EDIT_WATER_POINT) {
            forwardToView(editWaterPointView, event);
        }
    }
    public void getWaterPoint(String waterPointId){
        GWT.log("HomeController : getWaterPoint(String waterPointId)");
        waterpointService.getWaterPoint(waterPointId, new M4waterAsyncCallback<Waterpoint>() {

            @Override
            public void onSuccess(Waterpoint result) {
                editWaterPointView.setWaterPointData(result);
            }
        });
    }
}
