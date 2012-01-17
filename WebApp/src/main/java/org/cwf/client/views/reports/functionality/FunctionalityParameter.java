package org.cwf.client.views.reports.functionality;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.data.BaseModel;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.View;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.extjs.gxt.ui.client.widget.form.FieldSet;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.Radio;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.google.gwt.user.client.ui.FlexTable;
import java.util.ArrayList;
import java.util.List;
import org.cwf.client.controllers.FunctionalityParamController;
import org.cwf.client.controllers.ReportsController;
import org.cwf.client.model.District;

/**
 *
 * @author victor
 */
public class FunctionalityParameter extends View {

	private Window window;
	private FormPanel cp;
	private ComboBox<District> districtCombo;
	private ListStore<District> districtStore;
	private ComboBox<Year> yearCombo;
	private ListStore<Year> yearStore;
	private Button loadButton;

	public FunctionalityParameter(Controller controller) {
		super(controller);
	}

	@Override
	protected void initialize() {
		window = new Window();
		window.setHeading("Select Parameters");
		window.setHeight("500px");
		window.setSize(450, 250);
		window.setPlain(true);
		window.setDraggable(true);
		window.setResizable(true);
		window.setScrollMode(Scroll.AUTO);
		window.setModal(true);
		cp = new FormPanel();
		FormLayout layout = new FormLayout();
		layout.setLabelWidth(40);
		cp.setLayout(layout);
		FlexTable table = new FlexTable();
		table.setWidth("98%");
		yearCombo = new ComboBox<Year>();
		yearCombo.setFieldLabel("Year");
		yearCombo.setDisplayField("year");
		yearCombo.setAllowBlank(false);
		yearCombo.setTriggerAction(TriggerAction.ALL);
		yearStore = new ListStore<Year>();
		yearStore.add(getYears());
		yearCombo.setStore(yearStore);
		districtCombo = new ComboBox<District>();
		districtCombo.setFieldLabel("District");
		districtCombo.setDisplayField("name");
		districtCombo.setAllowBlank(false);
		districtCombo.setTriggerAction(TriggerAction.ALL);
		districtStore = new ListStore<District>();
		districtStore.add(getDistricts());
		districtCombo.setStore(districtStore);
		table.setWidget(0, 0, yearCombo);
		table.setWidget(0, 1, districtCombo);
		table.setCellPadding(1);
		window.setLayout(new FitLayout());
		cp.add(table);
		cp.add(getSourceTypeFldset());
		window.add(cp);
		loadButton = new Button("Load");
		loadButton.addSelectionListener(new SelectionListener<ButtonEvent>() {

			@Override
			public void componentSelected(ButtonEvent ce) {
				((FunctionalityParamController) FunctionalityParameter.this.getController()).
						getResponseTime(yearCombo.getValue().getYear(),districtCombo.getValue().getName());
			}
		});
		window.addButton(loadButton);
		window.setButtonAlign(HorizontalAlignment.CENTER);
	}

	private FieldSet getSourceTypeFldset(){
		FieldSet sourceTypeFldset = new FieldSet();
		sourceTypeFldset.setHeading("Select Source Type");
		sourceTypeFldset.setCheckboxToggle(false);

		FormLayout layout = new FormLayout();
		layout.setLabelWidth(200);
		sourceTypeFldset.setLayout(layout);
		Radio radio = new Radio();
		radio.setName("source");
		radio.setBoxLabel("DeepBoreHole");
		radio.setHideLabel(true);
		sourceTypeFldset.add(radio);
		Radio radio2 = new Radio();
		radio2.setName("source");
		radio2.setBoxLabel("Shallow wells");
		radio2.setHideLabel(true);
		sourceTypeFldset.add(radio2);
		Radio radio3 = new Radio();
		radio3.setName("source");
		radio3.setBoxLabel("Protected Springs");
		radio3.setHideLabel(true);
		sourceTypeFldset.add(radio3);
		Radio radio4 = new Radio();
		radio4.setName("source");
		radio4.setBoxLabel("Taps");
		radio4.setHideLabel(true);
		sourceTypeFldset.add(radio4);
		return sourceTypeFldset;
	}
	public void closeWindow() {
		window.hide();
	}

	public void showWindow() {
		window.show();
	}

	@Override
	protected void handleEvent(AppEvent event) {
		showWindow();
	}

	private class Year extends BaseModel {

		public Year(String year) {
			setYear(year);
		}

		public void setYear(String year) {
			set("year", year);
		}

		public String getYear() {
			return get("year");
		}
	}

	private List<Year> getYears() {
		List<Year> summary = new ArrayList<Year>();
			summary.add(new Year("Year"));
		for (int i = 2000; i < 2051; ++i) {
			summary.add(new Year(i + ""));
		}
		return summary;
	}

	private List<District> getDistricts() {
		List<District> districts = new ArrayList<District>();
		districts.add(new District("District"));
		districts.add(new District("Kasese"));
		return districts;
	}
}
