/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cwf.server;

import java.util.List;
import org.cwf.client.service.InspectionClientService;
import org.cwf.server.rpc.M4waterPersistentRemoteService;
import org.m4water.server.admin.model.Inspection;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 *
 * @author victor
 */
public class InspectionClientServiceImpl extends M4waterPersistentRemoteService implements InspectionClientService {

    private org.m4water.server.service.InspectionService inspectionService;

    @Override
    public List<Inspection> getInspections() {
        return getInspectionService().getInspections();
    }

    @Override
    public Inspection getInspection(String id) {
        return getInspectionService().getInspection(id);
    }

    @Override
    public void saveInspection(Inspection inspection) {
        getInspectionService().saveInspection(inspection);
    }

    public org.m4water.server.service.InspectionService getInspectionService() {
        if (inspectionService == null) {
            WebApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
            inspectionService = (org.m4water.server.service.InspectionService) ctx.getBean("inspectionService");
        }
        return inspectionService;
    }
}
