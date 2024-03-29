/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.m4water.server.dao;

import java.util.List;
import org.m4water.server.admin.model.reports.DistrictComparisons;
import org.m4water.server.admin.model.reports.WucManagement;

/**
 *
 * @author victor
 */
public interface ReportsDao extends BaseDAO<DistrictComparisons, Long> {

	List<DistrictComparisons> getDistrictComparisons();
	List<WucManagement> getWucManagementReport();
	String[] getRepairCosts(String district);
}
