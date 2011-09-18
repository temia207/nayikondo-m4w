package org.m4water.server.service.impl;

import java.util.Set;
import org.m4water.server.admin.model.County;
import org.m4water.server.admin.model.District;
import org.m4water.server.admin.model.Parish;
import org.m4water.server.admin.model.ProblemLog;
import org.m4water.server.admin.model.Problem;
import org.m4water.server.admin.model.Subcounty;
import org.m4water.server.admin.model.Village;
import org.m4water.server.admin.model.Waterpoint;
import org.m4water.server.service.YawlService;
import org.m4water.server.yawl.TicketYawlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author kay
 */
@Service("yawlService")
public class YawlServiceImpl implements YawlService {

    @Autowired
    TicketYawlService yawlService;
    private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(YawlServiceImpl.class);

    @Override
    public void launchWaterPointFlow(Problem problem) {
        try {
            Waterpoint waterpoint = problem.getWaterpoint();
            TicketYawlService.Params params = new TicketYawlService.Params();
            params.addPumpMechanicName("mechanic");
            ProblemLog problemLog = null;
            Set problemLogs = problem.getProblemLogs();
            for (Object object : problemLogs) {
                problemLog = (ProblemLog) object;
                break;
            }

            params.setSenderNumber(problemLog.getSenderNo());
            params.setMechanicNumber("0789388969");
            params.setWaterPointID(problem.getWaterpoint().getWaterpointId());
            params.setTicketMessage(problemLog.getIssue());

            Village village = waterpoint.getVillage();
            Parish parish = village.getParish();
            Subcounty subcounty = parish.getSubcounty();
            County county = subcounty.getCounty();
            District district = county.getDistrict();

            params.put("district", district.getName() );
            params.put("county", county.getCountyName());
            params.put("parish", parish.getParishName());
            params.put("village", village.getVillagename());
            yawlService.launchCase(params);
        } catch (Exception ex) {
            log.error("Error occured while launching a ticket workflow", ex);
            throw new RuntimeException(ex);
        }
    }
}
