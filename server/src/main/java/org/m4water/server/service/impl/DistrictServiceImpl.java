package org.m4water.server.service.impl;

import java.util.Set;
import net.sf.beanlib.hibernate3.Hibernate3BeanReplicator;
import org.hibernate.Hibernate;
import org.m4water.server.admin.model.County;
import org.m4water.server.admin.model.District;
import org.m4water.server.admin.model.Parish;
import org.m4water.server.admin.model.Subcounty;
import org.m4water.server.dao.DistricDao;
import org.m4water.server.service.DistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 */
@Service("districtService")
@Transactional
public class DistrictServiceImpl implements DistrictService {

    @Autowired
    private DistricDao districDao;

    public District getDistrictByName(String name) {
        District district = districDao.searchUniqueByPropertyEqual("name", name);
        return district;
    }

    public District getDistrictByID(String id) {
        return districDao.find(id);
    }

    @Override
    public Subcounty getAnySubcountInDistrict(District district, String subcountyName) {
        return districDao.getAnySubcountInDistrict(district,subcountyName);
    }
}
