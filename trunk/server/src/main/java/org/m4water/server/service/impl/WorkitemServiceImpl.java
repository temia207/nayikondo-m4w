package org.m4water.server.service.impl;

import java.util.List;
import org.m4water.server.dao.WorkItemDAO;
import org.m4water.server.service.WorkItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yawlfoundation.yawl.engine.interfce.WorkItemRecord;

@Service("workitemService")
@Transactional
public class WorkitemServiceImpl implements WorkItemsService {

	@Autowired
	private WorkItemDAO workItemDAO;
	
	public List<WorkItemRecord> getEnableWorkItems() {
		return workItemDAO.findAll();
	}

	public WorkItemRecord getWorkitem(int wirId) {
		return workItemDAO.find(wirId);
	}

	public WorkItemRecord getWorkitem(String caseId) {
		return workItemDAO.getWir4CaseIDTaskID(caseId);
	}

	public void saveWorkitem(WorkItemRecord wir) {
		workItemDAO.save(wir);
	}
	
}
