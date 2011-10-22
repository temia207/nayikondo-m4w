package org.m4water.server.dao.hibernate;

import org.m4water.server.admin.model.WaterpointTypes;
import org.m4water.server.dao.WaterPointTypeDao;
import org.springframework.stereotype.Repository;

/**
 *
 */
@Repository("waterPointDao")
public class HibernateWaterPointTypeDaoImpl extends BaseDAOImpl<WaterpointTypes, Integer> implements WaterPointTypeDao {
    
}
