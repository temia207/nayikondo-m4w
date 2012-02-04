/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cwf.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import java.util.List;
import org.m4water.server.admin.model.reports.DistrictComparisons;
import org.m4water.server.admin.model.reports.ResponseTime;

/**
 *
 * @author victor
 */
@RemoteServiceRelativePath("reportService")
public interface ReportService extends RemoteService {

	List<ResponseTime> getResponseTimes(String year, String district);
	List<DistrictComparisons> getDistrictSummaries();
}
