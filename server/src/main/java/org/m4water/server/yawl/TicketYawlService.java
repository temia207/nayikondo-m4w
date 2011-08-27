package org.m4water.server.yawl;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.yawlfoundation.yawl.engine.interfce.WorkItemRecord;
import org.yawlfoundation.yawl.engine.interfce.interfaceB.InterfaceBWebsideController;

/**
 *
 * @author kay
 */
@Component("yawlTicketService")
public class TicketYawlService extends InterfaceBWebsideController implements InitializingBean {

        private static TicketYawlService ticketYawlService;

        public static TicketYawlService getInstance() {
                return ticketYawlService;
        }

        @Override
        public void handleEnabledWorkItemEvent(WorkItemRecord enabledWorkItem) {
        }

        @Override
        public void handleCancelledWorkItemEvent(WorkItemRecord workItemRecord) {
        }

        @Override
        public void afterPropertiesSet() throws Exception {
                System.out.println("Initiations the yawl custom service");
                ticketYawlService = this;
        }
}
