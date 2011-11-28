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
import java.util.Set;
import org.cwf.client.AppMessages;
import org.cwf.client.controllers.EditWaterPointController;
import org.m4water.server.admin.model.Inspection;
import org.m4water.server.admin.model.InspectionQuestions;
import org.m4water.server.admin.model.WaterFunctionality;
import org.m4water.server.admin.model.WaterUserCommittee;
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
    private Waterpoint waterPoint;
    private FlexTable ticketsDataTable;
    private FieldSet inspectionFldset;
    private List<TextField<String>> questionTflds;
    private FormData formData;
    private HashMap<String, TextField<String>> waterUserCommitteeFields;
    private HashMap<String, TextField<String>> waterFunctionalityFields;
    private FieldSet detailsFldSet;

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
        detailsFldSet =  getWaterPointDetailsFldset();
        ticketsDataTable.setWidget(0, 0,detailsFldSet);
        ticketsDataTable.getFlexCellFormatter().setColSpan(0, 1, 2);
        ticketsDataTable.getFlexCellFormatter().setWidth(0, 0, "40%");
        ticketsDataTable.getFlexCellFormatter().setWidth(0, 1, "50%");
//        ticketsDataTable.getFlexCellFormatter().setHeight(0, 0,details.getHeight()+"px");
        ticketsDataTable.getFlexCellFormatter().setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_TOP);
        ticketsDataTable.getFlexCellFormatter().setVerticalAlignment(0, 1, HasVerticalAlignment.ALIGN_TOP);
        ticketsDataTable.getFlexCellFormatter().setVerticalAlignment(1, 0, HasVerticalAlignment.ALIGN_TOP);
        ticketsDataTable.getFlexCellFormatter().setVerticalAlignment(2, 0, HasVerticalAlignment.ALIGN_TOP);
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
        idTextFld.setReadOnly(true);


        waterPointNameTfld = new TextField<String>();
        waterPointNameTfld.setFieldLabel("Waterpoint Name");
        waterPointNameTfld.setName("waterpointname");
        waterPointDetails.add(waterPointNameTfld, formData);
        waterPointNameTfld.setReadOnly(true);


        districtTfld = new TextField<String>();
        districtTfld.setFieldLabel("District");
        districtTfld.setName("district");
        waterPointDetails.add(districtTfld, formData);
        districtTfld.setReadOnly(true);


        subcountyTfld = new TextField<String>();
        subcountyTfld.setFieldLabel("Subcounty");
        subcountyTfld.setName("subcounty");
        waterPointDetails.add(subcountyTfld, formData);
        subcountyTfld.setReadOnly(true);


        villageTfld = new TextField<String>();
        villageTfld.setFieldLabel("Village");
        villageTfld.setName("village");
        waterPointDetails.add(villageTfld, formData);
        villageTfld.setReadOnly(true);


        eastingsTfld = new TextField<String>();
        eastingsTfld.setFieldLabel("Eastings");
        eastingsTfld.setName("eastings");
        waterPointDetails.add(eastingsTfld, formData);
        eastingsTfld.setReadOnly(true);

        northingsTfld = new TextField<String>();
        northingsTfld.setFieldLabel("Northings");
        northingsTfld.setName("northings");
        waterPointDetails.add(northingsTfld, formData);
        northingsTfld.setReadOnly(true);

        dateIstalledTfld = new TextField<String>();
        dateIstalledTfld.setFieldLabel("Date Installed");
        dateIstalledTfld.setName("installdate");
        waterPointDetails.add(dateIstalledTfld, formData);
        dateIstalledTfld.setReadOnly(true);

        fundingSrcTfld = new TextField<String>();
        fundingSrcTfld.setFieldLabel("Funding Source");
        fundingSrcTfld.setName("fundingsrc");
        waterPointDetails.add(fundingSrcTfld, formData);
        fundingSrcTfld.setReadOnly(true);

        ownershipTfld = new TextField<String>();
        ownershipTfld.setFieldLabel("Ownership");
        ownershipTfld.setName("ownership");
        waterPointDetails.add(ownershipTfld, formData);
        ownershipTfld.setReadOnly(true);

        houseHoldsTfld = new TextField<String>();
        houseHoldsTfld.setFieldLabel("Number Of Households");
        houseHoldsTfld.setName("households");
        waterPointDetails.add(houseHoldsTfld, formData);
        houseHoldsTfld.setReadOnly(true);

        typeOfMagtTfld = new TextField<String>();
        typeOfMagtTfld.setFieldLabel("Type of management");
        typeOfMagtTfld.setName("typeofmagt");
        waterPointDetails.add(typeOfMagtTfld, formData);
        typeOfMagtTfld.setReadOnly(true);

        return waterPointDetails;
    }

    public void setInspectionQuestion(List<Inspection> questions) {
        inspectionFldset = new FieldSet();
        inspectionFldset.setHeading(appMessages.waterpointInspection());
        inspectionFldset.setCheckboxToggle(false);

        FormLayout layout = new FormLayout();
        layout.setLabelWidth(150);
        inspectionFldset.setLayout(layout);

        questionTflds = new ArrayList<TextField<String>>();
        if (!questions.isEmpty()) {
            Inspection x = questions.get(questions.size() - 1);
            Set quiz = x.getInspectionQuestionses();
            for (Object object : quiz) {
                TextField<String> questionFld = new TextField<String>();
                questionFld.setFieldLabel(((InspectionQuestions) object).getQuestion());
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
        inspectionFldset.setWidth("98%");
        ticketsDataTable.setWidget(0,1, inspectionFldset);
        ticketsDataTable.getFlexCellFormatter().setRowSpan(0, 1, 3);
        ticketsDataTable.getFlexCellFormatter().setWidth(0, 1, "50%");
        ticketsDataTable.getFlexCellFormatter().setVerticalAlignment(0, 1, HasVerticalAlignment.ALIGN_TOP);
    }

    public FieldSet getWaterUsercommitteeFldset(Waterpoint point) {
        FieldSet userCommittee = new FieldSet();
        userCommittee.setHeading(appMessages.waterUserCommittee());
        userCommittee.setCheckboxToggle(false);

        FormLayout layout = new FormLayout();
        layout.setLabelWidth(100);
        userCommittee.setLayout(layout);
        initializeTextfields();
        List<WaterUserCommittee> committee = new ArrayList<WaterUserCommittee>(point.getWaterUserCommittees());
        for (WaterUserCommittee x : committee) {
            waterUserCommitteeFields = new HashMap<String, TextField<String>>();
            userCommittee.add(addUserCommitteeFld("Commissioned", x.getCommissioned()), formData);
            userCommittee.add(addUserCommitteeFld("Year Established", x.getYrEstablished()), formData);
            userCommittee.add(addUserCommitteeFld("Trained", x.getTrained()), formData);
            userCommittee.add(addUserCommitteeFld("Collect Fees", x.getCollectFees()), formData);
            userCommittee.add(addUserCommitteeFld("Regular Service", x.getRegularService()), formData);
            userCommittee.add(addUserCommitteeFld("Regular Meeting", x.getRegularMeeting()), formData);
            userCommittee.add(addUserCommitteeFld("Functionality Of Committee", x.getFunctionalityOfWuc()), formData);
            userCommittee.add(addUserCommitteeFld("Number Of Members", x.getNoOfMembersOnWuc()), formData);
            userCommittee.add(addUserCommitteeFld("Number Of Active Members", x.getNoActiveMembers()), formData);
            userCommittee.add(addUserCommitteeFld("Number Of Women on Committee", x.getNoOfWomen()), formData);
            userCommittee.add(addUserCommitteeFld("Number Of Women in Key Positions", x.getNoOfWomenKeypos()), formData);
            break;
        }
        if (committee.isEmpty()) {
            Label lable = new Label("There is no Water User Committee for this waterpoint");
            userCommittee.add(lable);
        }
        ticketsDataTable.getFlexCellFormatter().setHeight(0, 0, detailsFldSet.getHeight()+"px");
        ticketsDataTable.setWidget(1,0,userCommittee);
        ticketsDataTable.getFlexCellFormatter().setVerticalAlignment(1, 0, HasVerticalAlignment.ALIGN_TOP);
        return userCommittee;
    }
    public FieldSet getWaterFunctionalityFldset(Waterpoint point){
        FieldSet functionality = new FieldSet();
        functionality.setHeading(appMessages.waterFunctionality());
        functionality.setCheckboxToggle(false);

        FormLayout layout = new FormLayout();
        layout.setLabelWidth(100);
        functionality.setLayout(layout);
        initializeTextfields();
        List<WaterFunctionality> func = new ArrayList<WaterFunctionality>(point.getWaterFunctionalities());
        for (WaterFunctionality x : func) {
            waterFunctionalityFields = new HashMap<String, TextField<String>>();
            functionality.add(addFunctionalityFld("Functionality Status", x.getFunctionalityStatus()), formData);
            functionality.add(addFunctionalityFld("Date non Functional", x.getDateNonFunctional().toString()), formData);
            functionality.add(addFunctionalityFld("Reason", x.getReason()), formData);
            functionality.add(addFunctionalityFld("Date Last Repaired", x.getDateLastRepaired().toString()), formData);
            functionality.add(addFunctionalityFld("Details Last Repair", x.getDetailsLastRepair()), formData);
            functionality.add(addFunctionalityFld("Date Last minor service", x.getDateLastMinorService().toString()), formData);
            functionality.add(addFunctionalityFld("Date Last major service", x.getDateLastMajorService().toString()), formData);
            functionality.add(addFunctionalityFld("Any previous Repairs", x.getAnyPreviousRepairs()), formData);
            functionality.add(addFunctionalityFld("Any Previous Major service", x.getAnyPreviousMajorService()), formData);
            functionality.add(addFunctionalityFld("Any Previous Minor service", x.getAnyPreviousMinorService()), formData);
        }
        if (func.isEmpty()) {
            Label lable = new Label("There is no Water Functionality for this waterpoint");
            functionality.add(lable);
        }
        ticketsDataTable.getFlexCellFormatter().setHeight(1, 0, wucFldSet.getHeight() + "px");
        ticketsDataTable.setWidget(2, 0, functionality);
        ticketsDataTable.getFlexCellFormatter().setVerticalAlignment(2, 0, HasVerticalAlignment.ALIGN_TOP);
        return functionality;
    }
    private TextField<String> addUserCommitteeFld(String name, String value) {
        TextField<String> committeeQuiz = new TextField<String>();
        committeeQuiz.setFieldLabel(name);
        committeeQuiz.setValue(value);
        waterUserCommitteeFields.put(name, committeeQuiz);
        committeeQuiz.setReadOnly(true);
        return committeeQuiz;

    }
    private TextField<String> addFunctionalityFld(String name, String value) {
        TextField<String> functionalityQuiz = new TextField<String>();
        functionalityQuiz.setFieldLabel(name);
        functionalityQuiz.setValue(value);
        waterFunctionalityFields.put(name, functionalityQuiz);
        functionalityQuiz.setReadOnly(true);
        return functionalityQuiz;

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
    FieldSet wucFldSet;
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
        setInspectionQuestion(new ArrayList<Inspection>(waterPoint.getInspections()));
        wucFldSet = getWaterUsercommitteeFldset(waterPoint);
        getWaterFunctionalityFldset(waterPoint);
    }

    @Override
    protected void handleEvent(AppEvent event) {
        GWT.log("Edit waterpoint : handleEvent");
        if (event.getType() == EditWaterPointController.EDIT_WATER_POINT) {
            String waterPointId = event.getData();
            EditWaterPointController controller2 = (EditWaterPointController) EditWaterPointView.this.getController();
            controller2.getWaterPoint(waterPointId);
//            controller2.getInspections();
            showWindow();
        }
    }
}
