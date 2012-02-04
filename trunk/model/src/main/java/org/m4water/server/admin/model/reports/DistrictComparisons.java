/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.m4water.server.admin.model.reports;

import org.m4water.server.admin.model.AbstractEditable;

/**
 *
 * @author victor
 */
public class DistrictComparisons extends AbstractEditable<String> {

	private String subcounty;
	private String parish;
	private String boreholes;
	private String shallowWells;
	private String publicTaps;
	private String protectedSprings;
	private String district;

	public DistrictComparisons() {
		//
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getBoreholes() {
		return boreholes;
	}

	public void setBoreholes(String boreholes) {
		this.boreholes = boreholes;
	}

	public String getParish() {
		return parish;
	}

	public void setParish(String parish) {
		this.parish = parish;
	}

	public String getProtectedSprings() {
		return protectedSprings;
	}

	public void setProtectedSprings(String protectedSprings) {
		this.protectedSprings = protectedSprings;
	}

	public String getPublicTaps() {
		return publicTaps;
	}

	public void setPublicTaps(String publicTaps) {
		this.publicTaps = publicTaps;
	}

	public String getShallowWells() {
		return shallowWells;
	}

	public void setShallowWells(String shallowWells) {
		this.shallowWells = shallowWells;
	}

	public String getSubcounty() {
		return subcounty;
	}

	public void setSubcounty(String subcounty) {
		this.subcounty = subcounty;
	}
}
