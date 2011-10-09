/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.m4water.server.sms;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.m4water.server.admin.model.Problem;
import org.m4water.server.admin.model.ProblemLog;
import org.m4water.server.admin.model.Smsmessagelog;
import org.m4water.server.admin.model.Waterpoint;
import org.m4water.server.dao.ProblemDao;
import org.m4water.server.dao.ProblemLogDao;
import org.m4water.server.dao.SmsMessageLogDao;
import org.m4water.server.dao.WaterPointDao;
import org.m4water.server.security.util.UUID;
import org.m4water.server.service.TicketService;
import org.m4water.server.service.YawlService;
import org.muk.fcit.results.sms.Channel;
import org.muk.fcit.results.sms.RequestListener;
import org.muk.fcit.results.sms.SMSMessage;
import org.muk.fcit.results.sms.SMSServer;
import org.muk.fcit.results.util.MLogger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionException;
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

    MLogger log = MLogger.getLogger();
    @Autowired
    private WaterPointDao waterPointDao;
    @Autowired
    private ProblemDao problemDao;
    private TransactionTemplate transactionTemplate;
    @Autowired
    private PlatformTransactionManager transactionManager;
    @Autowired
    private YawlService yawlService;
    @Autowired
    private SMSServiceImpl smsService;
    @Autowired
    private ProblemLogDao problemLogDao;
    @Autowired
    private SmsMessageLogDao messageLogDao;
    private final Set<String> receivedIds = new HashSet<String>();

    public TicketSms() {
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        transactionTemplate = new TransactionTemplate(transactionManager);
        RequestListener requestListener = new RequestListener() {

            @Override
            public void processRequest(SMSMessage request) {
                processSms(request);
            }
        };
        Channel ch = new TextMeUgChannel("m4w", "Trip77e");
        SMSServer server = new SMSServer(ch, requestListener, 10);
        System.out.println("Starting SMS server");
        server.startServer();
        System.out.println("Started SMS server");
    }

    private void createNewProblem(String complaint, Waterpoint waterPoint, String sender) throws HibernateException {
        Date date = new Date();

        Problem problem = new Problem();
        problem.setDateProblemReported(date);
        problem.setProblemDescsription(complaint);
        problem.setProblemStatus("open");
        problem.setWaterpoint(waterPoint);
        problem.setProblemLogs(new HashSet());

        waterPoint.setProblems(new HashSet());
        waterPoint.getProblems().add(problem);

        ProblemLog problemLog = new ProblemLog(UUID.jUuid(), problem, sender, date, waterPoint.getWaterpointId());
        problem.getProblemLogs().add(problemLog);
        launchCase(problem);
        saveProblem(problem);
        
    }

    private void processSms(final SMSMessage request) throws TransactionException {
        System.out.println("new message " + request.getSmsData());
        if (!isMessageNew(request, false)) {
            return;
        }

        String msg = cleanSms(request);
        String[] split = msg.split(" ");
        final String sourceId = split[0];
        String tempComplaint = "";
        try {
            tempComplaint = msg.substring(sourceId.length());
        } catch (IndexOutOfBoundsException ex) {
        }
        final String complaint = tempComplaint;
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {

            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                try {

                    if (!isMessageNew(request, true)) {
                        return;
                    }

                    saveAndCacheMessage(request);

                    if (complaint.isEmpty()) {
                        smsService.sendSMS(request.getSender(), "No problem Reported Please send again with the problem in the format: ID space problem");
                    } else {
                        processMessage(sourceId, complaint, request.getSender());
                    }

                } catch (Throwable x) {
                    smsService.sendSMS(request.getSender(), "Server is temporarily down try again later");
                    x.printStackTrace();
                }
            }
        });
    }

    public void processMessage(String sourceId, String complaint, String sender) throws HibernateException {
        Waterpoint waterPoint = waterPointDao.getWaterPoint(sourceId);
        if (waterPoint == null) {
            smsService.sendSMS(sender, "Waterpoint ID(" + sourceId + ") does not exist. Please send again with correct ID");
        } else if (validWaterPointID(sourceId)) {
            smsService.sendSMS(sender, "Invalid Waterpoint ID(" + sourceId + ") Format. Form should be similar to 521PZ001 with 8 characters");
        } else if (waterPoint.hasOpenProblems()) {
            Problem problem = waterPoint.getOpenProblem();
            ProblemLog problemLog = new ProblemLog(UUID.jUuid(), problem, sender, new Date(), complaint);
            problem.getProblemLogs().add(problemLog);
            problemLogDao.save(problemLog);
            smsService.sendSMS(sender, "Waterpoint(" + sourceId + ") problem has already been reported");
        } else {
            createNewProblem(complaint, waterPoint, sender);
        }
    }

    public void saveAndCacheMessage(SMSMessage request) {
        Object msgId = request.get("msgID");
        if (msgId != null) {
            synchronized (receivedIds) {
                receivedIds.add(msgId.toString());
            }
            saveNewMessageToDb(request);
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
    public List<Problem> getProblemHistory(Waterpoint waterPointId) {
        return problemDao.getProblemHistory(waterPointId);
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

    public boolean isMessageNew(SMSMessage message, boolean loadFromDb) {
        if (loadFromDb) {
            mayBeLoadMsgIds();
        }
        Object msgId = message.get("msgID");
        if (msgId == null) {
            return true;
        }
        return !receivedIds.contains(msgId + "");
    }

    public void setMessageLogDao(SmsMessageLogDao messageLogDao) {
        this.messageLogDao = messageLogDao;
    }

    public void setProblemDao(ProblemDao problemDao) {
        this.problemDao = problemDao;
    }

    public void setSmsService(SMSServiceImpl smsService) {
        this.smsService = smsService;
    }

    public void setTransactionManager(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
        this.transactionTemplate = transactionTemplate;
    }

    public void setWaterPointDao(WaterPointDao waterPointDao) {
        this.waterPointDao = waterPointDao;
    }

    public void setYawlService(YawlService yawlService) {
        this.yawlService = yawlService;
    }

    public synchronized void mayBeLoadMsgIds() {

        if (!receivedIds.isEmpty()) {
            return;
        }
        List<Smsmessagelog> findAll = messageLogDao.findAll();
        for (Smsmessagelog smsmessagelog : findAll) {
            receivedIds.add(smsmessagelog.getTextmeId());
        }
    }

    public String cleanSms(final SMSMessage request) {
        String msg = request.getSmsData();
        msg = StringUtils.trimToEmpty(msg);
        msg = msg.replaceAll("\\s+", " ");
        if (msg.toLowerCase().startsWith("m4w")) {
            msg = msg.substring(4);
        }
        return msg;
    }

    private void saveNewMessageToDb(SMSMessage request) {
        log.info("Saving new message from: " + request.getSender() + " Msg: " + request.getSmsData(), null, null);
        messageLogDao.save(new Smsmessagelog(java.util.UUID.randomUUID().toString(), request.get("msgID") + "", request.getSender(), request.get("time") + "", request.getSmsData()));
    }

    private boolean validWaterPointID(String sourceId) {
        return sourceId != null
                && "[0-9]{3}[a-zA-Z]{2}[0-9]{3}".matches(sourceId);
    }
}
