package org.cwf.client.views;

import org.cwf.client.views.widgets.OtherParametersFieldset;
import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.View;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.google.gwt.core.client.GWT;
import org.cwf.client.AppMessages;
import org.cwf.client.controllers.EditWaterPointController;
import org.cwf.client.model.WaterPointModel;
import org.m4water.server.admin.model.Waterpoint;

/**
 *
 * @author victor
 */
public class EditWaterPointView extends View {

    final AppMessages appMessages = GWT.create(AppMessages.class);
    private Window window;
    private TextField<String> idTextFld, districtTfld, subcountyTfld, villageTfld, otherNameTfld, eastingsTfld, northingsTfld;
    private TextField<String> waterPointNameTfld, dateIstalledTfld, fundingSrcTfld, ownershipTfld, houseHoldsTfld, typeOfMagtTfld;
    private Button saveChangesBtn, confirmBtn, cancelBtn;
    private FormPanel formPanel;
    private final OtherParametersFieldset otherParameters = new OtherParametersFieldset();
    private Waterpoint waterPoint;
//    private WaterPointModel waterPointSummary;

    public EditWaterPointView(Controller controller) {
        super(controller);
    }

    @Override
    protected void initialize() {
        window = new Window();
        window.setHeading("Waterpoint Details");

        formPanel = new FormPanel();
        formPanel.setFrame(false);
        formPanel.setBorders(false);
        formPanel.setBodyBorder(false);
        formPanel.setHeaderVisible(false);
        FormLayout layout = new FormLayout();
        layout.setLabelWidth(150);
        formPanel.setLayout(layout);
        initializeTextfields();
        formPanel.add(idTextFld);
        formPanel.add(waterPointNameTfld);
        formPanel.add(districtTfld);
        formPanel.add(subcountyTfld);
        formPanel.add(villageTfld);
        formPanel.add(eastingsTfld);
        formPanel.add(northingsTfld);
        formPanel.add(dateIstalledTfld);
        formPanel.add(fundingSrcTfld);
        formPanel.add(ownershipTfld);
        formPanel.add(houseHoldsTfld);
        formPanel.add(typeOfMagtTfld);
        formPanel.setButtonAlign(HorizontalAlignment.CENTER);
//        formPanel.add(otherParameters);

        createButtons();
        formPanel.addButton(saveChangesBtn);
        formPanel.addButton(confirmBtn);
        formPanel.addButton(cancelBtn);

        window.setAutoHeight(true);
        window.setWidth(425);
        window.setPlain(true);
        window.add(formPanel);
        window.setDraggable(true);
        window.setResizable(true);
        window.setScrollMode(Scroll.AUTO);
        window.show();
        window.setModal(true);
    }

    public void save(Waterpoint waterpoint) {
        waterpoint.setWaterpointId(idTextFld.getValue().trim());
        waterpoint.setName(waterPointNameTfld.getValue().trim());
        waterpoint.setFundingSource(fundingSrcTfld.getValue().trim());
        waterpoint.setHouseholds(houseHoldsTfld.getValue().trim());
        waterpoint.setOwnership(ownershipTfld.getValue().trim());
        waterpoint.setTypeOfMagt(typeOfMagtTfld.getValue().trim());
        ((EditWaterPointController) EditWaterPointView.this.getController()).saveWaterPoint(waterPoint);
    }

    private void createButtons() {
        saveChangesBtn = new Button("Save changes");
        saveChangesBtn.addListener(Events.Select, new Listener<ButtonEvent>() {

            @Override
            public void handleEvent(ButtonEvent be) {
                save(waterPoint);
            }
        });

        confirmBtn = new Button("Confirm");
        confirmBtn.addListener(Events.Select, new Listener<ButtonEvent>() {

            @Override
            public void handleEvent(ButtonEvent be) {
                save(waterPoint);
            }
        });

        cancelBtn = new Button("Cancel");
        cancelBtn.addListener(Events.Select, new Listener<ButtonEvent>() {

            @Override
            public void handleEvent(ButtonEvent be) {
                closeWindow();
            }
        });
    }

