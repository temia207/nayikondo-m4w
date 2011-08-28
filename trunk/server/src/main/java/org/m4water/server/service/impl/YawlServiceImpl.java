package org.m4water.server.service.impl;

import org.m4water.server.admin.model.Ticket;
import org.m4water.server.service.YawlService;
import org.m4water.server.yawl.TicketYawlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author kay
 */
@Service("yawlService")
public class YawlServiceImpl implements YawlService {

        @Autowired
        TicketYawlService yawlService;
        private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(YawlServiceImpl.class);

        @Override
        public void launchWaterPointFlow(Ticket ticket) {
                try {
                        TicketYawlService.Params params = new TicketYawlService.Params();
                        params.addPumpMechanicName("mechanic");
                        params.setSenderNumber(ticket.getCreatorTel());
                        params.setMechanicNumber("0789388969");
                        params.setWaterPointID(ticket.getWaterpoint().getReferenceNumber());
                        params.setTicketMessage(ticket.getMessage());
                        yawlService.launchCase(params);
                } catch (Exception ex) {
                        log.error("Error occured while launching a ticket workflow", ex);
                        ex.printStackTrace();
                }
        }
}
