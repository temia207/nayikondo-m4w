package org.m4water.server.dao.hibernate;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.m4water.server.admin.model.District;
import org.m4water.server.admin.model.Subcounty;
import org.m4water.server.dao.DistricDao;
import org.springframework.stereotype.Repository;

/**
 *
 */
@Repository("districtDao")
public class HibernateDistrictDaoImpl extends BaseDAOImpl<District, String> implements DistricDao {


    @Override
    public Subcounty getAnySubcountInDistrict(District district, String subcountyName) {

        Query query = getSession().createQuery("from Subcounty sub where sub.county.district = :district and sub.subcountyName = :subname");

        query.setParameter("district", district);
        query.setParameter("subname", subcountyName);

        List subounties = query.list();
        if (subounties != null && !subounties.isEmpty()) {
            return (Subcounty) subounties.get(0);
        }

        return null;
    }
}
