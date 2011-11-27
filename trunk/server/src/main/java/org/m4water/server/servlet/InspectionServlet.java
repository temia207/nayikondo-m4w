package org.m4water.server.servlet;

import org.m4water.server.admin.model.District;
import org.m4water.server.xml.InspectionQuestion;
import org.m4water.server.xml.Inspection;
import org.m4water.server.xml.InspectionQuestions;
import java.io.IOException;
import java.io.StringReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.m4water.server.admin.model.InspectionQuestionType;
import org.m4water.server.admin.model.Setting;
import org.m4water.server.admin.model.SettingGroup;
import org.m4water.server.admin.model.Subcounty;
import org.m4water.server.admin.model.Waterpoint;
import org.m4water.server.admin.model.WaterpointTypes;
import org.m4water.server.security.util.UUID;
import org.m4water.server.service.DistrictService;
import org.m4water.server.service.InspectionService;
import org.m4water.server.service.SettingService;
import org.m4water.server.service.UserService;
import org.m4water.server.service.WUCService;
import org.m4water.server.service.WaterFunctionalityService;
import org.m4water.server.service.WaterPointService;
import org.m4water.server.xml.WaterPointsList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 *
 * @author kay
 */
public class InspectionServlet extends HttpServlet {

    @Autowired
    private UserService userService;
    @Autowired
    private WaterPointService waterPointService;
    @Autowired
    private InspectionService insptnSrvc;
    @Autowired
    private DistrictService districtService;
    @Autowired
    private WaterFunctionalityService funxService;
    @Autowired
    private WUCService wUCService;
    private JAXBContext jxbCntxt;
    @Autowired
    private PlatformTransactionManager transactionManager;
    private TransactionTemplate txTemplate;
    private WaterpointTypes waterpointType;
    @Autowired
    private SettingService settingService;
//    @Autowired private SettingGroupDAO settingGroup;

    @Override
    public void init() throws ServletException {

	super.init();
	try {
	    jxbCntxt = JAXBContext.newInstance(Inspection.class, WaterPointsList.class);
	} catch (JAXBException ex) {
	    throw new ServletException(ex);
	}
	ServletContext sctx = this.getServletContext();
	WebApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(sctx);
	ctx.getAutowireCapableBeanFactory().autowireBean(this);
	txTemplate = new TransactionTemplate(transactionManager);
	this.waterpointType = waterPointService.getWaterPointType("BH");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	String parameter = req.getParameter("username");
	String password = req.getParameter("password");
	String action = req.getParameter("action");
	String xml = req.getParameter("xml");
	try {
	    if ("inspectionUpdate".equalsIgnoreCase(action))
		handleInspection(xml, resp);
	    else if ("newWaterPoint".equalsIgnoreCase(action))
		handleNewWaterPoint(xml, resp);


	} catch (Exception ex) {
	    ex.printStackTrace();
	    resp.getWriter().print("<fail>Technical Error Occured.Please contact the technical team (" + ex.getMessage() + ")</fail>");
	}
    }

    private void handleInspection(String xml, HttpServletResponse resp) throws IOException, JAXBException, ParseException {
	Unmarshaller createUnmarshaller = jxbCntxt.createUnmarshaller();
	Inspection unmarshal = (Inspection) createUnmarshaller.unmarshal(new StringReader(xml));
	String waterpointId = unmarshal.getWaterpointId();
	Waterpoint waterPoint = waterPointService.getWaterPoint(waterpointId);
	if (waterPoint == null) {
	    writeMessage(resp, "<fail>Water Point ID is not found</fail>");
	} else {
	    org.m4water.server.admin.model.Inspection createInspection = createInspection(unmarshal);
	    createInspection.setWaterpoint(waterPoint);
	    insptnSrvc.saveInspection(createInspection);
	    writeMessage(resp, "<success>Success</Success>");
	}
    }

