package org.m4water.server.admin.model;
// Generated Sep 7, 2011 12:43:34 PM by Hibernate Tools 3.2.1.GA

import java.util.HashSet;
import java.util.Set;

/**
 * District generated by hbm2java
 */
public class District extends AbstractEditable<String> {

    private String name;
    private String districtId;
    private Set counties = new HashSet(0);

    public District() {
    }

    public District(String id, String districtId, String name) {
        this.id = id;
        this.districtId = districtId;
        this.name = name;
    }

    public District(String id, String districtId, String name, Set counties) {
        this.id = id;
        this.districtId = districtId;
        this.name = name;
        this.counties = counties;
    }

    public String getDistrictId() {
        return this.districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set getCounties() {
        return this.counties;
    }

    public void setCounties(Set counties) {
        this.counties = counties;
    }

    public County getCounty(String countyName) {
        for (Object object : counties) {
            County county = (County) object;
            if (county.getCountyName() != null && county.getCountyName().equalsIgnoreCase(countyName)) {
                return county;
            }
        }
        return null;
    }

    public Subcounty getSubcouty(String sbcntyName) {
        for (Object object : counties) {
            County county = (County) object;
            Set subcounties = county.getSubcounties();
            for (Object sbcntyObj : subcounties) {
                Subcounty subcounty1 = (Subcounty) sbcntyObj;
                if (subcounty1.getSubcountyName() != null && subcounty1.getSubcountyName().equalsIgnoreCase(sbcntyName)) {
                    return subcounty1;
                }
            }
        }
        return null;
    }
}
