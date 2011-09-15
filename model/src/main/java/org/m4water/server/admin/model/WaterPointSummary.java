/*
 * generates waterpoint summary
 */
package org.m4water.server.admin.model;

/**
 *
 * @author victor
 */
public class WaterPointSummary extends AbstractEditable {
    private  String waterPointId;
    private  String villageName;
    private  String parishName;
    private  String subcountyName;
    private  String countyName;
    private  String district;

    public WaterPointSummary() {
    }



    public WaterPointSummary(String waterPointId,String villageName,String parishName,String subcountyName,String countyName,String district) {
        this.waterPointId = waterPointId;
        this.villageName = villageName;
        this.parishName = parishName;
        this.subcountyName = subcountyName;
        this.countyName = countyName;
        this.district = district;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getParishName() {
        return parishName;
    }

    public void setParishName(String parishName) {
        this.parishName = parishName;
    }

    public String getSubcountyName() {
        return subcountyName;
    }

    public void setSubcountyName(String subcountyName) {
        this.subcountyName = subcountyName;
    }

    public String getVillageName() {
        return villageName;
    }

    public void setVillageName(String villageName) {
        this.villageName = villageName;
    }

    public String getWaterPointId() {
        return waterPointId;
    }

    public void setWaterPointId(String waterPointId) {
        this.waterPointId = waterPointId;
    }


}