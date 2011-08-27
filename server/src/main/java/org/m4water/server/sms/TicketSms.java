/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.m4water.server.sms;

import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.m4water.server.admin.model.Ticket;
import org.m4water.server.admin.model.Waterpoint;
import org.m4water.server.dao.TicketDao;
import org.m4water.server.dao.WaterPointDao;
import org.m4water.server.service.TicketService;
import org.muk.fcit.results.sms.Channel;
import org.muk.fcit.results.sms.RequestListener;
import org.muk.fcit.results.sms.SMSMessage;
import org.muk.fcit.results.sms.impl.ModemChannel;
import org.muk.fcit.results.sms.SMSServer;
import org.smslib.modem.ModemGateway;
import org.smslib.modem.SerialModemGateway;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

/**
 *
 * @author victor
 */
@Service("ticketService")
@Transactional
public class TicketSms implements TicketService, InitializingBean {

    @Autowired
    private WaterPointDao waterPointDao;
    @Autowired
    private TicketDao ticketDao;
    private TransactionTemplate transactionTemplate;
    @Autowired
    private PlatformTransactionManager transactionManager;
    @Autowired
    private SessionFactory session;

    public TicketSms() {
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        int numOfProcessors = 10;
        System.out.println("starting sms server");
        ModemGateway gateWay = new SerialModemGateway("modem.com1", "COM35", 460200, "Nokia", "6500c");
        this.transactionTemplate = new TransactionTemplate(transactionManager);
//        this.transactionTemplate.setReadOnly(true);

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {

            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                final Ticket ticket = new Ticket();
                ticket.setTickectId("123we");
                ticket.setCreatorTel("0714505033");
                ticket.setMessage("Borehole broken");
                final Waterpoint waterPoint = waterPointDao.getWaterPoint("UMASA0123");
                session.getCurrentSession().evict(waterPoint);
                System.out.println("-------------------"+waterPoint.getDistrict() + "........."+waterPoint.getId());
                ticket.setWaterpoint(waterPoint);
//                ticketDao.save(ticket);
                waterPoint.setTickets(new HashSet());
                waterPoint.getTickets().add(ticket);
                waterPoint.setDistrict("masaka");
//                waterPointDao.save(waterPoint);
                ticketDao.save(ticket);
            }
        });
        Channel ch = new ModemChannel(gateWay);
        // SMSServer s = new SMSServer(ch, new RequestListenerImpl(), numOfProcessors);

        SMSServer server = new SMSServer(ch, new RequestListener() {

            @Override
            public void processRequest(SMSMessage request) {
                System.out.println("new message " + request.getSmsData());
                request.getSmsData();
                String msg = request.getSmsData();
                String sourceId = msg.split(" ")[0];
                String complaint = msg.split(" ")[1];

                Ticket ticket = new Ticket();
                ticket.setCreatorTel(request.getSender());
                ticket.setMessage(complaint);
                ticket.setWaterpoint(waterPointDao.getWaterPoint(sourceId));
                ticketDao.save(ticket);


            }
        }, numOfProcessors);

        try {
            server.startServer();
        } catch (Exception ex) {
            Logger.getLogger(TicketSms.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Ticket getTicket(String id) {
        return ticketDao.getTicket(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Ticket> getTickets() {
        return ticketDao.getTickets();
    }

    @Override
    public void saveTicket(Ticket ticket) {
        ticketDao.save(ticket);
    }

    @Override
    public void deleteTicket(Ticket ticket) {
        ticketDao.deleteTicket(ticket);
    }
}
