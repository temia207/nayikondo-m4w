package org.m4water.server.service.impl;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.m4water.server.OpenXDataPropertyPlaceholderConfigurer;
import org.m4water.server.admin.model.County;
import org.m4water.server.admin.model.District;
import org.m4water.server.admin.model.Parish;
import org.m4water.server.admin.model.ProblemLog;
import org.m4water.server.admin.model.Problem;
import org.m4water.server.admin.model.Subcounty;
import org.m4water.server.admin.model.User;
import org.m4water.server.admin.model.Village;
import org.m4water.server.admin.model.Waterpoint;
import org.m4water.server.admin.model.exception.M4waterRuntimeException;
import org.m4water.server.dao.ProblemDao;
import org.m4water.server.service.WaterPointService;
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
    @Autowired
    private WaterPointService waterPointService;
    @Autowired ProblemDao problemDao;
    @Autowired
    OpenXDataPropertyPlaceholderConfigurer prptyPlcHlder;
    private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(YawlServiceImpl.class);

    public void cancelCase(String caseID) throws RuntimeException {
	try {
	    yawlService.cancelWorkflow(caseID);
	    log.info("Canceled case : <"+caseID+">");
	} catch (IOException ex) {
	   throw new RuntimeException(ex);
	}
    }

    @Override
    public String launchWaterPointFlow(Problem problem) {
        try {
            Waterpoint waterpoint = problem.getWaterpoint();

            Village village = waterpoint.getVillage();
            Parish parish = village.getParish();
            Subcounty subcounty = parish.getSubcounty();
            County county = subcounty.getCounty();
            District district = county.getDistrict();

            TicketYawlService.Params params = new TicketYawlService.Params();
            User mechanic = getSubcountHPM(subcounty, "1");
            params.addPumpMechanicName(mechanic == null ? "" : mechanic.getOxdName());
            ProblemLog problemLog = null;
            Set problemLogs = problem.getProblemLogs();
            for (Object object : problemLogs) {
                problemLog = (ProblemLog) object;
                break;
            }

            params.setSenderNumber(problemLog.getSenderNo());
            params.setMechanicNumber((mechanic.getContacts() + "").replace("-", ""));
            params.setWaterPointID(problem.getWaterpoint().getWaterpointId());
            params.setTicketMessage(problem.getProblemDescsription());

            params.put("district", district.getName());
            params.put("county", county.getCountyName());
            params.put("subcounty", subcounty.getSubcountyName());
            params.put("parish", parish.getParishName());
            params.put("village", village.getVillagename());
            params.put("waterPointName", waterpoint.getName());
            params.put("ticketID", problem.getId() + "");
           return yawlService.launchCase(params);
        } catch (Exception ex) {

            throw new RuntimeException("Error occured while launching a ticket workflow", ex);
        }
    }

    private User getSubcountHPM(Subcounty subcounty, String postfix) {
        for (Object user : subcounty.getUsers()) {
            User tempMech = (User) user;
            if (tempMech.getUsername() != null) {
                if (tempMech.getUsername().trim().endsWith(postfix)) {
                    return (User) user;
                }
            }
        }
        return null;
    }

    @Override
    public String launchWaterPointBaseline(Waterpoint waterpoint) {
        Properties resolvedProps = prptyPlcHlder.getResolvedProps();
        Village village = waterpoint.getVillage();
        Parish parish = village.getParish();
        Subcounty subcounty = parish.getSubcounty();
        County county = subcounty.getCounty();
        District district = county.getDistrict();
        User mechanic = getSubcountHPM(subcounty, "1");
        User healthWorker = getSubcountHPM(subcounty, "2");
        TicketYawlService.Params params = new TicketYawlService.Params();
        params.put("district", district.getName());
        params.put("subcounty", subcounty.getSubcountyName());
        params.put("parish", parish.getParishName());
        params.put("village", village.getVillagename());
        params.put("source_name", waterpoint.getName());
        params.put("source_number", waterpoint.getWaterpointId());
        params.put("tel", (mechanic.getContacts() + "").replace("-", ""));
        params.put("namesms", mechanic.getUsername());
        params.put("userAssignedCollector", mechanic.getUsername());
        params.put("userAssignedReviewer", healthWorker.getUsername());
        try {
            return yawlService.launchCase("baseline", resolvedProps.getProperty("baseline.version"), params);
        } catch (IOException ex) {
            throw new M4waterRuntimeException("Error While lauching baseline workflow for water point: "+waterpoint.getId()+ " \n"+ex.getMessage(), ex);
        }
    }

    @Override
    public String launchWaterPointBaseline(String waterpointId) {
        Waterpoint waterPoint = waterPointService.getWaterPoint(waterpointId);
        String caseID = launchWaterPointBaseline(waterPoint);
        //set this waterpoint to pending basleine
        waterPoint.setBaselinePending(caseID);
        if(waterPoint.getBaselineDate() == null){
            waterPoint.setBaselineDate(new Date(1));
        }
        waterPointService.saveWaterPoint(waterPoint);
	return caseID;
    }
    
    public void cancelProblem(int problemID){
	Problem problem = problemDao.find((long)problemID);
	Waterpoint waterpoint = problem.getWaterpoint();
	String baselinePending = problem.getYawlid();
	if(baselinePending == null)
	    return;
	 baselinePending = baselinePending.replaceFirst("T", "");
	String caseID = null;
	try {
	    caseID = baselinePending.split("-")[0];
	} catch (Exception e) {
	    throw new RuntimeException("Error Extracting yawl case ID from Problem + Waterpoint<"+problemID+" + "+waterpoint.getId()+"> YawlID <"+problem.getYawlid()+">");
	}
	cancelCase(caseID);
    }
    
    
}
