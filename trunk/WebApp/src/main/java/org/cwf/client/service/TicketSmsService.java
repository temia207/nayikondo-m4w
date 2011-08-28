package org.cwf.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import java.util.List;
import org.m4water.server.admin.model.Ticket;

/**
 *
 * @author victor
 */
@RemoteServiceRelativePath("ticket")
public interface  TicketSmsService extends RemoteService{
    Ticket getTicket(String id);

    List<Ticket> getTickets();

    void saveTicket(Ticket ticket);

    void deleteTicket(Ticket ticket);
}
