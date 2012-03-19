/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cwf.client.model;

import com.extjs.gxt.ui.client.data.BaseModel;
import org.m4water.server.admin.model.reports.DistrictComparisons;

/**
 *
 * @author victor
 */
public class DistrictComparisonSummary extends BaseModel {

	private DistrictComparisons summary;

	public DistrictComparisonSummary() {
		//
	}
	public DistrictComparisonSummary(String district,String subcounty,String borehall,
			String shallowWell,String yadTaps,String protectedSpring,String total){
	setDistrict(district);
	setSubcounty(subcounty);
	setBoreholes(borehall);
	setShallowWells(shallowWell);
	setPublicTaps(yadTaps);
	setProtectedSprings(protectedSpring);
	setTotal(total);
	}
	public String getBoreholes() {
		return get("bh");
	}

	public void setBoreholes(String boreholes) {
		set("bh", boreholes);
	}

	public String getDistrict() {
		return get("district");
	}

	public void setDistrict(String district) {
		set("district", district);
	}

	public String getParish() {
		return get("parish");
	}

	public void setParish(String parish) {
		set("parish", parish);
	}

	public String getProtectedSprings() {
		return get("ps");
	}

	public void setProtectedSprings(String protectedSprings) {
		set("ps", protectedSprings);
	}

	public String getPublicTaps() {
		return get("yt");
	}

	public void setPublicTaps(String publicTaps) {
		set("yt", publicTaps);
	}

	public String getShallowWells() {
		return get("sw");
	}

	public void setShallowWells(String shallowWells) {
		set("sw", shallowWells);
	}

	public String getSubcounty() {
		return get("subcounty");
	}

	public void setSubcounty(String subcounty) {
		set("subcounty", subcounty);
	}

	public String getTotal() {
		return get("total");
	}

	public void setTotal(String total) {
		set("total",total);
	}
	public DistrictComparisons getSummary() {
		return summary;
	}

	public void setSummary(DistrictComparisons summary) {
		this.summary = summary;
	}
}
