/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.m4water.server.service.impl;

import org.m4water.server.admin.model.WaterUserCommittee;
import org.m4water.server.dao.WUCDao;
import org.m4water.server.service.WUCService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author kay
 */
@Service("wucService")
@Transactional
public class WucServiceImpl implements WUCService {

    @Autowired
    private WUCDao wUCDao;

    public void save(WaterUserCommittee wuc) {
        wUCDao.save(wuc);
    }
}
