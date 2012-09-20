package org.m4water.server.service;

import java.util.List;

import org.yawlfoundation.yawl.engine.interfce.WorkItemRecord;

public interface WorkItemsService 
{

        public List<WorkItemRecord> getEnableWorkItems();

        public WorkItemRecord getWorkitem(int wirId);
	
	public WorkItemRecord getWorkitem(String caseId);
	
	public void saveWorkitem(WorkItemRecord wir);
}
