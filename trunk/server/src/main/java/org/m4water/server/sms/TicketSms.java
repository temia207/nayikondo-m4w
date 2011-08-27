/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.m4water.server.sms;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.m4water.server.admin.model.Ticket;
import org.m4water.server.dao.WaterPointDao;
import org.muk.fcit.results.sms.Channel;
import org.muk.fcit.results.sms.RequestListener;
import org.muk.fcit.results.sms.SMSMessage;
import org.muk.fcit.results.sms.impl.ModemChannel;
import org.muk.fcit.results.sms.SMSServer;
import org.smslib.modem.ModemGateway;
import org.smslib.modem.SerialModemGateway;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author victor
 */
public class TicketSms implements InitializingBean {

    @Autowired
    private WaterPointDao waterPointDao;

    public TicketSms() {
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        int numOfProcessors = 10;
        System.out.println("starting sms server");
        ModemGateway gateWay = new SerialModemGateway("modem.com1", "COM35", 460200, "Nokia", "6500c");

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

            }
        }, numOfProcessors);

        try {
            server.startServer();
        } catch (Exception ex) {
            Logger.getLogger(TicketSms.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
