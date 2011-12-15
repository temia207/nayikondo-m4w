package org.cwf.server;

import org.cwf.client.service.YawlService;
import org.cwf.server.rpc.M4waterPersistentRemoteService;
import org.m4water.server.admin.model.Waterpoint;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 *
 * @author victor
 */
public class YawlServiceImpl extends M4waterPersistentRemoteService implements YawlService {

    private org.m4water.server.service.YawlService yawlService;

    @Override
    public void launchWaterPointBaseline(Waterpoint waterpoint) {
        getYawlService().launchWaterPointBaseline(waterpoint);
    }

    @Override
    public void launchWaterPointBaseline(String waterpointId) {
        getYawlService().launchWaterPointBaseline(waterpointId);
    }

    public org.m4water.server.service.YawlService getYawlService() {
        if (yawlService == null) {
            WebApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
            yawlService = (org.m4water.server.service.YawlService) ctx.getBean("yawlService");
        }
        return yawlService;
    }

    @Override
    public void cancelProblem(int problemID) {
	yawlService.cancelProblem(problemID);
    }

    @Override
    public void cancelCase(String caseID) {
	yawlService.cancelCase(caseID);
    }
}
