/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.m4water.server.service.impl;

import java.util.List;
import org.m4water.server.admin.model.Waterpoint;
import org.m4water.server.dao.WaterPointDao;
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

}
