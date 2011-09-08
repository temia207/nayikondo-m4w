/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.m4water.server.sms;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.SessionFactory;
import org.m4water.server.admin.model.Problem;
import org.m4water.server.admin.model.Waterpoint;
import org.m4water.server.dao.ProblemDao;
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
    private ProblemDao ticketDao;
    private TransactionTemplate transactionTemplate;
    @Autowired
    private PlatformTransactionManager transactionManager;
    @Autowired
    private SessionFactory session;
    @Autowired
    private YawlService yawlService;
    @Autowired
    private SMSServiceImpl smsService;

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
                final String complaint = msg.substring(sourceId.length());

                transactionTemplate.execute(new TransactionCallbackWithoutResult() {

                    @Override
                    protected void doInTransactionWithoutResult(TransactionStatus status) {
                        final Problem problem = new Problem();
                        Date date = new Date();
                        problem.setDateProblemReported(date);
                        problem.setProblemDescsription(complaint);
                        problem.setProblemStatus("F");
                        // final Waterpoint waterPoint = waterPointDao.getWaterPoint("UMASA0123");
                        final Waterpoint waterPoint = waterPointDao.getWaterPoint(sourceId);
                        session.getCurrentSession().evict(waterPoint);
                        problem.setWaterpoint(waterPoint);
                        waterPoint.setProblems(new HashSet());
                        waterPoint.getProblems().add(problem);
                        ticketDao.save(problem);
                        launchCase(problem);
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
    public Problem getProblem(int id) {
        return ticketDao.getProblem(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Problem> getProblems() {
        return ticketDao.getProblems();
    }

    @Override
    public void saveProblem(Problem problem) {
        ticketDao.save(problem);
    }

    @Override
    public void deleteProblem(Problem problem) {
        ticketDao.deleteProblem(problem);
    }

    public void launchCase(Problem problem) {
        yawlService.launchWaterPointFlow(problem);
    }
}
