/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.m4water.server.dao;

import java.util.List;
import org.m4water.server.admin.model.Ticket;

/**
 *
 * @author victor
 */
public interface TicketDao extends BaseDAO<Ticket> {

    List<Ticket> getTickets();

    Ticket getTicket(String referenceNumber);

    void deleteTicket(Ticket ticket);
    void saveTicket(Ticket ticket);
}
