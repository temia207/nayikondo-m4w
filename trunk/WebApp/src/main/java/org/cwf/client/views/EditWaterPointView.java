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
import org.cwf.client.model.WaterPointSummary;

/**
 *
 * @author victor
 */
public class EditWaterPointView extends View {

    final AppMessages appMessages = GWT.create(AppMessages.class);
    private Window window;
    private TextField<String> idTextFld, districtTfld, subcountyTfld, villageTfld, otherNameTfld,eastingsTfld,northingsTfld;
    private Button saveChangesBtn, confirmBtn, cancelBtn;
    private FormPanel formPanel;
    private final OtherParametersFieldset otherParameters = new OtherParametersFieldset();
//    private WaterPointSummary waterPointSummary;

    public EditWaterPointView(Controller controller) {
        super(controller);
    }

    @Override
    protected void initialize() {
        window = new Window();
        window.setHeading("Edit");

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
        formPanel.add(districtTfld);
        formPanel.add(subcountyTfld);
        formPanel.add(villageTfld);
        formPanel.add(eastingsTfld);
        formPanel.add(northingsTfld);
        formPanel.setButtonAlign(HorizontalAlignment.CENTER);
        formPanel.add(otherParameters);

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

    private void createButtons() {
        saveChangesBtn = new Button("Save changes");
        saveChangesBtn.addListener(Events.Select, new Listener<ButtonEvent>() {

            @Override
            public void handleEvent(ButtonEvent be) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        });

        confirmBtn = new Button("Confirm");
        confirmBtn.addListener(Events.Select, new Listener<ButtonEvent>() {

            @Override
            public void handleEvent(ButtonEvent be) {
                throw new UnsupportedOperationException("Not supported yet.");
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
    }

    public void showWindow() {
        window.show();
    }

    public void closeWindow() {
        window.hide();
    }

    private void setWaterPointData(WaterPointSummary summary) {
        idTextFld.setValue(summary.getId());
        districtTfld.setValue(summary.getDistrict().getName());
        subcountyTfld.setValue(summary.getSubCounty().getSubcountyName());
        villageTfld.setValue(summary.getVillage().getVillagename());
        eastingsTfld.setValue(summary.getEastings());
        northingsTfld.setValue(summary.getNorthings());
    }

    @Override
    protected void handleEvent(AppEvent event) {
        GWT.log("Edit waterpoint : handleEvent");
        if (event.getType() == EditWaterPointController.EDIT_WATER_POINT) {
            WaterPointSummary waterPointSummary = event.getData();
            setWaterPointData(waterPointSummary);
            showWindow();
        }
    }
}
