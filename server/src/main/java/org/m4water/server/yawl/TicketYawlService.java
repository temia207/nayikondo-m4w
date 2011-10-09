package org.m4water.server.yawl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.SessionFactory;
import org.jdom.JDOMException;
import org.m4water.server.OpenXDataPropertyPlaceholderConfigurer;
import org.m4water.server.admin.model.FaultAssessment;
import org.m4water.server.admin.model.Problem;
import org.m4water.server.admin.model.WaterFunctionality;
import org.m4water.server.admin.model.Waterpoint;
import org.m4water.server.security.util.UUID;
import org.m4water.server.service.AssessmentService;
import org.m4water.server.service.WaterPointService;
import org.muk.fcit.results.util.MLogger;
import org.openxdata.yawl.util.InterfaceBHelper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.yawlfoundation.yawl.elements.data.YParameter;
import org.yawlfoundation.yawl.engine.YSpecificationID;
import org.yawlfoundation.yawl.engine.interfce.WorkItemRecord;
import org.yawlfoundation.yawl.engine.interfce.interfaceB.InterfaceBWebsideController;
import org.yawlfoundation.yawl.exceptions.YAWLException;
import org.yawlfoundation.yawl.util.StringUtil;

/**
 *
 * @author kay
 */
@Component("yawlTicketService")
public class TicketYawlService extends InterfaceBWebsideController implements InitializingBean {

    private static TicketYawlService ticketYawlService;
    private InterfaceBHelper yawlHelper;
    @Autowired
    private WaterPointService waterPointService;
    @Autowired
    private OpenXDataPropertyPlaceholderConfigurer properties;
    @Autowired
    private AssessmentService assessmentService;


    public static TicketYawlService getInstance() {
        return ticketYawlService;
    }

    @Override
    public void handleEnabledWorkItemEvent(WorkItemRecord enabledWorkItem) {
        try {

            yawlHelper.initSessionHandle();
            List<WorkItemRecord> workitems = yawlHelper.checkOutWorkitemAndChildren(enabledWorkItem);

            for (WorkItemRecord workItemRecord : workitems) {

                processWorkitem(workItemRecord);

            }


        } catch (JDOMException ex) {
            Logger.getLogger(TicketYawlService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (YAWLException ex) {
            Logger.getLogger(TicketYawlService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TicketYawlService.class.getName()).log(Level.SEVERE, null, ex);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void handleCancelledWorkItemEvent(WorkItemRecord workItemRecord) {
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("Initiations the yawl custom service");
        yawlHelper = new InterfaceBHelper(this, _interfaceBClient, DEFAULT_ENGINE_USERNAME, DEFAULT_ENGINE_PASSWORD);
        ticketYawlService = this;
    }

    public void launchCase(Params params) throws IOException, YAWLException {
        Properties resolvedProps = properties.getResolvedProps();
        String launchCase = _interfaceBClient.launchCase(new YSpecificationID("WaterFlow",resolvedProps.getProperty("yawl.version")), params.asXML(), yawlHelper.initSessionHandle());
        boolean successful = successful(launchCase);
        if (!successful) {
            throw new YAWLException(launchCase);
        }
    }

    private void processWorkitem(WorkItemRecord workItemRecord) throws IOException, JDOMException {
        try {
            String waterPointID = InterfaceBHelper.getValueFromWorkItem(workItemRecord, "waterPointID");
            String assesment = InterfaceBHelper.getValueFromWorkItem(workItemRecord, "assesment");
            String repairDetails = InterfaceBHelper.getValueFromWorkItem(workItemRecord, "repairDetails");
            String problemFixed = InterfaceBHelper.getValueFromWorkItem(workItemRecord, "problemFixed");
            String reasonNotFixed = InterfaceBHelper.getValueFromWorkItem(workItemRecord, "reasonNotFixed");

            Waterpoint waterPoint = waterPointService.getWaterPoint(waterPointID);
            if(waterPoint == null)
                throw new RuntimeException("Water point id ["+waterPointID+"] sent from yawl was not found");
            WaterFunctionality functionality = new WaterFunctionality(new Date(), waterPoint, problemFixed, new Date(), _report, new Date(), repairDetails, new Date(), new Date());
            Set waterFunctionality = waterPoint.getWaterFunctionality();
            waterFunctionality.add(functionality);
            waterPoint.setWaterFunctionality(waterFunctionality);
            functionality.setFunctionalityStatus("Working = " + problemFixed + " Assesment = " + assesment);
            //            waterPoint.setDate(new Date());
            Date baselineDate = waterPoint.getBaselineDate();
            if(baselineDate==null)
                baselineDate = new Date(1);
            waterPoint.setBaselineDate(baselineDate);
            try {
                saveWaterPoint(waterPoint);
            } catch (Exception e) {
                System.out.println("Problem saving problem log for waterpoint ["+waterPointID+"] in ticaket yawl service");
                e.printStackTrace();
            }
            FaultAssessment assessmentItm = new FaultAssessment();
            assessmentItm.setId(UUID.jUuid());
            Set problems = waterPoint.getProblems();
            if(problems == null ||problems.isEmpty() ){
                throw new RuntimeException("Water point ["+waterPointID+"] id from yawl has no reported problems");
            }
            assessmentItm.setProblem((Problem)waterPoint.getProblems().iterator().next());
            assessmentItm.setFaults(assesment);
            assessmentItm.setProblemFixed(problemFixed);
            assessmentItm.setRepairsDone(repairDetails);
            assessmentItm.setReasonNotFixed(reasonNotFixed);
            assessmentItm.setUserId("n/a");
            assessmentItm.setDate(new  Date());

           try{ saveFaultAssessment(assessmentItm);
            }catch(Exception ex){
                System.out.println("Problem save fault assement in ticaket yawl service");
                ex.printStackTrace();
            }



        } finally {
            yawlHelper.checkInWorkItem(workItemRecord);
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
                add(new InterfaceBHelper.YParam("waterPointID", XSD_STRINGTYPE, YParameter._INPUT_PARAM_TYPE, "WaterPoint ID"));
                add(new InterfaceBHelper.YParam("assesment", XSD_STRINGTYPE, YParameter._INPUT_PARAM_TYPE, "Assesment"));
                add(new InterfaceBHelper.YParam("repairDetails", XSD_STRINGTYPE, YParameter._INPUT_PARAM_TYPE, "Repair Details"));
                add(new InterfaceBHelper.YParam("problemFixed", "boolean", YParameter._INPUT_PARAM_TYPE, "Prolem Fixed"));
                add(new InterfaceBHelper.YParam("reasonNotFixed", XSD_STRINGTYPE, YParameter._INPUT_PARAM_TYPE, "Reason not FIxed"));
            }
        };

        return yawlHelper.describeRequiredParams(params);
    }

    public void launchCase(String specName,String version, Params params) throws IOException {
        _interfaceBClient.launchCase(new YSpecificationID(specName, version), params.asXML(), yawlHelper.initSessionHandle());
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

        public String asXML() {
            StringBuilder builder = new StringBuilder();
            Set<Entry<String, String>> keySet = params.entrySet();
            for (Entry<String, String> entry : keySet) {
                String tag = entry.getKey();
                String value = entry.getValue();
                builder.append(StringUtil.wrapEscaped(value, tag));
            }
            return StringUtil.wrap(builder.toString(), "WaterFlow");
        }
    }
}
