/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.m4water.server.service;

import java.util.List;
import org.m4water.server.admin.model.reports.DistrictComparisons;
import org.m4water.server.admin.model.reports.ResponseTime;
import org.m4water.server.admin.model.reports.WucManagement;

/**
 *
 * @author victor
 */
public interface ReportsService {

	 List<ResponseTime> getResponseTimes(String year,String district);
	 List<DistrictComparisons> getDistrictComparisons();
	 List<WucManagement> getWucManagementReport();
	 String []getRepairCosts(String district);
}
