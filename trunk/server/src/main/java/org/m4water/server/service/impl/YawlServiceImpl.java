package org.m4water.server.service.impl;

import java.util.Set;
import org.m4water.server.admin.model.County;
import org.m4water.server.admin.model.District;
import org.m4water.server.admin.model.Parish;
import org.m4water.server.admin.model.ProblemLog;
import org.m4water.server.admin.model.Problem;
import org.m4water.server.admin.model.Subcounty;
import org.m4water.server.admin.model.User;
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

            Village village = waterpoint.getVillage();
            Parish parish = village.getParish();
            Subcounty subcounty = parish.getSubcounty();
            County county = subcounty.getCounty();
            District district = county.getDistrict();
            
            TicketYawlService.Params params = new TicketYawlService.Params();
            User mechanic = getSubcountHPM(subcounty);
            params.addPumpMechanicName(mechanic == null ? "":mechanic.getOxdName());
            ProblemLog problemLog = null;
            Set problemLogs = problem.getProblemLogs();
            for (Object object : problemLogs) {
                problemLog = (ProblemLog) object;
                break;
            }

            params.setSenderNumber(problemLog.getSenderNo());
            params.setMechanicNumber((mechanic.getContacts()+"").replace("-", ""));
            params.setWaterPointID(problem.getWaterpoint().getWaterpointId());
            params.setTicketMessage(problemLog.getIssue());

            params.put("district", district.getName() );
            params.put("county", county.getCountyName());
            params.put("subcounty", subcounty.getSubcountyName());
            params.put("parish", parish.getParishName());
            params.put("village", village.getVillagename());
            params.put("waterPointName", waterpoint.getName());
            params.put("ticketID", problem.getId()+"");
            yawlService.launchCase(params);
        } catch (Exception ex) {
            
            throw new RuntimeException("Error occured while launching a ticket workflow",ex);
        }
    }

    private User getSubcountHPM(Subcounty subcounty) {
        for (Object user : subcounty.getUsers()) {
            User tempMech = (User) user;
            if (tempMech.getUsername() != null) {
                if (tempMech.getUsername().trim().endsWith("1")) {
                    return  (User) user;
                }
            }
        }
        return null;
    }

    public void launchWaterPointBaseline(Waterpoint waterpoint) {
          Village village = waterpoint.getVillage();
            Parish parish = village.getParish();
            Subcounty subcounty = parish.getSubcounty();
            County county = subcounty.getCounty();
            District district = county.getDistrict();
            User user = getSubcountHPM(subcounty);
            TicketYawlService.Params params = new TicketYawlService.Params();
            params.put("district", district.getName() );
            params.put("subcounty", subcounty.getSubcountyName());
            params.put("parish", parish.getParishName());
            params.put("village", village.getVillagename());
            params.put("source_name", waterpoint.getName());
            params.put("source_number", null);
            params.put("tel", null);
            params.put("namesms", null);
            params.put("userAssignedCollector", null);
            params.put("userAssignedReviewer", null);
            
            //yawlService.launchWorkFlow
    }
}
