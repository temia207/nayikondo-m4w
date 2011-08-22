/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cwf.client.views.widgets;

import com.extjs.gxt.ui.client.widget.form.TextField;
import com.google.gwt.user.client.ui.Widget;

/**
 *
 * @author victor
 */
public class ParameterWidget extends Widget{

    private TextField<String> nameTfld, valueTfld;

    public ParameterWidget() {
        initialize();
    }

    private void initialize() {
        nameTfld = new TextField<String>();
        valueTfld = new TextField<String>();
    }

    public String getNameField() {
        return nameTfld.getValue();
    }

    public String getValueField() {
        return valueTfld.getValue();
    }

    public TextField<String> getNameTfld() {
        return nameTfld;
    }

    public TextField<String> getValTfld() {
        return valueTfld;
    }

    public void setEnabled(boolean enabled) {
        nameTfld.setEnabled(enabled);
        valueTfld.setEnabled(enabled);
    }
    public void setVisible(boolean enabled) {
        nameTfld.setVisible(enabled);
        valueTfld.setVisible(enabled);
    }
}
