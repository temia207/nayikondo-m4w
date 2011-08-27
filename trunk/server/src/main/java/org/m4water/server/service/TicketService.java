package org.m4water.server.service;

import java.util.List;
import org.m4water.server.admin.model.Ticket;

/**
 *
 * @author victor
 */
public interface TicketService {

    Ticket getTicket(String id);

    List<Ticket> getTickets();

    void saveTicket(Ticket ticket);

    void deleteTicket(Ticket ticket);
}
