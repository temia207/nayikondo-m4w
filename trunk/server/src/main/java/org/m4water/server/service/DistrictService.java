package org.m4water.server.service;

import org.m4water.server.admin.model.District;

/**
 *
 */
public interface  DistrictService {
    
    public District getDistrictByName(String name);
    
    public District getDistrictByID(String id);
    
}
