package org.cwf.client.views;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.View;
import com.extjs.gxt.ui.client.widget.Label;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.FieldSet;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.FlowLayout;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.cwf.client.AppMessages;
import org.cwf.client.controllers.EditNewWaterPointController;
import org.cwf.client.model.NewWaterpointSummary;
import org.m4water.server.admin.model.Setting;
import org.m4water.server.admin.model.SettingGroup;
import org.m4water.server.admin.model.Waterpoint;
/**
 *
 * @author victor
 */
public class EditNewWaterPointsView extends View {

    final AppMessages appMessages = GWT.create(AppMessages.class);
    private Window window;
    private TextField<String> idTextFld, districtTfld, subcountyTfld, villageTfld, otherNameTfld, eastingsTfld, northingsTfld;
    private TextField<String> waterPointNameTfld, dateIstalledTfld, fundingSrcTfld, ownershipTfld, houseHoldsTfld, typeOfMagtTfld;
    private Button saveChangesBtn, confirmBtn, cancelBtn;
    private FormPanel formPanel;
    private Waterpoint waterPoint;
    private SettingGroup settingGroup;
    private FlexTable ticketsDataTable;
    private FieldSet inspectionFldset;
    private List<TextField<String>> questionTflds;
    private FormData formData;
    private HashMap<String, TextField<String>> waterUserCommitteeFields;
    private HashMap<String, TextField<String>> waterFunctionalityFields;
    private FieldSet detailsFldSet;

    public EditNewWaterPointsView (Controller controller) {
        super(controller);
    }

    @Override
    protected void initialize() {
        window = new Window();
        window.setHeading("Waterpoint Details");
        window.setHeight("500px");
        window.setWidth("425px");
        window.setPlain(true);
        window.setDraggable(true);
        window.setResizable(true);
        window.setScrollMode(Scroll.AUTO);
        window.setModal(true);
        formData = new FormData("-20");
        getWindowLayout();
    }

