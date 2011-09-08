/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.m4water.server.dao.hibernate;

import java.util.List;
import org.m4water.server.admin.model.Waterpoint;
import org.m4water.server.dao.WaterPointDao;

import org.springframework.stereotype.Repository;
/**
 *
 * @author victor
 */
@Repository("Water")
public class HibernateWaterPointDao extends BaseDAOImpl<Waterpoint> implements WaterPointDao{

    @Override
    public List<Waterpoint> getWaterPoints() {
		return findAll();
    }

    @Override
    public Waterpoint getWaterPoint(String waterpointId) {
         return searchUniqueByPropertyEqual("waterpointId", waterpointId);
    }

    @Override
    public void saveWaterPoint(Waterpoint waterPoint) {
        save(waterPoint);
    }

}
