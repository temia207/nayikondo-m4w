package org.m4water.server.yawl;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openxdata.yawl.util.InterfaceBHelper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.yawlfoundation.yawl.engine.interfce.WorkItemRecord;
import org.yawlfoundation.yawl.engine.interfce.interfaceB.InterfaceBWebsideController;
import org.yawlfoundation.yawl.exceptions.YAWLException;

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
}
