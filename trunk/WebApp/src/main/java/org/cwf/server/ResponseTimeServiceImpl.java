/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cwf.server;

import java.util.List;
import org.cwf.client.service.ResponseTimeService;
import org.cwf.server.rpc.M4waterPersistentRemoteService;
import org.m4water.server.admin.model.reports.ResponseTime;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 *
 * @author victor
 */
public class ResponseTimeServiceImpl extends M4waterPersistentRemoteService implements ResponseTimeService {

	private org.m4water.server.service.ResponseTimeService responseTimeService;

	@Override
	public List<ResponseTime> getResponseTimes(String year, String district) {
		return getResponseTimeService().getResponseTimes(year, district);
	}

	public org.m4water.server.service.ResponseTimeService getResponseTimeService() {
		if (responseTimeService == null) {
			WebApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
			responseTimeService = (org.m4water.server.service.ResponseTimeService) ctx.getBean("responseTimeService");
		}
		return responseTimeService;
	}
}
