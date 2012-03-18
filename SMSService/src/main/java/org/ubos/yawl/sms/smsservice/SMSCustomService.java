package org.ubos.yawl.sms.smsservice;

import com.google.inject.Inject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.ubos.yawl.sms.guice.ServletConfig;
import org.ubos.yawl.sms.service.SMSService;
import org.ubos.yawl.sms.service.UserService;
import org.yawlfoundation.yawl.elements.data.YParameter;
import org.yawlfoundation.yawl.engine.YSpecificationID;
import org.yawlfoundation.yawl.engine.interfce.TaskInformation;
import org.yawlfoundation.yawl.engine.interfce.WorkItemRecord;
import org.yawlfoundation.yawl.engine.interfce.interfaceB.InterfaceBWebsideController;
import org.yawlfoundation.yawl.exceptions.YAWLException;
import org.yawlfoundation.yawl.util.JDOMUtil;
import org.yawlfoundation.yawl.util.StringUtil;

/**
 *
 * @author kay
 */
public class SMSCustomService extends InterfaceBWebsideController {

        private static Logger log = Logger.getLogger(SMSCustomService.class);
        private String _sessionHandle = null;
        @Inject
        private UserService userService;
        @Inject
        private SMSService smsService;

        public static SMSCustomService getInstance() {
                return ServletConfig.injector.getInstance(SMSCustomService.class);
        }

        @Override
        public void handleEnabledWorkItemEvent(WorkItemRecord enabledWorkItem) {
                try {
                        String sessionHanlde = initSessionHandle();

                        List<WorkItemRecord> children = getChildren(enabledWorkItem.getID(), sessionHanlde);

                        List<WorkItemRecord> checkOutWirs = new ArrayList<WorkItemRecord>();
                        for (WorkItemRecord workItemRecord : children) {
                                if (WorkItemRecord.statusFired.equals(workItemRecord.getStatus())) {
                                        WorkItemRecord checkedOutWrkItem = checkOut(workItemRecord.getID(), sessionHanlde);
                                        checkOutWirs.add(checkedOutWrkItem);
                                }
                        }

                        enabledWorkItem = checkOut(enabledWorkItem.getID(), sessionHanlde);
                        checkOutWirs.add(enabledWorkItem);

                        for (WorkItemRecord workItemRecord : checkOutWirs) {
                                try {
                                        processWorkItem(workItemRecord);
                                } catch (JDOMException ex) {
                                        log.error("handleEnabledWorkItemEvent: Error While processing enable workitem", ex);
                                }
                        }

                } catch (YAWLException ex) {
                        log.error("handleEnabledWorkItemEvent: Error While processing enable workitem", ex);
                } catch (IOException ex) {
                        log.error("handleEnabledWorkItemEvent: Error While processing enable workitem", ex);
                }catch(Exception ex){
                        log.error("handleEnabledWorkItemEvent: Error While processing enable workitem", ex);
                }
        }

        private void processWorkItem(WorkItemRecord enabledWorkItem) throws IOException, JDOMException {
               try{ String userName = getValueFromWorkItem(enabledWorkItem, "name");
                String phoneNo = userService.getPhoneNoByUsername(userName);
                if (phoneNo == null) {
                        sendSmsUsingParams(enabledWorkItem);
                } else {
                        smsService.sendSMS(phoneNo, userName);
                }
               }finally{
                  String wrap = StringUtil.wrap(null, getDecompositionID(enabledWorkItem));
                  checkInWorkItem(enabledWorkItem, wrap); 
               }
                
        }

        public void checkInWorkItem(WorkItemRecord wir, String outPut) throws IOException, JDOMException {
                Element outPutElement = JDOMUtil.stringToElement(outPut);
                Element inputElement = JDOMUtil.stringToElement(wir.getDataListString());

                checkInWorkItem(wir.getID(), inputElement, outPutElement, initSessionHandle());
                log.debug("Checking in workitem "+wir.getID());
        }

        public String getDecompositionID(WorkItemRecord wrkItem) throws IOException {
                String sessionHandle = initSessionHandle();
                YSpecificationID ySpecId = new YSpecificationID(wrkItem.getSpecificationID(), wrkItem.getSpecVersion());
                TaskInformation taskInformation = getTaskInformation(ySpecId, wrkItem.getTaskID(), sessionHandle);
                log.debug("Getting taskInformation for workitem"+wrkItem.getID());
                return taskInformation.getDecompositionID();
        }

        private String initSessionHandle() throws IOException {

                if (!checkConnection(_sessionHandle)) {
                        _sessionHandle = connect(DEFAULT_ENGINE_USERNAME,
                                DEFAULT_ENGINE_PASSWORD);
                }
                if (!successful(_sessionHandle)) {
                        _logger.error("Unsuccessful");
                        throw new RuntimeException("Failed to initialise session handle");
                }
                return _sessionHandle;
        }

        @Override
        public void handleCancelledWorkItemEvent(WorkItemRecord workItemRecord) {
        }

        @Override
        public YParameter[] describeRequiredParams() {
                YParameter[] params = new YParameter[3];
                YParameter param;

                param = new YParameter(null, YParameter._INPUT_PARAM_TYPE);
                param.setDataTypeAndName(XSD_STRINGTYPE, "name", XSD_NAMESPACE);
                param.setDocumentation("name of recepient");
                params[0] = param;

                param = new YParameter(null, YParameter._INPUT_PARAM_TYPE);
                param.setDataTypeAndName(XSD_STRINGTYPE, "number", XSD_NAMESPACE);
                param.setDocumentation("Number of the recepient");
                params[1] = param;

                param = new YParameter(null, YParameter._INPUT_PARAM_TYPE);
                param.setDataTypeAndName(XSD_STRINGTYPE, "message", XSD_NAMESPACE);
                param.setDocumentation("message to send");
                params[2] = param;
                return params;
        }

        private String getValueFromWorkItem(WorkItemRecord wir, String param) {
                String dataListString = wir.getDataListString();
                Element element = JDOMUtil.stringToElement(dataListString);
                String name = element.getChildTextNormalize(param);
                return name;
        }

        private void sendSmsUsingParams(WorkItemRecord enabledWorkItem) {
                String number = getValueFromWorkItem(enabledWorkItem, "number");
                String msg = getValueFromWorkItem(enabledWorkItem, "message");

                smsService.sendSMS(number, msg);
        }
}
