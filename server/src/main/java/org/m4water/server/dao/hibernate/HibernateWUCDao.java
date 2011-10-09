/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.m4water.server.dao.hibernate;

import org.m4water.server.admin.model.WaterUserCommittee;
import org.m4water.server.dao.WUCDao;
import org.springframework.stereotype.Repository;

/**
 *
 * @author kay
 */
@Repository("wucDao")
public class HibernateWUCDao extends BaseDAOImpl<WaterUserCommittee, String> implements WUCDao{
    
}
