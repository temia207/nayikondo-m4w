/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.m4water.server.sms;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.SessionFactory;
import org.m4water.server.admin.model.Ticket;
import org.m4water.server.admin.model.Waterpoint;
import org.m4water.server.dao.TicketDao;
import org.m4water.server.dao.WaterPointDao;
import org.m4water.server.service.TicketService;
import org.m4water.server.service.YawlService;
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
    @Autowired
    private YawlService yawlService;

    public TicketSms() {
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        int numOfProcessors = 10;
        System.out.println("starting sms server");
        ModemGateway gateWay = new SerialModemGateway("modem.com1", "COM30", 460200, "Nokia", "6500c");
transactionTemplate = new TransactionTemplate(transactionManager);
        Channel ch = new ModemChannel(gateWay);
        // SMSServer s = new SMSServer(ch, new RequestListenerImpl(), numOfProcessors);

        SMSServer server = new SMSServer(ch, new RequestListener() {

            @Override
            public void processRequest(final SMSMessage request) {
                System.out.println("new message " + request.getSmsData());
                request.getSmsData();
                String msg = request.getSmsData();
                final String sourceId = msg.split(" ")[0];
                final String complaint =msg.substring(sourceId.length());
                
                transactionTemplate.execute(new TransactionCallbackWithoutResult() {

                    @Override
                    protected void doInTransactionWithoutResult(TransactionStatus status) {
                        final Ticket ticket = new Ticket();
                        ticket.setTickectId(org.m4water.server.security.util.UUID.uuid());
                        ticket.setCreatorTel(request.getSender());
                        ticket.setMessage(complaint);
                        // final Waterpoint waterPoint = waterPointDao.getWaterPoint("UMASA0123");
                        final Waterpoint waterPoint = waterPointDao.getWaterPoint(sourceId);
                        session.getCurrentSession().evict(waterPoint);
                        ticket.setWaterpoint(waterPoint);
                        waterPoint.setTickets(new HashSet());
                        waterPoint.getTickets().add(ticket);
                        ticketDao.save(ticket);
                        launchCase(ticket);
                    }
                });


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

    public void launchCase(Ticket ticket){
            yawlService.launchWaterPointFlow(ticket);
    }
}