    private org.m4water.server.admin.model.Inspection createInspection(Inspection jxbInspctn) throws ParseException {
	org.m4water.server.admin.model.Inspection ins = new org.m4water.server.admin.model.Inspection(java.util.UUID.randomUUID().toString());
	DateFormat df = new SimpleDateFormat("dd-mm-yyyy");

	ins.setInspectionDate(df.parse(jxbInspctn.getDate()));
	ins.setInspectorName(jxbInspctn.getInspectorName());
	ins.setInspectorTitle(null);
	InspectionQuestions inspectionQuestions = jxbInspctn.getInspectionQuestions();
	List<InspectionQuestion> inspectionQuestion = inspectionQuestions.getInspectionQuestion();
	Set<org.m4water.server.admin.model.InspectionQuestions> m4wQuestions = new HashSet<org.m4water.server.admin.model.InspectionQuestions>();
	Map<String, InspectionQuestionType> qnType = new HashMap<String, InspectionQuestionType>();
	for (InspectionQuestion inspectionQuestion1 : inspectionQuestion) {
	    org.m4water.server.admin.model.InspectionQuestions qestin = new org.m4water.server.admin.model.InspectionQuestions(UUID.jUuid());
	    qestin.setQuestion(inspectionQuestion1.getQuestion());
	    qestin.setAnswer(inspectionQuestion1.getAnswer());
	    InspectionQuestionType questionType = qnType.get(inspectionQuestion1.getType());
	    if (questionType == null)
		questionType = insptnSrvc.getQuestionType(inspectionQuestion1.getType());
	    if (questionType == null) {
		questionType = new InspectionQuestionType(UUID.jUuid());
		questionType.setQuestionType(inspectionQuestion1.getType());
	    }
	    qestin.setInspectionQuestionType(questionType);
	    questionType.setInspectionQuestionses(m4wQuestions);
	    m4wQuestions.add(qestin);
	    qnType.put(inspectionQuestion1.getType(), questionType);
	    qestin.setInspection(ins);
	}
	ins.setInspectionQuestionses(m4wQuestions);
	return ins;
    }

