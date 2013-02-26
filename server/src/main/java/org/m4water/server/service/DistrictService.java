package org.m4water.server.service;

import org.m4water.server.admin.model.District;
import org.m4water.server.admin.model.Subcounty;

/**
 *
 */
public interface  DistrictService {
    
    public District getDistrictByName(String name);
    
    public District getDistrictByID(String id);

    public Subcounty getAnySubcountInDistrict(District district, String subcountyName);
    
}
