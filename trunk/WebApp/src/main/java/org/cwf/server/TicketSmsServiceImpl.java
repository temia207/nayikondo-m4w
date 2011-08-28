package org.cwf.server;

import java.util.List;
import org.cwf.client.service.TicketSmsService;
import org.cwf.server.rpc.M4waterPersistentRemoteService;
import org.m4water.server.admin.model.Ticket;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 *
 * @author victor
 */
public class TicketSmsServiceImpl extends M4waterPersistentRemoteService implements TicketSmsService {

    private org.m4water.server.service.TicketService ticketService;

    @Override
    public Ticket getTicket(String id) {
        return getTicketService().getTicket(id);
    }

    @Override
    public List<Ticket> getTickets() {
        return getTicketService().getTickets();
    }

    @Override
    public void saveTicket(Ticket ticket) {
        getTicketService().saveTicket(ticket);
    }

    @Override
    public void deleteTicket(Ticket ticket) {
        getTicketService().deleteTicket(ticket);
    }

    public org.m4water.server.service.TicketService getTicketService() {
        if (ticketService == null) {
            WebApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
            ticketService = (org.m4water.server.service.TicketService) ctx.getBean("ticketService");
        }
        return ticketService;
    }
}
