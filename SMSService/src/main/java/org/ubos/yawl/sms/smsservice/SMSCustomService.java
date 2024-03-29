package org.ubos.yawl.sms.smsservice;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.ubos.yawl.sms.utils.Settings;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.apache.log4j.Logger;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.openxdata.yawl.util.InterfaceBHelper;
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
public class SMSCustomService extends InterfaceBWebsideController implements Provider<InterfaceBHelper> {

    public static final String NUMBER = "number";
    public static final String MESSAGE = "message";
    public static final String SENDER = "sender";
    public static final String NAME = "name";
    private static Logger log = Logger.getLogger(SMSCustomService.class);
        private String _sessionHandle = null;
        @Inject
        private UserService userService;
        @Inject
        private SMSService smsService;
        private InterfaceBHelper yHlp;
	private YawlPinger yawlPinger = null;

    public SMSCustomService() {
    }

	@Override
	public void setUpInterfaceBClient(String backEndURI) {
		super.setUpInterfaceBClient(backEndURI);
		yHlp = new InterfaceBHelper(this, _interfaceBClient, DEFAULT_ENGINE_USERNAME, DEFAULT_ENGINE_PASSWORD);
		yawlPinger = new YawlPinger();
		yawlPinger.setName("SMS YawlPinger");
		yawlPinger.start();
		yHlp.selfInitialiseHandle(true);
	}



        public static SMSCustomService getInstance() {
		 SMSCustomService instance = ServletConfig.injector.getInstance(SMSCustomService.class);
                return instance;
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
               try{
               String sender = getValueFromWorkItem(enabledWorkItem, SENDER);
               String userName = getValueFromWorkItem(enabledWorkItem, NAME);
               String phoneNo = userService.getPhoneNoByUsername(userName);
                if (phoneNo == null) {
                        sendSmsUsingParams(enabledWorkItem);
                } else {
                        smsService.sendSMS(phoneNo,sender, userName);
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
                param.setDataTypeAndName(XSD_STRINGTYPE, NAME, XSD_NAMESPACE);
                param.setDocumentation("name of recepient");
                params[0] = param;

                param = new YParameter(null, YParameter._INPUT_PARAM_TYPE);
                param.setDataTypeAndName(XSD_STRINGTYPE, NUMBER, XSD_NAMESPACE);
                param.setDocumentation("Number of the recepient");
                params[1] = param;

                param = new YParameter(null, YParameter._INPUT_PARAM_TYPE);
                param.setDataTypeAndName(XSD_STRINGTYPE, MESSAGE, XSD_NAMESPACE);
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
                String number = getValueFromWorkItem(enabledWorkItem, NUMBER);
                String msg = getValueFromWorkItem(enabledWorkItem, MESSAGE);
                String sender = getValueFromWorkItem(enabledWorkItem, SENDER);
    		    number = processNumbers(number);
                smsService.sendSMS(number,sender, msg);
        }

    String processNumbers(String number) {

        number = number + "";
        if (!number.contains(","))
            return add256(number);


        String[] numbers = number.split(",");

        String finalNumbers = "";

        for (int i = 0; i < numbers.length; i++) {
            String _256Number = add256(numbers[i]);
            if (i == 0)
                finalNumbers = _256Number;
            else
                finalNumbers = finalNumbers + "," + _256Number;
        }

        return finalNumbers;
    }

	public String add256(String number){
		number = (number+"").trim();
		if(number.startsWith("0")){
			return number.replaceFirst("0", "256");
		}else if(!number.startsWith("256") && number.length() <= 9){
			return "256"+number;
		}else {
			return number;
		}
	}

    public InterfaceBHelper get() {
        return yHlp;
    }

    public String getWorkitemName(WorkItemRecord wir){
	    if(wir == null)
		    return "null";
	    return "<"+wir.getID()+":"+wir.getCaseID()+">";
    }


	public class YawlPinger extends Thread {

		public void run() {
			while (true) {
				try {
					if (Settings.readSetting("ping.yawl") == null) {
						log.debug("Yawl Pinger Is Disaabled aborting ping");
						sleep();
						continue;
					}
					log.debug("@Pinging yawl for <TASKS> matching case smscustomservice.....");
					Set<String> yTasks = yHlp.getTaskSetWithCustomServiceMatch("smsservice");
					log.debug("@Got " + yTasks.size() + " Tasks");
					int enabledworkiems = 0;
					int executinng = 0;
					for (String taskId : yTasks) {
						log.debug("@Pinging Yawl for <WORKITEMS> for Tasks<"+taskId+">");
						List<WorkItemRecord> workItemsForTask = yHlp.getWorkItemsForTask(taskId);
						log.debug("@Got " + workItemsForTask.size() + " workitems");
						for (WorkItemRecord wir : workItemsForTask) {
							log.debug("@Handling workitem: " + wir.getID() + ":" + wir.getCaseID());
							if (wir.statusEnabled.equals(wir.getStatus())) {
								log.debug("@Handling enabled workitems event " + getWorkitemName(wir));
								log.debug("@Enabled workitem count: "+(++enabledworkiems));
								handleEnabledWorkItemEvent(wir);
							} else if (wir.statusExecuting.equals(wir.getStatus())) {
								log.debug("@Workitem " + getWorkitemName(wir) + " is in state <executing> going to check it in");
								log.debug("@Executing workitem count: "+(++executinng));
								_model.addWorkItem(wir);
								yHlp.checkInWorkItem(wir);
							}

						}
					}
				} catch (Exception ex) {
					log.error("Unexpected", ex);
				}
				sleep();
			}
		}

		public void sleep() {
			int time = Settings.readInt("sleepTime");
			sleep(time);
		}

		private void sleep(int i) {
			try {
				if(i<=0) {
					log.debug("Invalid slep time setting to default time. time = "+i);
					i = 5000;
				}
				log.debug("@Sleeping for "+i+" mili secs before pinging yawl again");
				Thread.currentThread().sleep(i);
			} catch (InterruptedException ex) {
				log.error("Unable to sleep for the specified time = "+i,ex);			}
		}
	}
}
