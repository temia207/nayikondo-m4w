package org.m4water.server.admin.model.mapping;

import org.m4water.server.admin.model.AbstractEditable;
import org.m4water.server.admin.model.Report;
import org.m4water.server.admin.model.User;

/**
 * Maps <code>Reports</code> to <code>User</code>.
 */
public class UserReportMap extends AbstractEditable {

	private int userId;
	private int reportId;
	private static final long serialVersionUID = 4321925710032960853L;

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getUserId() {
		return userId;
	}

	public void setReportId(int reportId) {
		this.reportId = reportId;
	}

	public int getReportId() {
		return reportId;
	}
	
	/**
	 * Adds the specified <code>User</code> to the Map.
	 * @param user <code>User</code> to remove.
	 */
	public void addUser(User user){
		setUserId(user.getId());
	}
	
	/**
	 * Removes the specified <code>User</code> from the Map.
	 * @param user <code>User</code> to remove.
	 */
	public void removeUser(User user){
		setUserId(user.getId());
	}
	
	/**
	 * Adds the specified <code>Report</code> to the Map.
	 * @param report <code>Report</code> to remove.
	 */
	public void addReport(Report report){
		setReportId(report.getId());
	}
	
   /**
     * Removes the specified <code>Report</code> from the Map.
     * @param report <code>Report</code> to remove.
     */
    public void removeReport(Report report) {
        setReportId(report.getId());
    }
}
