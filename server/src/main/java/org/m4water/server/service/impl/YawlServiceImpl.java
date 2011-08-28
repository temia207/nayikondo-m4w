package org.m4water.server.service.impl;

import org.m4water.server.admin.model.Ticket;
import org.m4water.server.yawl.TicketYawlService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author kay
 */
public class YawlServiceImpl {

        @Autowired
        TicketYawlService yawlService;

        public void launchWaterPointFlow(Ticket ticket) {
               
        }
}
