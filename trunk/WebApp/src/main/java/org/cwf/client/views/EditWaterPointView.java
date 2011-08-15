package org.cwf.client.views;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.View;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.google.gwt.core.client.GWT;
import org.cwf.client.AppMessages;
import org.cwf.client.controllers.EditWaterPointController;

/**
 *
 * @author victor
 */
public class EditWaterPointView extends View {

    final AppMessages appMessages = GWT.create(AppMessages.class);
    private Window window;
    private TextField<String> idTextFld, districtTfld, subcountyTfld, villageTfld;
    private Button saveChangesBtn, confirmBtn, cancelBtn;

    public EditWaterPointView(Controller controller) {
        super(controller);
    }

    @Override
    protected void initialize() {
        window = new Window();
        window.setHeading("Edit");

        initializeTextfields();

        window.add(idTextFld);
        window.add(districtTfld);
        window.add(subcountyTfld);
        window.add(villageTfld);

        window.addButton(saveChangesBtn);
        window.addButton(confirmBtn);
        window.addButton(cancelBtn);

        createButtons();
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
                throw new UnsupportedOperationException("Not supported yet.");
            }
        });
    }

    private void initializeTextfields() {

        idTextFld = new TextField<String>();
        idTextFld.setFieldLabel("ID:");
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
    }

    public void showWindow() {
        window.show();
    }

    @Override
    protected void handleEvent(AppEvent event) {
        GWT.log("Edit waterpoint : handleEvent");
        if (event.getType() == EditWaterPointController.EDIT_WATER_POINT) {
            showWindow();
        }
    }
}
