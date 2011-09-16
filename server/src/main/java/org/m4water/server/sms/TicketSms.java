/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.m4water.server.sms;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang.StringUtils;
import org.hibernate.SessionFactory;
import org.m4water.server.admin.model.Problem;
import org.m4water.server.admin.model.ProblemLog;
import org.m4water.server.admin.model.Smsmessagelog;
import org.m4water.server.admin.model.Waterpoint;
import org.m4water.server.dao.ProblemDao;
import org.m4water.server.dao.SmsMessageLogDao;
import org.m4water.server.dao.WaterPointDao;
import org.m4water.server.service.TicketService;
import org.m4water.server.service.YawlService;
import org.muk.fcit.results.sms.Channel;
import org.muk.fcit.results.sms.RequestListener;
import org.muk.fcit.results.sms.SMSMessage;
import org.muk.fcit.results.sms.SMSServer;
import org.muk.fcit.results.sms.impl.TextMeUgChannel;
import org.muk.fcit.results.util.MLogger;
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

        MLogger log = new MLogger().getLogger();
        @Autowired
        private WaterPointDao waterPointDao;
        @Autowired
        private ProblemDao problemDao;
        private TransactionTemplate transactionTemplate;
        @Autowired
        private PlatformTransactionManager transactionManager;
        @Autowired
        private SessionFactory session;
        @Autowired
        private YawlService yawlService;
        @Autowired
        private SMSServiceImpl smsService;
        @Autowired
        private SmsMessageLogDao messageLogDao;
        private Set<String> receivedIds = new HashSet<String>();

        public TicketSms() {
        }

        @Override
        public void afterPropertiesSet() throws Exception {
                int numOfProcessors = 10;
                System.out.println("starting sms server");

                //ModemGateway gateWay = new SerialModemGateway("modem.com1", "COM21", 460200, "Nokia", "6500c");

                transactionTemplate = new TransactionTemplate(transactionManager);
                Channel ch = new TextMeUgChannel("m4w", "Trip77e");
                // SMSServer s = new SMSServer(ch, new RequestListenerImpl(), numOfProcessors);
                SMSServer server = new SMSServer(ch, new RequestListener() {

                        @Override
                        public void processRequest(final SMSMessage request) {
                                System.out.println("new message " + request.getSmsData());
                                if(!isMessageNew(request, false)){
                                        receivedIds.add(request.get("msgID").toString());
                                        return;
                                }
                                request.getSmsData();
                                String msg = cleanSms(request);
                                final String[] split = msg.split(" ");
                                final String sourceId = split[0];
                                String tempComplaint = "";
                                try{
                                tempComplaint = msg.substring(sourceId.length());
                                }catch(IndexOutOfBoundsException ex){
                                }
                                final String complaint = tempComplaint;

                                transactionTemplate.execute(new TransactionCallbackWithoutResult() {

                                        @Override
                                        protected void doInTransactionWithoutResult(TransactionStatus status) {
                                                try {
                                                        if(!isMessageNew(request,true)){
                                                                receivedIds.add(request.get("msgID").toString());
                                                                saveNewMessageToDb(request);
                                                                return;
                                                        }
                                                        final Problem problem = new Problem();
                                                        Date date = new Date();
                                                        problem.setDateProblemReported(date);
                                                        problem.setProblemDescsription(complaint);
                                                        problem.setProblemStatus("F");
                                                        // final Waterpoint waterPoint = waterPointDao.getWaterPoint("UMASA0123");
                                                        final Waterpoint waterPoint = waterPointDao.getWaterPoint(sourceId);
                                                        if (waterPoint == null) {
                                                                smsService.sendSMS(request.getSender(), "Water Point Id Not Found");
                                                                return;
                                                        }
                                                        session.getCurrentSession().evict(waterPoint);
                                                        problem.setWaterpoint(waterPoint);
                                                        waterPoint.setProblems(new HashSet());
                                                        waterPoint.getProblems().add(problem);

                                                        problem.setProblemLogs(new HashSet() {

                                                                {
                                                                        add(new ProblemLog(0, problem, request.getSender(), new Date(), waterPoint.getWaterpointId()));
                                                                }
                                                        });

                                                        problemDao.save(problem);

                                                        launchCase(problem);
                                                } catch (Throwable x) {
                                                        smsService.sendSMS(request.getSender(), "Invalid Message send message in Format: "
                                                                + "<waterpointid> <message>");
                                                }
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
                return problemDao.getProblem(id);
        }

        @Override
        @Transactional(readOnly = true)
        public List<Problem> getProblems() {
                return problemDao.getProblems();
        }

        @Override
        public void saveProblem(Problem problem) {
                problemDao.save(problem);
        }

        @Override
        public void deleteProblem(Problem problem) {
                problemDao.deleteProblem(problem);
        }

        public void launchCase(Problem problem) {

                yawlService.launchWaterPointFlow(problem);
        }

        private boolean isMessageNew(SMSMessage message, boolean loadFromDb) {
                if(loadFromDb)
                        mayLoadMsgIds();

                        return !receivedIds.contains(message.get("msgID")+"");
        }

        private void mayLoadMsgIds() {
                if (!receivedIds.isEmpty()) {
                        return;
                }
                List<Smsmessagelog> findAll = messageLogDao.findAll();
                for (Smsmessagelog smsmessagelog : findAll) {
                        receivedIds.add(smsmessagelog.getTextmeId());
                }
                return;
        }

        private String cleanSms(final SMSMessage request) {
                String msg = request.getSmsData();
                msg = StringUtils.trimToEmpty(msg);
                msg = msg.replaceAll("\\s+", " ");
                if (msg.toLowerCase().startsWith("m4w")) {
                        msg = msg.substring(5);
                }
                return msg;
        }

        private void saveNewMessageToDb(SMSMessage request) {
                log.info("Saving new message from: "+request.getRecepient()+ " Msg: "+request.getSmsData(), null, null);
                messageLogDao.save(new Smsmessagelog(java.util.UUID.randomUUID().toString(), request.get("msgID").toString(), request.getSender(), request.get("time") + "", request.getSender()));
        }
}
