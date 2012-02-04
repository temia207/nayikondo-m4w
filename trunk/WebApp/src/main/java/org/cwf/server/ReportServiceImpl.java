/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cwf.server;

import java.util.List;
import org.cwf.client.service.ReportService;
import org.cwf.server.rpc.M4waterPersistentRemoteService;
import org.m4water.server.admin.model.reports.DistrictComparisons;
import org.m4water.server.admin.model.reports.ResponseTime;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 *
 * @author victor
 */
public class ReportServiceImpl extends M4waterPersistentRemoteService implements ReportService {

	private org.m4water.server.service.ReportsService reportsService;

	@Override
	public List<ResponseTime> getResponseTimes(String year, String district) {
		return getReportsService().getResponseTimes(year, district);
	}

	@Override
	public List<DistrictComparisons> getDistrictSummaries() {
		return getReportsService().getDistrictComparisons();
	}

	public org.m4water.server.service.ReportsService getReportsService() {
		if (reportsService == null) {
			WebApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
			reportsService = (org.m4water.server.service.ReportsService) ctx.getBean("reportService");
		}
		return reportsService;
	}
}
