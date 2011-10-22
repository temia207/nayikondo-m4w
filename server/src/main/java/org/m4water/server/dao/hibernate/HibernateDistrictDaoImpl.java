package org.m4water.server.dao.hibernate;

import java.io.Serializable;
import org.m4water.server.admin.model.District;
import org.m4water.server.dao.DistricDao;
import org.springframework.stereotype.Repository;

/**
 *
 */
@Repository("districtDao")
public class HibernateDistrictDaoImpl extends BaseDAOImpl<District, String> implements DistricDao {
    
}
