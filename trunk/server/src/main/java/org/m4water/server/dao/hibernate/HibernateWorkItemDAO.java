package org.m4water.server.dao.hibernate;

import com.googlecode.genericdao.dao.hibernate.GenericDAOImpl;
import com.googlecode.genericdao.search.Search;
import java.util.List;
import org.hibernate.SessionFactory;
import org.m4water.server.dao.WorkItemDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.yawlfoundation.yawl.engine.interfce.WorkItemRecord;


@Repository("workItemDAO")
public class HibernateWorkItemDAO extends GenericDAOImpl<WorkItemRecord, Integer>
	implements WorkItemDAO {

	@Autowired
	@Override
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Override
	public List<WorkItemRecord> searchByPropertyEqual(String property, Object value) {
		Search search = new Search();
		search.addFilterEqual(property, value);
		return search(search);
	}

	@Override
	public WorkItemRecord searchUniqueByPropertyEqual(String property, Object value) {
		Search search = new Search();
		search.addFilterEqual(property, value);
		return (WorkItemRecord) searchUnique(search);
	}

	@Override
	public List<WorkItemRecord> getEnableWorkItems() {
		return searchByPropertyEqual("_status", WorkItemRecord.statusExecuting);
	}

	@Override
	public WorkItemRecord getWir4CaseIDTaskID(String caseId) {
		return searchUniqueByPropertyEqual("_caseID", caseId);
	}

	public List<WorkItemRecord> getUproccesedWorkitems() {
		Search search = new Search();
		search.addFilterEqual("_status",WorkItemRecord.statusFailed);
		return search(search);
	}
}
