package org.m4water.server.dao;

import org.m4water.server.admin.model.District;
import org.m4water.server.admin.model.Subcounty;

/**
 *
 */
public interface  DistricDao extends BaseDAO<District, String> {

    public Subcounty getAnySubcountInDistrict(District district,String subcountyName);
    
}
