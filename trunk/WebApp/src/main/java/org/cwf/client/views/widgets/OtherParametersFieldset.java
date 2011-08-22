package org.cwf.client.views.widgets;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.Label;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.FieldSet;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.google.gwt.user.client.ui.FlexTable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author victor
 */
public class OtherParametersFieldset extends FieldSet {

    private FlexTable table = null;
    private List<ParameterWidget> fieldsetRowsList;
    private Button addParameterBtn;

    public OtherParametersFieldset() {
        initialize();
    }

    private void initialize() {
        setHeading("Aditional Parameters");
        setCheckboxToggle(false);
        FormLayout otherlayout = new FormLayout();
        otherlayout.setLabelWidth(150);
        setLayout(otherlayout);
        setExpanded(false);
        table = new FlexTable();
        table.setWidget(0, 0, new Label("Name"));
        table.setWidget(0, 1, new Label(""));
        table.getCellFormatter().setWidth(0, 1, "50px");
        table.setWidget(0, 2, new Label("Value"));
        fieldsetRowsList = new ArrayList<ParameterWidget>();
        ParameterWidget row = new ParameterWidget();
        fieldsetRowsList.add(row);
        addRow(table, row);
        add(table);

        add(buttonsPanel(table));
    }

    private void addRow(final FlexTable table, ParameterWidget row) {
        int lastRow = table.getRowCount();
        table.setWidget(lastRow, 0, row.getNameTfld());
        table.setWidget(lastRow, 1, new Label(""));
        table.setWidget(lastRow, 2, row.getValTfld());
    }

    private void removeRow(FlexTable table, int row) {
        table.removeRow(row);
    }

    private HorizontalPanel buttonsPanel(final FlexTable fTable) {
        HorizontalPanel panel = new HorizontalPanel();
        addParameterBtn = new Button("Add Parameter");
        addParameterBtn.addListener(Events.Select, new Listener<ButtonEvent>() {

            @Override
            public void handleEvent(ButtonEvent be) {
                ParameterWidget row = new ParameterWidget();
                fieldsetRowsList.add(row);
                addRow(fTable, row);
            }
        });
        panel.add(addParameterBtn);
        Button removeBtn = new Button("Remove Last column");
        removeBtn.addListener(Events.Select, new Listener<ButtonEvent>() {

            @Override
            public void handleEvent(ButtonEvent be) {
                int row = fTable.getRowCount();
                if (row > 1) {
                    removeRow(fTable, row - 1);
//                remember row 1 or header
                    fieldsetRowsList.remove(row - 2);
                }
            }
        });
        panel.add(removeBtn);
        return panel;
    }
}