    public void getWindowLayout() {
        formPanel = new FormPanel();
        formPanel.setFrame(true);
        formPanel.setHeading("");
        formPanel.setWidth("98%");
        formPanel.setLayout(new FlowLayout());
        ticketsDataTable = new FlexTable();
        ticketsDataTable.setWidth("98%");
        detailsFldSet =  getWaterPointDetailsFldset();
        ticketsDataTable.setWidget(0, 0,detailsFldSet);
        ticketsDataTable.getFlexCellFormatter().setColSpan(0, 1, 2);
        ticketsDataTable.getFlexCellFormatter().setWidth(0, 0, "40%");
        ticketsDataTable.getFlexCellFormatter().setWidth(0, 1, "50%");
//        ticketsDataTable.getFlexCellFormatter().setHeight(0, 0,details.getHeight()+"px");
        ticketsDataTable.getFlexCellFormatter().setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_TOP);
        ticketsDataTable.getFlexCellFormatter().setVerticalAlignment(0, 1, HasVerticalAlignment.ALIGN_TOP);
        ticketsDataTable.getFlexCellFormatter().setVerticalAlignment(1, 0, HasVerticalAlignment.ALIGN_TOP);
        formPanel.add(ticketsDataTable);
        formPanel.setButtonAlign(HorizontalAlignment.CENTER);
        createButtons();
        formPanel.addButton(saveChangesBtn);
        formPanel.addButton(confirmBtn);
        formPanel.addButton(cancelBtn);
        window.add(formPanel);
        window.setWidth("82%");
    }

    
    public FieldSet getWaterPointDetailsFldset() {
        FieldSet waterPointDetails = new FieldSet();
        waterPointDetails.setHeading("Water point Details");
        waterPointDetails.setCheckboxToggle(false);

        FormLayout layout = new FormLayout();
        layout.setLabelWidth(75);
        waterPointDetails.setLayout(layout);
        initializeTextfields();

        idTextFld = new TextField<String>();
        idTextFld.setFieldLabel("ID");
        idTextFld.setName("id");
        waterPointDetails.add(idTextFld, formData);
        idTextFld.setReadOnly(false);


        waterPointNameTfld = new TextField<String>();
        waterPointNameTfld.setFieldLabel("Waterpoint Name");
        waterPointNameTfld.setName("waterpointname");
        waterPointDetails.add(waterPointNameTfld, formData);
        waterPointNameTfld.setReadOnly(false);


        districtTfld = new TextField<String>();
        districtTfld.setFieldLabel("District");
        districtTfld.setName("district");
        waterPointDetails.add(districtTfld, formData);
        districtTfld.setReadOnly(false);


        subcountyTfld = new TextField<String>();
        subcountyTfld.setFieldLabel("Subcounty");
        subcountyTfld.setName("subcounty");
        waterPointDetails.add(subcountyTfld, formData);
        subcountyTfld.setReadOnly(false);


        villageTfld = new TextField<String>();
        villageTfld.setFieldLabel("Village");
        villageTfld.setName("village");
        waterPointDetails.add(villageTfld, formData);
        villageTfld.setReadOnly(false);


        eastingsTfld = new TextField<String>();
        eastingsTfld.setFieldLabel("Eastings");
        eastingsTfld.setName("eastings");
        waterPointDetails.add(eastingsTfld, formData);
        eastingsTfld.setReadOnly(false);

        northingsTfld = new TextField<String>();
        northingsTfld.setFieldLabel("Northings");
        northingsTfld.setName("northings");
        waterPointDetails.add(northingsTfld, formData);
        northingsTfld.setReadOnly(false);

        dateIstalledTfld = new TextField<String>();
        dateIstalledTfld.setFieldLabel("Date Installed");
        dateIstalledTfld.setName("installdate");
        waterPointDetails.add(dateIstalledTfld, formData);
        dateIstalledTfld.setReadOnly(false);

        fundingSrcTfld = new TextField<String>();
        fundingSrcTfld.setFieldLabel("Funding Source");
        fundingSrcTfld.setName("fundingsrc");
        waterPointDetails.add(fundingSrcTfld, formData);
        fundingSrcTfld.setReadOnly(false);

        ownershipTfld = new TextField<String>();
        ownershipTfld.setFieldLabel("Ownership");
        ownershipTfld.setName("ownership");
        waterPointDetails.add(ownershipTfld, formData);
        ownershipTfld.setReadOnly(false);

        houseHoldsTfld = new TextField<String>();
        houseHoldsTfld.setFieldLabel("Number Of Households");
        houseHoldsTfld.setName("households");
        waterPointDetails.add(houseHoldsTfld, formData);
        houseHoldsTfld.setReadOnly(false);

        typeOfMagtTfld = new TextField<String>();
        typeOfMagtTfld.setFieldLabel("Type of management");
        typeOfMagtTfld.setName("typeofmagt");
        waterPointDetails.add(typeOfMagtTfld, formData);
        typeOfMagtTfld.setReadOnly(false);

        return waterPointDetails;
    }

    public void setInspectionQuestion(List<Setting> settings) {
        inspectionFldset = new FieldSet();
        inspectionFldset.setHeading(appMessages.waterpointInspection());
        inspectionFldset.setCheckboxToggle(false);

        FormLayout layout = new FormLayout();
        layout.setLabelWidth(150);
        inspectionFldset.setLayout(layout);

        questionTflds = new ArrayList<TextField<String>>();
        if (!settings.isEmpty()) {
            for (Setting setting : settings) {
                TextField<String> questionFld = new TextField<String>();
                questionFld.setFieldLabel(setting.getName());
                questionFld.setValue(setting.getValue());
                inspectionFldset.add(questionFld, formData);
                questionFld.setReadOnly(false);
//                addInspectionQuestion(((InspectionQuestions) object).getQuestion());
            }
        }
        if (settings.isEmpty()) {
            Label lable = new Label("There is no Inspection done for this waterpoint");
            inspectionFldset.add(lable);
        }
        inspectionFldset.setWidth("98%");
        ticketsDataTable.setWidget(0,1, inspectionFldset);
        ticketsDataTable.getFlexCellFormatter().setRowSpan(0, 1, 2);
        ticketsDataTable.getFlexCellFormatter().setWidth(0, 1, "50%");
        ticketsDataTable.getFlexCellFormatter().setVerticalAlignment(0, 1, HasVerticalAlignment.ALIGN_TOP);
    }

    public FieldSet getWaterUsercommitteeFldset(List<Setting> settings) {
        FieldSet userCommittee = new FieldSet();
        userCommittee.setHeading(appMessages.waterUserCommittee());
        userCommittee.setCheckboxToggle(false);

        FormLayout layout = new FormLayout();
        layout.setLabelWidth(100);
        userCommittee.setLayout(layout);
        initializeTextfields();
        waterUserCommitteeFields = new HashMap<String, TextField<String>>();
        for (Setting x : settings) {
            userCommittee.add(addUserCommitteeFld(x.getName(), x.getValue()), formData);
        }
        if (settings.isEmpty()) {
            Label lable = new Label("There is no Water User Committee for this waterpoint");
            userCommittee.add(lable);
        }
//        ticketsDataTable.getFlexCellFormatter().setHeight(0, 0, detailsFldSet.getHeight()+"px");
        ticketsDataTable.setWidget(1,0,userCommittee);
        ticketsDataTable.getFlexCellFormatter().setVerticalAlignment(1, 0, HasVerticalAlignment.ALIGN_TOP);
        return userCommittee;
    }

    public FieldSet getWaterFunctionalityFldset(List<Setting> settings){
        FieldSet functionality = new FieldSet();
        functionality.setHeading(appMessages.waterFunctionality());
        functionality.setCheckboxToggle(false);

        FormLayout layout = new FormLayout();
        layout.setLabelWidth(150);
        functionality.setLayout(layout);
        initializeTextfields();
        waterFunctionalityFields = new HashMap<String, TextField<String>>();
        for (Setting x : settings) {
            functionality.add(addFunctionalityFld(x.getName(), x.getValue()), formData);
        }
        if (settings.isEmpty()) {
            Label lable = new Label("There is no Water Functionality for this waterpoint");
            functionality.add(lable);
        }
//        ticketsDataTable.getFlexCellFormatter().setHeight(0, 0, detailsFldSet.getHeight() + "px");
        ticketsDataTable.setWidget(2, 0, functionality);
        ticketsDataTable.getFlexCellFormatter().setVerticalAlignment(2, 0, HasVerticalAlignment.ALIGN_TOP);
        return functionality;
    }
    private void setSettingWUCorFunc(SettingGroup group,HashMap<String, TextField<String>> fields){
	List<Setting> settings = group.getSettings();
	for (Setting setting : settings) {
	    setting.setValue(fields.get(setting.getName()).getValue());
	}
    }
    private TextField<String> addUserCommitteeFld(String name, String value) {
        TextField<String> committeeQuiz = new TextField<String>();
        committeeQuiz.setFieldLabel(name);
        committeeQuiz.setValue(value);
        waterUserCommitteeFields.put(name, committeeQuiz);
        committeeQuiz.setReadOnly(false);
        return committeeQuiz;

    }

    private TextField<String> addFunctionalityFld(String name, String value) {
        TextField<String> functionalityQuiz = new TextField<String>();
        functionalityQuiz.setFieldLabel(name);
        functionalityQuiz.setValue(value);
        waterFunctionalityFields.put(name, functionalityQuiz);
        functionalityQuiz.setReadOnly(false);
        return functionalityQuiz;

    }    
    public void saveWaterPoint(SettingGroup group) {
        ((EditNewWaterPointController)EditNewWaterPointsView .this.
		getController()).exportSettingGroupToWaterPoint(group);
    }
    public void saveSettingGroup(SettingGroup group){
        ((EditNewWaterPointController)EditNewWaterPointsView .this.getController()).saveSettingGroup(group);
    }
    private void createButtons() {
        saveChangesBtn = new Button("Save changes");
        saveChangesBtn.addListener(Events.Select, new Listener<ButtonEvent>() {

            @Override
            public void handleEvent(ButtonEvent be) {
		saveSettingGroup(getFieldValues(settingGroup));
            }
        });

        confirmBtn = new Button("Confirm");
        confirmBtn.addListener(Events.Select, new Listener<ButtonEvent>() {

            @Override
            public void handleEvent(ButtonEvent be) {
                saveWaterPoint(getFieldValues(settingGroup));
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

    public void setWaterPointData(NewWaterpointSummary model) {
        SettingGroup group = model.getSettingGroup();
        idTextFld.setValue(model.getId());
        waterPointNameTfld.setValue(model.getWaterpointName());
        districtTfld.setValue(model.getDistrict());
        subcountyTfld.setValue(model.getSubCounty());
        villageTfld.setValue(model.getVillage());
        eastingsTfld.setValue(model.getEastings());
        northingsTfld.setValue(model.getNorthings());
        dateIstalledTfld.setValue(model.getDate().toString());
        fundingSrcTfld.setValue(model.getFundingSrc());
        ownershipTfld.setValue(model.getOwnership());
        houseHoldsTfld.setValue(model.getHouseHolds());
        typeOfMagtTfld.setValue(model.getTypeOfMagt());
        List<SettingGroup> groups = group.getGroups();
        for(SettingGroup x:groups){
            if(x.getName().equalsIgnoreCase("functionality")){
                getWaterFunctionalityFldset(x.getSettings());
            }else if(x.getName().equalsIgnoreCase("waterusercommittee")){
                getWaterUsercommitteeFldset(x.getSettings());
            }else if(x.getName().equalsIgnoreCase("inspection")){
                setInspectionQuestion(x.getSettings());
            }
            setInspectionQuestion(new ArrayList<Setting>());
        }
    }
    private SettingGroup getFieldValues(SettingGroup group){
	group.setName(idTextFld.getValue());
		List<Setting> settings = group.getSettings();
		for (Setting setting : settings) {
		    if (setting.getName().equalsIgnoreCase("id")) {
			setting.setValue(waterPointNameTfld.getValue());
		    } else if (setting.getName().equalsIgnoreCase("village")) {
			setting.setValue(villageTfld.getValue());
		    } else if (setting.getName().equalsIgnoreCase("parish")) {
//			setting.setValue(setting.getValue());
		    } else if (setting.getName().equalsIgnoreCase("subcounty")) {
			setting.setValue(subcountyTfld.getValue());
		    } else if (setting.getName().equalsIgnoreCase("county")) {
//			setting.setValue(setting.getValue());
		    } else if (setting.getName().equalsIgnoreCase("district")) {
			setting.setValue(districtTfld.getValue());
		    }  else if (setting.getName().equalsIgnoreCase("waterpointType")) {
//			setting.setValue(setting.getValue());
		    } else if (setting.getName().equalsIgnoreCase("waterpointname")) {
			setting.setValue(waterPointNameTfld.getValue());
		    } else if (setting.getName().equalsIgnoreCase("dateInstalled")) {
			setting.setValue(dateIstalledTfld.getValue());
		    } else if (setting.getName().equalsIgnoreCase("fundingSource")) {
			setting.setValue(fundingSrcTfld.getValue());
		    } else if (setting.getName().equalsIgnoreCase("ownership")) {
			setting.setValue(ownershipTfld.getValue());
		    } else if (setting.getName().equalsIgnoreCase("nohousehold")) {
			setting.setValue(houseHoldsTfld.getValue());
		    } else if (setting.getName().equalsIgnoreCase("typeOfManagement")) {
			setting.setValue(typeOfMagtTfld.getValue());
		    } else if (setting.getName().equalsIgnoreCase("eastings")) {
			setting.setValue(eastingsTfld.getValue());
		    } else if (setting.getName().equalsIgnoreCase("norhtings")) {
			setting.setValue(northingsTfld.getValue());
		    }
		}
		//FIX ME saveWaterPoint wuc data and functionality data
		List<SettingGroup> groups = group.getGroups();
		for (SettingGroup x : groups) {
		    if (x.getName().equalsIgnoreCase("functionality")) {
			setSettingWUCorFunc(x, waterFunctionalityFields);
		    } else if (x.getName().equalsIgnoreCase("waterusercommittee")) {
			setSettingWUCorFunc(x, waterUserCommitteeFields);
		    }
		}
		return group;
    }
    @Override
    protected void handleEvent(AppEvent event) {
        GWT.log("Edit waterpoint : handleEvent");
        if (event.getType() == EditNewWaterPointController.EDIT_NEW_WATER_POINT) {
            NewWaterpointSummary model = event.getData();
            setWaterPointData(model);
	    settingGroup = model.getSettingGroup();
            showWindow();
        }
    }
}
