package org.m4water.server.dao;

import com.googlecode.genericdao.dao.hibernate.GenericDAO;
import java.util.List;

import org.yawlfoundation.yawl.engine.interfce.WorkItemRecord;

/**
 *
 * @author kay
 */
public interface WorkItemDAO extends
        GenericDAO<WorkItemRecord, Integer>
{

        public List<WorkItemRecord> searchByPropertyEqual(String property, Object value);

        public WorkItemRecord searchUniqueByPropertyEqual(String property, Object value);

        public List<WorkItemRecord> getEnableWorkItems();

        public WorkItemRecord getWir4CaseIDTaskID(String caseId);

}
