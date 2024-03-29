package org.m4water.server.yawl;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom.JDOMException;
import org.m4water.server.OpenXDataPropertyPlaceholderConfigurer;
import org.m4water.server.admin.model.FaultAssessment;
import org.m4water.server.admin.model.Problem;
import org.m4water.server.admin.model.WaterFunctionality;
import org.m4water.server.admin.model.WaterUserCommittee;
import org.m4water.server.admin.model.Waterpoint;
import org.m4water.server.security.util.UUID;
import org.m4water.server.service.AssessmentService;
import org.m4water.server.service.WUCService;
import org.m4water.server.service.WaterFunctionalityService;
import org.m4water.server.service.WaterPointService;
import org.m4water.server.service.WorkItemsService;
import org.openxdata.yawl.util.InterfaceBHelper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yawlfoundation.yawl.elements.data.YParameter;
import org.yawlfoundation.yawl.engine.YSpecificationID;
import org.yawlfoundation.yawl.engine.interfce.WorkItemRecord;
import org.yawlfoundation.yawl.exceptions.YAWLException;
import org.yawlfoundation.yawl.util.StringUtil;
import org.openxdata.yawl.util.YawlPinger;
import org.openxdata.yawl.util.YawlPingerListener;
import static org.openxdata.yawl.util.InterfaceBHelper.*;

/**
 *
 * @author kay
 */
@Component("yawlTicketService")
public class TicketYawlService extends YawlPingerListener implements InitializingBean {

	private static TicketYawlService ticketYawlService;
	private InterfaceBHelper yawlHelper;
	@Autowired
	private WaterPointService waterPointService;
	@Autowired
	private OpenXDataPropertyPlaceholderConfigurer properties;
	@Autowired
	private AssessmentService assessmentService;
	@Autowired
	WaterFunctionalityService funxService;
	@Autowired
	WUCService wUCService;
	private org.slf4j.Logger log = LoggerFactory.getLogger(TicketYawlService.class);
	private YawlPinger pinger;
	@Autowired
	private WorkItemsService workitemService;
	@Autowired
	private WorkitemReprocessor workitemReprocessor;

	public static TicketYawlService getInstance() {
		return ticketYawlService;
	}

