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
import java.util.Set;
import org.cwf.client.AppMessages;
import org.cwf.client.controllers.EditWaterPointController;
import org.cwf.client.model.WaterPointModel;
import org.m4water.server.admin.model.Inspection;
import org.m4water.server.admin.model.InspectionQuestions;
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
    private FlexTable ticketsDataTable;
    private FieldSet inspectionFldset;
    private List<TextField<String>> questionTflds;
    private FormData formData;
    private HashMap<String, TextField<String>> waterUserCommitteeFields;
//    private WaterPointModel waterPointSummary;

    public EditWaterPointView(Controller controller) {
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
        ticketsDataTable.setWidget(0, 0, getWaterPointDetailsFldset());
        ticketsDataTable.setWidget(1, 0, getWaterUsercommitteeFldset());
        ticketsDataTable.getFlexCellFormatter().setRowSpan(0, 1, 2);
        ticketsDataTable.getFlexCellFormatter().setWidth(0, 0, "40%");
        ticketsDataTable.getFlexCellFormatter().setWidth(0, 1, "50%");
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
        idTextFld.setAllowBlank(false);
        waterPointDetails.add(idTextFld, formData);
        idTextFld.setReadOnly(true);


        waterPointNameTfld = new TextField<String>();
        waterPointNameTfld.setFieldLabel("Waterpoint Name");
        waterPointNameTfld.setName("waterpointname");
        waterPointDetails.add(waterPointNameTfld, formData);
        waterPointNameTfld.setAllowBlank(false);
        waterPointNameTfld.setReadOnly(true);


        districtTfld = new TextField<String>();
        districtTfld.setFieldLabel("District");
        districtTfld.setName("district");
        waterPointDetails.add(districtTfld, formData);
        districtTfld.setAllowBlank(false);
        districtTfld.setReadOnly(true);


        subcountyTfld = new TextField<String>();
        subcountyTfld.setFieldLabel("Subcounty");
        subcountyTfld.setName("subcounty");
        waterPointDetails.add(subcountyTfld, formData);
        subcountyTfld.setAllowBlank(false);
        subcountyTfld.setReadOnly(true);


        villageTfld = new TextField<String>();
        villageTfld.setFieldLabel("Village");
        villageTfld.setName("village");
        waterPointDetails.add(villageTfld, formData);
        villageTfld.setAllowBlank(false);
        villageTfld.setReadOnly(true);


        eastingsTfld = new TextField<String>();
        eastingsTfld.setFieldLabel("Eastings");
        eastingsTfld.setName("eastings");
        waterPointDetails.add(eastingsTfld, formData);
        eastingsTfld.setAllowBlank(false);
        eastingsTfld.setReadOnly(true);

        northingsTfld = new TextField<String>();
        northingsTfld.setFieldLabel("Northings");
        northingsTfld.setName("northings");
        waterPointDetails.add(northingsTfld, formData);
        northingsTfld.setAllowBlank(false);
        northingsTfld.setReadOnly(true);

        dateIstalledTfld = new TextField<String>();
        dateIstalledTfld.setFieldLabel("Date Installed");
        dateIstalledTfld.setName("installdate");
        waterPointDetails.add(dateIstalledTfld, formData);
        dateIstalledTfld.setAllowBlank(false);
        dateIstalledTfld.setReadOnly(true);

        fundingSrcTfld = new TextField<String>();
        fundingSrcTfld.setFieldLabel("Funding Source");
        fundingSrcTfld.setName("fundingsrc");
        waterPointDetails.add(fundingSrcTfld, formData);
        fundingSrcTfld.setAllowBlank(false);
        fundingSrcTfld.setReadOnly(true);

        ownershipTfld = new TextField<String>();
        ownershipTfld.setFieldLabel("Ownership");
        ownershipTfld.setName("ownership");
        waterPointDetails.add(ownershipTfld, formData);
        ownershipTfld.setAllowBlank(false);
        ownershipTfld.setReadOnly(true);

        houseHoldsTfld = new TextField<String>();
        houseHoldsTfld.setFieldLabel("Number Of Households");
        houseHoldsTfld.setName("households");
        waterPointDetails.add(houseHoldsTfld, formData);
        houseHoldsTfld.setAllowBlank(false);
        houseHoldsTfld.setReadOnly(true);

        typeOfMagtTfld = new TextField<String>();
        typeOfMagtTfld.setFieldLabel("Type of management");
        typeOfMagtTfld.setName("typeofmagt");
        waterPointDetails.add(typeOfMagtTfld, formData);
        typeOfMagtTfld.setAllowBlank(false);
        typeOfMagtTfld.setReadOnly(true);

        return waterPointDetails;
    }

    public void setInspectionQuestion(List<Inspection> questions) {
        inspectionFldset = new FieldSet();
        inspectionFldset.setHeading(appMessages.waterpointInspection());
        inspectionFldset.setCheckboxToggle(false);

        FormLayout layout = new FormLayout();
        layout.setLabelWidth(500);
        inspectionFldset.setLayout(layout);

        questionTflds = new ArrayList<TextField<String>>();

        for (Inspection x : questions) {
            Set quiz = x.getInspectionQuestionses();
            for (Object object : quiz) {
                TextField<String> questionFld = new TextField<String>();
                questionFld.setFieldLabel(((InspectionQuestions) object).getQuestion());
                questionFld.setAllowBlank(false);
                questionFld.setValue(((InspectionQuestions) object).getAnswer());
                inspectionFldset.add(questionFld, formData);
                questionFld.setReadOnly(true);
//                addInspectionQuestion(((InspectionQuestions) object).getQuestion());
            }
        }
        if (questions.isEmpty()) {
            Label lable = new Label("There is no Inspection done for this waterpoint");
            inspectionFldset.add(lable);
        }
        ticketsDataTable.setWidget(0, 1, inspectionFldset);
        ticketsDataTable.getFlexCellFormatter().setRowSpan(0, 1, 2);
        ticketsDataTable.getFlexCellFormatter().setWidth(0, 1, "50%");
        ticketsDataTable.getFlexCellFormatter().setVerticalAlignment(0, 1, HasVerticalAlignment.ALIGN_TOP);
    }

    public FieldSet getWaterUsercommitteeFldset() {
        FieldSet userCommittee = new FieldSet();
        userCommittee.setHeading(appMessages.waterUserCommittee());
        userCommittee.setCheckboxToggle(false);

        FormLayout layout = new FormLayout();
        layout.setLabelWidth(100);
        userCommittee.setLayout(layout);
        initializeTextfields();
        waterUserCommitteeFields = new HashMap<String, TextField<String>>();
        userCommittee.add(addUserCommitteeFld("Id", ""), formData);
        userCommittee.add(addUserCommitteeFld("Waterpoint Id", ""), formData);
        userCommittee.add(addUserCommitteeFld("Commissioned", ""), formData);
        userCommittee.add(addUserCommitteeFld("Year Established", ""), formData);
        userCommittee.add(addUserCommitteeFld("Trained", ""), formData);
        userCommittee.add(addUserCommitteeFld("Collect Fees", ""), formData);
        userCommittee.add(addUserCommitteeFld("Regular Service", ""), formData);


        return userCommittee;
    }

    private TextField<String> addUserCommitteeFld(String name, String value) {
        TextField<String> committeeQuiz = new TextField<String>();
        committeeQuiz.setFieldLabel(name);
        committeeQuiz.setValue(value);
        waterUserCommitteeFields.put(name, committeeQuiz);
        committeeQuiz.setReadOnly(true);
        return committeeQuiz;

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
//                save(waterPoint);
                idTextFld.setValue("kkkkkkkkkk");
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

    public void setWaterPointData(Waterpoint waterPoint) {
        this.waterPoint = waterPoint;
        idTextFld.setValue(waterPoint.getWaterpointId());
        waterPointNameTfld.setValue(waterPoint.getName());
        districtTfld.setValue(waterPoint.getVillage().getParish().getSubcounty().getCounty().getDistrict().getName());
        subcountyTfld.setValue(waterPoint.getVillage().getParish().getSubcounty().getSubcountyName());
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
            EditWaterPointController controller2 = (EditWaterPointController) EditWaterPointView.this.getController();
            controller2.getWaterPoint(waterPointId);
            controller2.getInspections();
            showWindow();
        }
    }
}