    private void initializeTextfields() {
        idTextFld = new TextField<String>();
        idTextFld.setFieldLabel("ID");
        idTextFld.setName("id");
        idTextFld.setAllowBlank(false);
        idTextFld.setEnabled(false);

        waterPointNameTfld = new TextField<String>();
        waterPointNameTfld.setFieldLabel("Waterpoint Name");
        waterPointNameTfld.setName("waterpointname");
        waterPointNameTfld.setAllowBlank(false);

        districtTfld = new TextField<String>();
        districtTfld.setFieldLabel("District");
        districtTfld.setName("district");
        districtTfld.setAllowBlank(false);

        subcountyTfld = new TextField<String>();
        subcountyTfld.setFieldLabel("Subcounty");
        subcountyTfld.setName("subcounty");
        subcountyTfld.setAllowBlank(false);

        villageTfld = new TextField<String>();
        villageTfld.setFieldLabel("Village");
        villageTfld.setName("village");
        villageTfld.setAllowBlank(false);

        eastingsTfld = new TextField<String>();
        eastingsTfld.setFieldLabel("Eastings");
        eastingsTfld.setName("eastings");
        eastingsTfld.setAllowBlank(false);

        northingsTfld = new TextField<String>();
        northingsTfld.setFieldLabel("Northings");
        northingsTfld.setName("northings");
        northingsTfld.setAllowBlank(false);

        otherNameTfld = new TextField<String>();
        otherNameTfld.setFieldLabel("Name");
        otherNameTfld.setName("name");
        otherNameTfld.setAllowBlank(false);
        //
        dateIstalledTfld = new TextField<String>();
        dateIstalledTfld.setFieldLabel("Date Installed");
        dateIstalledTfld.setName("installdate");
        dateIstalledTfld.setAllowBlank(false);

        fundingSrcTfld = new TextField<String>();
        fundingSrcTfld.setFieldLabel("Funding Source");
        fundingSrcTfld.setName("fundingsrc");
        fundingSrcTfld.setAllowBlank(false);

        ownershipTfld = new TextField<String>();
        ownershipTfld.setFieldLabel("Ownership");
        ownershipTfld.setName("ownership");
        ownershipTfld.setAllowBlank(false);

        houseHoldsTfld = new TextField<String>();
        houseHoldsTfld.setFieldLabel("Number Of Households");
        houseHoldsTfld.setName("households");
        houseHoldsTfld.setAllowBlank(false);

        typeOfMagtTfld = new TextField<String>();
        typeOfMagtTfld.setFieldLabel("Type of management");
        typeOfMagtTfld.setName("typeofmagt");
        typeOfMagtTfld.setAllowBlank(false);
    }

    public void showWindow() {
        window.show();
    }

    public void closeWindow() {
        window.hide();
    }

    public void setWaterPointData(Waterpoint waterPoint) {
        this.waterPoint = waterPoint;
        idTextFld.setValue(waterPoint.getWaterpointId());
        waterPointNameTfld.setValue(waterPoint.getName());
        districtTfld.setValue(waterPoint.getVillage().getParish().getSubcounty().getCounty().getDistrict().getName());
        subcountyTfld.setValue(waterPoint.getVillage().getParish().getSubcounty().getSubcountyName());
//        villageTfld.setValue(summary.getVillage().getVillagename());
        villageTfld.setValue(waterPoint.getVillage().getVillagename());
        eastingsTfld.setValue(waterPoint.getEastings());
        northingsTfld.setValue(waterPoint.getNorthings());
        dateIstalledTfld.setValue(waterPoint.getDateInstalled().toString());
        fundingSrcTfld.setValue(waterPoint.getFundingSource());
        ownershipTfld.setValue(waterPoint.getOwnership());
        houseHoldsTfld.setValue(waterPoint.getHouseholds());
        typeOfMagtTfld.setValue(waterPoint.getTypeOfMagt());
    }

    @Override
    protected void handleEvent(AppEvent event) {
        GWT.log("Edit waterpoint : handleEvent");
        if (event.getType() == EditWaterPointController.EDIT_WATER_POINT) {
            String waterPointId = event.getData();
            ((EditWaterPointController) EditWaterPointView.this.getController()).getWaterPoint(waterPointId);
            showWindow();
        }
    }
}