	@Override
	public void handleEnabledWorkItemEvent(WorkItemRecord enabledWorkItem) {
		try {

			yawlHelper.initSessionHandle();
			List<WorkItemRecord> workitems = yawlHelper.checkOutWorkitemAndChildren(enabledWorkItem);

			for (WorkItemRecord workItemRecord : workitems) {

                processWorkitemAndCheckIn(workItemRecord);

            }
		} catch (JDOMException ex) {
			Logger.getLogger(TicketYawlService.class.getName()).log(Level.SEVERE, null, ex);
		} catch (YAWLException ex) {
			Logger.getLogger(TicketYawlService.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(TicketYawlService.class.getName()).log(Level.SEVERE, null, ex);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

    private void processWorkitemAndCheckIn(WorkItemRecord workItemRecord) throws IOException, JDOMException {
        try {
            processWorkitem(workItemRecord);
        } catch (Exception e) {
            log.error("Error occured while processing workitem from yawl: "+workItemRecord.getID(),e);
        }finally {
            log.info("Checking in workitem: ["+workItemRecord.getID()+"]");
            _model.addWorkItem(workItemRecord);
            yawlHelper.checkInWorkItem(workItemRecord);
        }
    }

    @Override
	public void handleCancelledWorkItemEvent(WorkItemRecord workItemRecord) {
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		log.debug("Initiations the yawl custom service done by spring");
		ticketYawlService = this;
	}

	@Override
	public void setUpInterfaceBClient(String backEndURI) {
		super.setUpInterfaceBClient(backEndURI);
		log.trace("Initiating interfaceB helper");
		yawlHelper = new InterfaceBHelper(this, _interfaceBClient, DEFAULT_ENGINE_USERNAME, DEFAULT_ENGINE_PASSWORD);
		pinger = new YawlPinger(yawlHelper, ticketYawlService);
		pinger.setServiceID("m4wticket");
		log.debug("Starting Yawl Pinger for m4w.web app");
		pinger.start();
		new Thread(workitemReprocessor, "WorkitemReprocessor").start();
	}

	public String launchCase(Params params) throws IOException, YAWLException {
		Properties resolvedProps = properties.getResolvedProps();
		log.debug("launching ticket case: " + params.params.toString());
		String caseID = _interfaceBClient.launchCase(new YSpecificationID("WaterFlow", resolvedProps.getProperty("yawl.version")), params.asXML("WaterFlow"), yawlHelper.initSessionHandle());
		boolean successful = successful(caseID);
		if (!successful) {
			log.error("Launching baseline failed: " + caseID);
			throw new YAWLException(caseID);
		}
		return caseID;
	}

	void processWorkitem(WorkItemRecord workItemRecord) throws IOException, JDOMException, Exception {
		String status = null;
		String tag = null;
		try {
			log.debug("received workitem : " + workItemRecord.getID());
			String action = getValueFromWorkItem(workItemRecord, "action");
			if (action != null && action.equals("baseline")) {
				processBaseLine(workItemRecord);
			} else {
				processAssesMent(workItemRecord);
			}
			status = WorkItemRecord.statusComplete;
			tag = "Processed Succesfuly";
		} catch (Exception e) {
			status = WorkItemRecord.statusFailed;
			tag = e.getMessage();
			throw e;
		} finally {
			try {//remeber to checking workitems
				saveWorkitem(workItemRecord, status, tag);
			} catch (Exception e) {
				log.error("ERROR OCCURED WHILE SAVING WORKITEM..", e);
			}
		}
	}

	private void saveWorkitem(WorkItemRecord wir, String status, String tag) {
		WorkItemRecord wirFromDb = workitemService.getWorkitem(wir.getCaseID());
		if (wirFromDb != null) {
			log.debug("Updating workiem: [" + wirFromDb.getID() + "] with tag [" + tag + "] and status [" + status + "]");
			wirFromDb.setTag(tag);
			wirFromDb.setStatus(status);
			workitemService.saveWorkitem(wirFromDb);
		} else {
			log.debug("Saving new workiem: [" + wir.getID() + "] with tag [" + tag + "] and status [" + status + "]");
			wir.setTag(tag);
			wir.setStatus(status);
			workitemService.saveWorkitem(wir);
		}
	}

	private void processAssesMent(WorkItemRecord workItemRecord) throws RuntimeException, Exception {
		String waterPointID = getValueFromWorkItem(workItemRecord, "waterPointID");
		String assesment = getValueFromWorkItem(workItemRecord, "waterPointAssessment");
		String repairDone = getValueFromWorkItem(workItemRecord, "repairsDone");
		String problemFixed = getValueFromWorkItem(workItemRecord, "waterPointIsFixed");
		String reasonNotFixed = getValueFromWorkItem(workItemRecord, "waterPointWhyNotFixed");

        String typeOfFault = getValueFromWorkItem(workItemRecord, "typeOfFault");
        String assesorTel = getValueFromWorkItem(workItemRecord,"AssessorTel");
        String repairsDone = getValueFromWorkItem(workItemRecord,"repairsDone");
        String costLabour = getValueFromWorkItem(workItemRecord ,"costLabor");
        String costSpares = getValueFromWorkItem(workItemRecord,"CostSpares");
        String comment =  getValueFromWorkItem(workItemRecord,"comment");
        String assessorName = getValueFromWorkItem(workItemRecord,"assessorName");
        String repairPlan = getValueFromWorkItem(workItemRecord,"waterPointDetailsOfRepairPlan");




                              //  costLabor
        //   repairsDone
        // CostSpares
        // comment
        // assessorName

		System.out.println("Processing workitem recourd Retrieving waterpoint "
			+ "\nWaterpoint id = " + waterPointID
			+ "\n assesment = " + assesment
			+ "\n repairDetails = " + repairDone
			+ "\n ");
		Waterpoint waterPoint = waterPointService.getWaterPoint(waterPointID);
		if (waterPoint == null) {
			throw new RuntimeException("Water point id [" + waterPointID + "] sent from yawl was not found");
		}
		WaterFunctionality functionality = new WaterFunctionality(new Date(), waterPoint, problemFixed, new Date(), _report, new Date(), repairDone, new Date(), new Date(), "", "", "");
		Set waterFunctionality = waterPoint.getWaterFunctionalities();
		waterFunctionality.add(functionality);
		waterPoint.setWaterFunctionalities(waterFunctionality);
		functionality.setFunctionalityStatus("Working = " + problemFixed + " Assesment = " + assesment);
		//            waterPoint.setDate(new Date());
		Date baselineDate = waterPoint.getBaselineDate();
		if (baselineDate == null) {
			baselineDate = new Date(1);
		}
		waterPoint.setBaselineDate(baselineDate);
		try {
			saveWaterPoint(waterPoint);
		} catch (Exception e) {
			System.out.println("Problem saving problem log for waterpoint [" + waterPointID + "] in ticaket yawl service");
			throw e;
		}
		FaultAssessment assessmentItm = new FaultAssessment();
		assessmentItm.setId(UUID.jUuid());
		Problem problem = waterPoint.findProblemWithYawlId(workItemRecord.getRootCaseID());

        if(problem == null)
            problem = waterPointService.getLatestProblem(waterPoint);
		if (problem == null ) {
			throw new RuntimeException("Water point [" + waterPointID + "] id from yawl has no reported problems");
		}

		assessmentItm.setProblem(problem);
		assessmentItm.setFaults(assesment);
		assessmentItm.setProblemFixed(problemFixed);
		assessmentItm.setRepairsDone(repairDone);
		assessmentItm.setReasonNotFixed(reasonNotFixed);
        assessmentItm.setRecommendations(comment);
        assessmentItm.setRepairsDone(repairsDone);
        assessmentItm.setCostOfLabour(costLabour);
        assessmentItm.setCostOfMaterials(costSpares);
        assessmentItm.setAssessedBy(assessorName);
        assessmentItm.setAssessorTel(assesorTel);
        assessmentItm.setTypeOfRepairesNeeded(repairPlan);
		assessmentItm.setUserId("n/a");
		assessmentItm.setDate(new Date());


		try {
			saveFaultAssessment(assessmentItm);
		} catch (Exception ex) {
			System.out.println("Problem save fault assement in ticaket yawl service");
			ex.printStackTrace();
		}
	}

	private void saveWaterPoint(final Waterpoint waterPoint) {
		waterPointService.saveWaterPoint(waterPoint);

	}

	private void saveFaultAssessment(final FaultAssessment assessment) {
		assessmentService.saveAssessment(assessment);
	}

	@Override
	public YParameter[] describeRequiredParams() {

		List<InterfaceBHelper.YParam> params = new ArrayList<InterfaceBHelper.YParam>() {

			{
				add(new InterfaceBHelper.YParam("action", XSD_STRINGTYPE, YParameter._INPUT_PARAM_TYPE, "baseline or ticket"));
			}
		};

		return yawlHelper.describeRequiredParams(params);
	}

	public String launchCase(String specName, String version, Params params) throws IOException {
		log.debug("Launching Baseline: " + params.params.toString());
		String launchCase = _interfaceBClient.launchCase(new YSpecificationID(specName, version), params.asXML("BaselineNet"), yawlHelper.initSessionHandle());
		boolean successful = successful(launchCase);
		if (!successful) {
			log.error("laucnhing basline failed: " + launchCase);
			throw new RuntimeException(new YAWLException(launchCase));//WaterFlow
		}
		return launchCase;
	}

	void processBaseLine(WorkItemRecord workItemRecord) {
		//Functionality of of water user committee
		//collect fees

		String source_number = getValueFromWorkItem(workItemRecord, "source_number");
		String functionality = getValueFromWorkItem(workItemRecord, "functionality");
		String wuc_member_number = getValueFromWorkItem(workItemRecord, "wuc_member_number");
		String wuc_women_number = getValueFromWorkItem(workItemRecord, "wuc_women_number");
		String non_functional_days = getValueFromWorkItem(workItemRecord, "non_functional_days");
		String non_functional_reason = getValueFromWorkItem(workItemRecord, "non_functional_reason");
		String wuc_established = getValueFromWorkItem(workItemRecord, "wuc_established");
		String current_ownership = getValueFromWorkItem(workItemRecord, "current_ownership");
		String wuc_functional = getValueFromWorkItem(workItemRecord, "wuc_functional");
		String wuc_trained = getValueFromWorkItem(workItemRecord, "wuc_trained");
		String wuc_women_key_number = getValueFromWorkItem(workItemRecord, "wuc_key_number");
		String management_type = getValueFromWorkItem(workItemRecord, "management_type");
		String activeWUCmembers = getValueFromWorkItem(workItemRecord, "activeWUCmembers");
		if (activeWUCmembers == null) {
			activeWUCmembers = getValueFromWorkItem(workItemRecord, "wuc_active_members");
		}
		String assessorName = getValueFromWorkItem(workItemRecord, "assessorName");//TODO Store this
		if (assessorName == null) {
			assessorName = getValueFromWorkItem(workItemRecord, "name_of_reporter");
		}
		String dateVisit = getValueFromWorkItem(workItemRecord, "dateVisit");
		String dateLastMinorService = getValueFromWorkItem(workItemRecord, "dateMinorService");
		String dateLastMajorService = getValueFromWorkItem(workItemRecord, "dateMajorService");
		String water_point_found = getValueFromWorkItem(workItemRecord, "water_point_found");
		String care_taker_name = getValueFromWorkItem(workItemRecord, "care_taker_name");
		String caretaker_tel = getValueFromWorkItem(workItemRecord, "caretaker_tel");
		String dateLastRepaired = getValueFromWorkItem(workItemRecord, "date_last_repair");
		//location_source_name
		//picture_img
		//location_village
		//location_subcounty
		//location_parish
		//location_district
		//water_point_found
		//approve_data




		System.out.println("Processing workitem recourd Retrieving waterpoint "
			+ "\nWaterpoint id = " + source_number
			+ "\n functionality = " + functionality
			+ "\n ");

		Waterpoint waterPoint = waterPointService.getWaterPoint(source_number);
		if (waterPoint == null) {
			throw new RuntimeException("WaterPointID [" + source_number + "]from yawl in Baseline Could not be found");
		}
		waterPoint.setFundingSource(current_ownership);
		waterPoint.setBaselineDate(new Date());
		waterPoint.setTypeOfMagt(management_type);
		waterPoint.setBaselinePending("F");
		waterPoint.setCareTakerName(care_taker_name);
		waterPoint.setCareTakerTel(caretaker_tel);

		WaterFunctionality funx = new WaterFunctionality(new Date(),
			waterPoint,
			functionality,
			new Date(1),
			non_functional_reason,
			new Date(1),
			"No Last Repair",
			new Date(1),
			new Date(1), "", "", "");
		funx.setId(UUID.jUuid());;
		funx.setWaterpoint(waterPoint);
		funx.setFunctionalityStatus(functionality);
		funx.setReason(non_functional_reason);
		funx.setDetailsLastRepair("");
		funx.setDateLastMajorService(toDate(dateLastMajorService));
		funx.setDateLastMinorService(toDate(dateLastMinorService));
		funx.setDateLastRepaired(toDate(dateLastRepaired));
		if (!toDate(non_functional_days).equals(new Date(1))) {
			funx.setDateNonFunctional(toDate(non_functional_days));
		}
		funx.setNameOfReporter(assessorName);




		WaterUserCommittee wuc = new WaterUserCommittee();
		wuc.setUserCommitteeId(UUID.jUuid());
		wuc.setTrained(wuc_trained);
		wuc.setNoActiveMembers(activeWUCmembers);
		wuc.setNoOfMembersOnWuc(wuc_member_number);
		wuc.setFunctionalityOfWuc(wuc_functional);
		wuc.setNoOfWomen(wuc_women_number);
		wuc.setNoOfWomenKeypos(wuc_women_key_number);
		wuc.setWaterpoint(waterPoint);
		wuc.setCommissioned(wuc_established);
		if(wuc.getYrEstablished()==null){
			wuc.setYrEstablished("1900");
		}
		

		wUCService.save(wuc);
		funxService.save(funx);
		waterPointService.saveWaterPoint(waterPoint);
	}

	public void cancelWorkflow(String caseID) throws IOException {
		yawlHelper.cancelCase(caseID, yawlHelper.initSessionHandle());
	}

	@Override
	public void handleExcutingWorkitem(WorkItemRecord wir) {
		try {
               processWorkitemAndCheckIn(wir);
		} catch (Exception ex) {
			log.error("Error while processing executing workitem", ex);
		}
	}

	public static class Params {

		Map<String, String> params = new LinkedHashMap<String, String>();

		public void addPumpMechanicName(String mechName) {
			params.put("user_pumpMechanic_u", mechName);
		}

		public void setMechanicNumber(String number) {
			params.put("number_pumpMechanic", number);
		}

		public void setWaterPointID(String waterPointID) {
			params.put("waterPointID", waterPointID);
		}

		public void setSenderNumber(String senderNumber) {
			params.put("number_sender", senderNumber);
		}

		public void setTicketMessage(String message) {
			params.put("msgTicketCommentFromSender", message);
		}

		public String remove(Object key) {
			return params.remove(key);
		}

		public String put(String key, String value) {
			return params.put(key, value);
		}

		public String asXML(String WaterFlow) {
			StringBuilder builder = new StringBuilder();
			Set<Entry<String, String>> keySet = params.entrySet();
			for (Entry<String, String> entry : keySet) {
				String tag = entry.getKey();
				String value = entry.getValue();
				builder.append(StringUtil.wrapEscaped(value, tag));
			}

			return StringUtil.wrap(builder.toString(), WaterFlow);
		}
	}

	private Date toDate(String dateString) {

		Date date = new Date(1);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			date = df.parse(dateString);
		} catch (Exception ex) {
			log.error("Error while processing date from yawl:" + dateString + " : " + ex.getMessage());
		}
		return date;
	}
}
