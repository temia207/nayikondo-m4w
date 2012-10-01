package org.m4water.server.servlet;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.HibernateException;
import org.m4water.server.admin.model.Problem;
import org.m4water.server.admin.model.ProblemLog;
import org.m4water.server.admin.model.User;
import org.m4water.server.admin.model.Waterpoint;
import org.m4water.server.admin.model.exception.M4waterCaseLauchException;
import org.m4water.server.admin.model.exception.M4waterRuntimeException;
import org.m4water.server.admin.model.exception.M4waterYawlDownException;
import org.m4water.server.admin.model.exception.UserNotFoundException;
import org.m4water.server.security.util.M4wUtil;
import org.m4water.server.security.util.UUID;
import org.m4water.server.service.TicketService;
import org.m4water.server.service.UserService;
import org.m4water.server.service.WaterPointService;
import org.m4water.server.service.YawlService;
import org.m4water.server.sms.SMSServiceImpl;
import org.muk.fcit.results.sms.SMSMessage;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 *
 * @author kay
 */
public class TicketsServlet extends HttpServlet {

	private static org.slf4j.Logger log = LoggerFactory.getLogger(TicketsServlet.class);
	@Autowired
	private UserService userService;
	@Autowired
	private WaterPointService waterPointService;
	@Autowired
	private YawlService yawlService;
	@Autowired
	private SMSServiceImpl smsService;
	@Autowired
	private TicketService ticketSms;
	//private Settings s;

	@Override
	public void init() throws ServletException {

		WebApplicationContext appCtx = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
		appCtx.getAutowireCapableBeanFactory().autowireBean(this);
//		super.init();
//		try {
//		//	s = new Settings("problems");
//		} catch (URISyntaxException ex) {
//			log.error("Error whiile inititing setting file", ex);
//			throw new ServletException(ex);
//		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doGet(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String waterpointId = req.getParameter("waterpointId");
		String waterpointProblem = req.getParameter("problem");
		String userName = req.getParameter("username");

		User user = null;
		try {
			user = userService.findUserByUsername(userName);
		} catch (UserNotFoundException ex) {
			log.warn("Usename [" + userName + "] From OXD was not found in local database" + ex);
		}

		String contact = user != null ? (user.getContacts()+"").replace("-", "").replaceFirst("0", "256") : "OXD:" + userName;

		SMSMessage msg = new SMSMessage(contact, "m4w " + waterpointId + " " + waterpointProblem, null);
		msg.put("msgID", "OXD:" + userName + ":" + waterpointId);
		msg.put("time", new Date().toString());

		try {
			processSms(waterpointId, waterpointProblem, contact);
			writeMessage(resp, "<success>Success</Success>");
		} catch (Exception e) {
			log.error("Error while processing ticket in servlet", e);
			writeMessage(resp, "<fail>" + e.getMessage() + "</fail>");
		}


	}

	private void writeMessage(HttpServletResponse resp, String msg) throws IOException {
		resp.getWriter().print(msg);
		resp.getWriter().flush();
	}

	public void processMessage(String sourceId, String complaint, String sender) throws HibernateException {

		Waterpoint waterPoint = waterPointService.getWaterPoint(sourceId);
		if (waterPoint == null && !M4wUtil.validWaterPointID(sourceId)) {
			throw new M4waterRuntimeException("Invalid Waterpoint ID(" + sourceId + ") Format. Form should be similar to 521BH001 with 8 characters");
		} else if (waterPoint == null) {
			throw new M4waterRuntimeException("Waterpoint ID(" + sourceId + ") does not exist. Please send again with correct ID");
		} else if (waterPoint.hasOpenProblems()) {
			Problem problem = waterPoint.getOpenProblem();
			ProblemLog problemLog = new ProblemLog(UUID.jUuid(), problem, sender, new Date(), complaint);
			problem.getProblemLogs().add(problemLog);
			ticketSms.saveProblemLog(problemLog);
			throw new M4waterRuntimeException("Waterpoint(" + sourceId + ") problem has already been reported");
		} else {
			createNewProblem(complaint, waterPoint, sender);
		}
	}

	private void createNewProblem(String complaint, Waterpoint waterPoint, String sender) throws HibernateException {
		Date date = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(date);

		Problem problem = new Problem();
		problem.setDateProblemReported(date);
		problem.setProblemDescsription(complaint);
		problem.setProblemStatus("open");
		problem.setWaterpoint(waterPoint);

		waterPoint.getProblems().add(problem);

		ProblemLog problemLog = new ProblemLog(UUID.jUuid(), problem, sender, date, complaint);
		problem.getProblemLogs().add(problemLog);
		String caseID = null;
		try {

			caseID = launchCase(problem);
			problem.setYawlid("T" + caseID + "-" + c.get(Calendar.YEAR));
			ticketSms.saveProblem(problem);
		} catch (M4waterYawlDownException ex) {
			sendErrorNotification(sender, complaint, ex);
			//msg.put("status", "M4waterYawlDownException:" + ex.getMessage());
			log.error("Error while launching ticket: Yawl Seems to be down", ex);
		} catch (M4waterCaseLauchException ex) {
			sendErrorNotification(sender, complaint, ex);
			//msg.put("status", "M4waterCaseLauchException:" + ex.getMessage());
			log.error("Error while launching ticket: Yawl Seems to have rejected the case params" + ex.getMessage(), ex);
		} catch (Exception e) {
			sendErrorNotification(sender, complaint, e);
			//msg.put("status", "Exception:" + e.getMessage());
			log.error("Unxpected error occurd while launching ticket", e);
		}


	}

	private void sendErrorNotification(String sender, String complaint, Exception ex) {
		try {
			smsService.sendSMS("256712075579", "Yawl is down: Problem: " + complaint);
//			s.store(UUID.jUuid(), "sender:" + sender + " complaint:" + complaint + "ex:" + ex);
			log.info("TICKET:UUID.jUuid() sender:" + sender + " complaint:" + complaint + "ex:" + ex);
		} catch (Exception e) {
			log.error("Error while sending notification to admin:", e);
		}
	}

	private void processSms(String waterpoint, String complaint, String sender) {
	
			if (complaint.isEmpty()) {
				throw new M4waterRuntimeException("No problem Reported Please send again with the problem");
			} else {
				processMessage(waterpoint, complaint, sender);
			}

		
	}

	public String launchCase(Problem problem) throws M4waterYawlDownException, M4waterCaseLauchException {

		return yawlService.launchWaterPointFlow(problem);
	}
}
