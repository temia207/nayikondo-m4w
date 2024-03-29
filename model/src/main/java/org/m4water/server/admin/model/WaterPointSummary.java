/*
 * generates waterpoint summary
 */
package org.m4water.server.admin.model;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author victor
 */
public class WaterPointSummary implements Serializable{

    private String waterPointId;
    private String waterpointName;
    private String villageName;
    private String parishName;
    private String subcountyName;
    private String countyName;
    private String district;
    private Date date;
    private Date baselineDate;
    private String baselinePending;

    public WaterPointSummary() {
    }

    public WaterPointSummary(String waterPointId,String waterpointName, String villageName, String parishName, String subcountyName, String countyName, String district, Date date,Date baselineDate,String baselinePending) {
        this.waterPointId = waterPointId;
	this.waterpointName = waterpointName;
        this.villageName = villageName;
        this.parishName = parishName;
        this.subcountyName = subcountyName;
        this.countyName = countyName;
        this.district = district;
        this.date = date;
        this.baselineDate = baselineDate;
        this.baselinePending = baselinePending;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getBaselineDate() {
        return this.baselineDate;
    }

    public void setBaselineDate(Date baselineDate) {
        this.baselineDate = baselineDate;
    }

    public String getBaselinePending() {
        return this.baselinePending;
    }

    public void setBaselinePending(String baselinePending) {
        this.baselinePending = baselinePending;
    }

	public String getWaterpointName() {
		return waterpointName;
	}

	public void setWaterpointName(String waterpointName) {
		this.waterpointName = waterpointName;
	}
}
