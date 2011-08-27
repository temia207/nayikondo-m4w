/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.m4water.server.dao.hibernate;
import com.googlecode.genericdao.search.Search;
import java.util.List;
import org.m4water.server.admin.model.Ticket;
import org.m4water.server.dao.TicketDao;
import org.springframework.stereotype.Repository;
/**
 *
 * @author victor
 */
@Repository("Ticket")
public class HibernateTicketDao extends BaseDAOImpl<Ticket> implements TicketDao{

    @Override
    public List<Ticket> getTickets() {
        Search s = new Search();
		s.addSort("id", false);
        return search(s);
    }

    @Override
    public Ticket getTicket(String referenceNumber) {
        return searchUniqueByPropertyEqual("referenceNumber", referenceNumber);
    }

    @Override
    public void deleteTicket(Ticket ticket) {
        remove(ticket);
    }

    @Override
    public void saveTicket(Ticket ticket) {
        save(ticket);
    }

}
