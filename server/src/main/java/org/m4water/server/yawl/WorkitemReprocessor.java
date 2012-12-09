package org.m4water.server.yawl;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import org.jdom.JDOMException;
import org.m4water.server.dao.WorkItemDAO;
import org.openxdata.yawl.util.Settings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.yawlfoundation.yawl.engine.interfce.WorkItemRecord;

/**
 *
 * @author kay
 */
@Component("workitemReprocessor")
public class WorkitemReprocessor implements Runnable {

	private static Logger log = LoggerFactory.getLogger(WorkitemReprocessor.class);
	@Autowired
	private WorkItemDAO workItemDAO;
	@Autowired
	private TicketYawlService ticketYawlService;
	@Autowired
	private PlatformTransactionManager txManager;
	private TransactionTemplate txTemplate;

	public void run() {
		try {
			log.debug("sleeping for 5 secs before workitem reprocessing");
			Thread.sleep(TimeUnit.SECONDS.toMillis(5));
		} catch (InterruptedException ex) {
		}

		String reprocessFlag = Settings.readSetting("reprocess.workitems");

		if (!Boolean.valueOf(reprocessFlag)) {
			log.info("Workitem reprocessing disbaled... returning");
			return;
		}


		getTxTemplate().execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				List<WorkItemRecord> workitems = workItemDAO.getUproccesedWorkitems();
				for (final WorkItemRecord workitem : workitems) {
					
					log.debug("processing workitems: " + workitem.getID());
					try {
						//Thread.sleep(TimeUnit.SECONDS.toMillis(4));
						ticketYawlService.processWorkitem(workitem);
					} catch (Exception ex) {
						log.error("Error occured while reprocessing workitems: " + workitem.getID(), ex);
					}
				}
			}
		});
	}

	private TransactionTemplate getTxTemplate() {
		if (txTemplate == null) {
			txTemplate = new TransactionTemplate(txManager);
		}
		return txTemplate;
	}
}
