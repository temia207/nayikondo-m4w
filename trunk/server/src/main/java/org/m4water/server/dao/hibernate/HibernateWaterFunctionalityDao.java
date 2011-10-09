/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.m4water.server.dao.hibernate;

import org.m4water.server.admin.model.WaterFunctionality;
import org.m4water.server.dao.WaterFunctionalityDao;
import org.springframework.stereotype.Repository;

/**
 *
 * @author kay
 */
@Repository("waterFunctionalityDao")
public class HibernateWaterFunctionalityDao extends BaseDAOImpl<WaterFunctionality, String> implements WaterFunctionalityDao{
    
}
