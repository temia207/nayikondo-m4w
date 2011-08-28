
package org.cwf.server;

import java.util.List;
import org.cwf.client.service.WaterPointService;
import org.cwf.server.rpc.M4waterPersistentRemoteService;
import org.m4water.server.admin.model.Waterpoint;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 *
 * @author victor
 */
public class WaterPointServiceImpl extends M4waterPersistentRemoteService implements WaterPointService {

    private org.m4water.server.service.WaterPointService waterPointService;
    @Override


    public List<Waterpoint> getWaterPoints() {
        return getWaterPointService().getWaterPoints();
    }

    @Override
    public Waterpoint getWaterPoint(String referenceNumber) {
        return getWaterPointService().getWaterPoint(referenceNumber);
    }

    @Override
    public void saveWaterPoint(Waterpoint waterPoint) {
        getWaterPointService().saveWaterPoint(waterPoint);
    }

    public org.m4water.server.service.WaterPointService getWaterPointService() {
        if (waterPointService == null) {
            WebApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
            waterPointService = (org.m4water.server.service.WaterPointService) ctx.getBean("waterpointService");
        }
        return waterPointService;
    }
}
