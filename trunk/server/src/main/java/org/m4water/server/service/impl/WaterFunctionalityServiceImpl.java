/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.m4water.server.service.impl;

import org.m4water.server.admin.model.WaterFunctionality;
import org.m4water.server.dao.WaterFunctionalityDao;
import org.m4water.server.service.WaterFunctionalityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author kay
 */
@Repository("waterFunctionalityServiceImpl")
@Transactional
public class WaterFunctionalityServiceImpl implements WaterFunctionalityService {

    @Autowired
    private WaterFunctionalityDao wfDao;

    public void save(WaterFunctionality wf) {
        wfDao.save(wf);
    }
}
