package org.m4water.server.yawl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.m4water.server.admin.model.exception.M4waterException;
import org.openxdata.yawl.util.InterfaceBHelper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
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

        public static TicketYawlService getInstance() {
                return ticketYawlService;
        }

        @Override
        public void handleEnabledWorkItemEvent(WorkItemRecord enabledWorkItem) {
                try {
                        yawlHelper.initSessionHandle();
                        List<WorkItemRecord> workitems = yawlHelper.checkOutWorkitemAndChildren(enabledWorkItem);


                } catch (YAWLException ex) {
                        Logger.getLogger(TicketYawlService.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                        Logger.getLogger(TicketYawlService.class.getName()).log(Level.SEVERE, null, ex);
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
                String launchCase = _interfaceBClient.launchCase(new YSpecificationID("WaterFlow", "0.35"), params.asXML(), yawlHelper.initSessionHandle());
                boolean successful = successful(launchCase);
                if(!successful)
                        throw  new YAWLException(launchCase);
        }

        public static class Params {

                HashMap<String, String> params = new HashMap<String, String>();

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