    private void handleNewWaterPoint(String xml, HttpServletResponse resp) throws JAXBException, IOException {
	Unmarshaller createUnmarshaller = jxbCntxt.createUnmarshaller();
	WaterPointsList waterPointsList = (WaterPointsList) createUnmarshaller.unmarshal(new StringReader(xml));


	List<SettingGroup> newWaterPoints = new ArrayList<SettingGroup>();

	for (org.m4water.server.xml.Waterpoint jxbWaterPoint : waterPointsList) {
	    //Validate that the district exist
	    District districtByName = districtService.getDistrictByName(jxbWaterPoint.getDistrict());
	    if (districtByName == null) {
		writeMessage(resp, "<fail>District [" + jxbWaterPoint.getDistrict() + "] does not exist</fail>");
		return;
	    }
	    Subcounty subcounty = districtByName.getSubcouty(jxbWaterPoint.getSubcounty());

	    if (subcounty == null) {
		writeMessage(resp, "<fail>Subcounty [" + jxbWaterPoint.getSubcounty() + "] does not exist</fail>");
		return;
	    }
	    //get parent seeting group

	    SettingGroup waterpointGroup = settingService.getSettingGroup("waterpoints");
	    SettingGroup parentGroup = new SettingGroup();
	    parentGroup.setParentSettingGroup(waterpointGroup);
	    parentGroup.setName(UUID.jUuid());
	    createSetting("id", UUID.jUuid(), parentGroup);
	    createSetting("baselinedate", new Date(1).toString(), parentGroup);
	    createSetting("waterpointname", jxbWaterPoint.getSourceName(), parentGroup);
	    createSetting("village", jxbWaterPoint.getVillage(), parentGroup);
	    createSetting("parish", jxbWaterPoint.getParish(), parentGroup);
	    createSetting("subcounty", subcounty.getSubcountyName(), parentGroup);
	    createSetting("county", subcounty.getCounty().getCountyName(), parentGroup);
	    createSetting("district", districtByName.getName(), parentGroup);
	    createSetting("typeOfManagement", jxbWaterPoint.getTypeOfManagement(), parentGroup);
	    createSetting("ownership", jxbWaterPoint.getCurrentOwnership() == null ? "N/A" : jxbWaterPoint.getCurrentOwnership(), parentGroup);//TODO: Send user message abt owner ship
	    createSetting("waterpointType", waterpointType.getName(), parentGroup);
	    createSetting("dateInstalled", new Date().toString(), parentGroup);
	    createSetting("fundingSource", jxbWaterPoint.getSourceOfFunding(), parentGroup);
	    createSetting("baselinePending", "F", parentGroup);
	    createSetting("yearOfConstruction", jxbWaterPoint.getYearOfConstruction(), parentGroup);
	    createSetting("sourceNumber", jxbWaterPoint.getSourceNumber(), parentGroup);
	    createSetting("typeOfWaterSource", jxbWaterPoint.getTypeOfWaterSource(), parentGroup);
	    createSetting("sourceOfFunding", jxbWaterPoint.getSourceOfFunding(), parentGroup);
	    


//waterpoint functionality
	    String non_functional_reason = jxbWaterPoint.getReasonNonFunctional();
	    String functionality = jxbWaterPoint.getIsFunctional();
	    //String lastRepair = jxbWaterPoint.get

	    SettingGroup functionalityGroup = new SettingGroup("functionality");
	    functionalityGroup.setParentSettingGroup(parentGroup);
	    List<Setting> waterFunctionality = functionalityGroup.getSettings();
	    Setting date = new Setting("date", "", new Date().toString());
	    date.setSettingGroup(functionalityGroup);
	    waterFunctionality.add(date);
	    Setting functinalityStatus = new Setting("functionalityStatus", "", functionality);
	    functinalityStatus.setSettingGroup(functionalityGroup);
	    waterFunctionality.add(functinalityStatus);
	    Setting dayNonFunctional = new Setting("dayNonFunctional", "", new Date(1).toString());
	    dayNonFunctional.setSettingGroup(functionalityGroup);
	    waterFunctionality.add(dayNonFunctional);
	    Setting reasonNonFunctional = new Setting("reasonNonFunctional", "", non_functional_reason == null ? "N/A" : non_functional_reason);
	    reasonNonFunctional.setSettingGroup(functionalityGroup);
	    waterFunctionality.add(reasonNonFunctional);
	    Setting dateLastRepair = new Setting("dateLastRepair", "", new Date(1).toString());
	    dateLastRepair.setSettingGroup(functionalityGroup);
	    waterFunctionality.add(dateLastRepair);
	    Setting detailsLastRepair = new Setting("detailsLastRepair", "", "No Last Repair");
	    detailsLastRepair.setSettingGroup(functionalityGroup);
	    waterFunctionality.add(detailsLastRepair);
	    Setting dateLastMinorService = new Setting("dateLastMinorService", "", new Date(1).toString());
	    dateLastMinorService.setSettingGroup(functionalityGroup);
	    waterFunctionality.add(dateLastMinorService);
	    Setting dateLastMajorService = new Setting("dateLastMajorService", "", new Date(1).toString());
	    dateLastMajorService.setSettingGroup(functionalityGroup);
	    waterFunctionality.add(dateLastMajorService);
	    Setting id = new Setting("id", "", new Date().toString());
	    waterFunctionality.add(id);
	    parentGroup.getGroups().add(functionalityGroup);

//water user committee
	    if (jxbWaterPoint.getIsWucEstablished().equalsIgnoreCase("yes")) {
		SettingGroup waterUserCom = new SettingGroup("waterusercommittee");
		waterUserCom.setParentSettingGroup(parentGroup);
		List<Setting> wucSettings = waterUserCom.getSettings();
		Setting wid = new Setting("id", "", UUID.jUuid());
		wid.setSettingGroup(waterUserCom);
		wucSettings.add(wid);
		Setting currentOwnership = new Setting("currentOwnership", "", jxbWaterPoint.getCurrentOwnership());
		currentOwnership.setSettingGroup(waterUserCom);
		wucSettings.add(currentOwnership);
		Setting yearEstablished = new Setting("yearEstablished", "", "1900");
		yearEstablished.setSettingGroup(waterUserCom);
		wucSettings.add(yearEstablished);
		Setting trained = new Setting("trained", "", jxbWaterPoint.getIsWucTrained());
		trained.setSettingGroup(waterUserCom);
		wucSettings.add(trained);
		Setting collectFees = new Setting("collectFees", "", "N/A Collect Fees");
		collectFees.setSettingGroup(waterUserCom);
		wucSettings.add(collectFees);
		Setting regularService = new Setting("regualarService", "", jxbWaterPoint.getIsWucFunctional());
		regularService.setSettingGroup(waterUserCom);
		wucSettings.add(regularService);
		Setting regularMeeting = new Setting("regularMeeting", "", "Regular Service Not Known");
		regularMeeting.setSettingGroup(waterUserCom);
		wucSettings.add(regularMeeting);
		Setting noActiveMembers = new Setting("noActiveMembers", "", jxbWaterPoint.getNumberActiveMembers());
		noActiveMembers.setSettingGroup(waterUserCom);
		wucSettings.add(noActiveMembers);
		Setting womenNumber = new Setting("numberOfWomen", "", jxbWaterPoint.getWomenNumberWuc());
		womenNumber.setSettingGroup(waterUserCom);
		wucSettings.add(womenNumber);
		Setting womenKeypos = new Setting("womenKeyPosition", "", jxbWaterPoint.getWomenKeypositionsNumberWuc());
		womenKeypos.setSettingGroup(waterUserCom);
		wucSettings.add(womenKeypos);
		Setting commissioned = new Setting("commissioned", "", "N/A");
		commissioned.setSettingGroup(waterUserCom);
		wucSettings.add(commissioned);
		parentGroup.getGroups().add(waterUserCom);
	    }
	    newWaterPoints.add(parentGroup);
	}

	for (SettingGroup waterpoint : newWaterPoints) {
	    settingService.saveSettingGroup(waterpoint);
	}
	writeMessage(resp, "<success>Success</Success>");
    }

    private void writeMessage(HttpServletResponse resp, String msg) throws IOException {
	resp.getWriter().print(msg);
	resp.getWriter().flush();
    }

    public Setting createSetting(String name, String value, SettingGroup group) {
	Setting setting = new Setting(name, "", value);
	group.getSettings().add(setting);
	setting.setSettingGroup(group);
	return setting;
    }
}
//Get district
//Get Subcounty
//Get Village
//