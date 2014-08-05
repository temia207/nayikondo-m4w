/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.m4water.server.service.impl;

import java.util.Date;
import java.util.List;

import org.m4water.server.admin.model.Problem;
import org.m4water.server.admin.model.WaterPointSummary;
import org.m4water.server.admin.model.Waterpoint;
import org.m4water.server.admin.model.WaterpointTypes;
import org.m4water.server.admin.model.paging.PagingLoadConfig;
import org.m4water.server.admin.model.paging.PagingLoadResult;
import org.m4water.server.dao.ProblemDao;
import org.m4water.server.dao.WaterPointDao;
import org.m4water.server.dao.WaterPointSummaryDao;
import org.m4water.server.dao.WaterPointTypeDao;
import org.m4water.server.service.WaterPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

/**
 *
 * @author victor
 */
@Service("waterpointService")
@Transactional
public class WaterPointServiceImpl implements WaterPointService{

    @Autowired
    private WaterPointDao waterPointDao;
    @Autowired
    private WaterPointSummaryDao waterPointSummaryDao;
    @Autowired
    private WaterPointTypeDao pointTypeDao;
    @Autowired
    private ProblemDao problemDao;
    
    @Override
    @Transactional(readOnly = true)
    public List<Waterpoint> getWaterPoints() {
        return waterPointDao.getWaterPoints();
    }

    @Override
    @Transactional(readOnly = true)
    public Waterpoint getWaterPoint(String referenceNumber) {
        return waterPointDao.getWaterPoint(referenceNumber);
    }

    @Override
    public void saveWaterPoint(Waterpoint waterPoint) {
        waterPointDao.save(waterPoint);
    }

    @Override
    public List<WaterPointSummary> getWaterPointSummaries() {
       return waterPointDao.getWaterPointSummaries();
    }

    @Override
    public Date getBaselineSetDate() {
        return waterPointDao.getBaselineSetDate();
    }

    @Override
    public WaterpointTypes getWaterPointType(String name) {
       return this.pointTypeDao.searchUniqueByPropertyEqual("name", name);
    }

    @Override
    public List<WaterPointSummary> getWaterPointSummaries(String district) {
        return waterPointDao.getWaterPointSummaries(district);
    }

	@Override
	public List<Waterpoint> getWaterpoints(String baselineDone) {
		return waterPointDao.getWaterpoints(baselineDone);
	}

    public Problem getLatestProblem(Waterpoint waterpoint){
          return this.problemDao.getLatestProblem(waterpoint.getWaterpointId());
    }

    public Problem getProblemWithYawlID(String yawlId){
        return problemDao.searchUniqueByPropertyEqual("yawlid",yawlId);
    }

	@Override
	public List<WaterPointSummary> getWaterPointSummaries(String district, String baseLineType) {
		return waterPointDao.getWaterPointSummaries(district, baseLineType);
	}

    @Override
    public PagingLoadResult<WaterPointSummary> getWaterPointSummaries(String district, String baseLineType, PagingLoadConfig loadConfig) {
        return waterPointSummaryDao.getWaterPointSummaries(district, baseLineType, loadConfig);
    }

}
