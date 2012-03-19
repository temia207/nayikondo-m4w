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
public class WucManagement extends AbstractEditable<String> {

	private String subcounty;
	private String parish;
	private String sources;
	private String wucEstablished;
	private String wucTrained;
	private String noOfWomen;
	private String womenInKeyPositions;

	public WucManagement() {
	}

	public String getNoOfWomen() {
		return noOfWomen;
	}

	public void setNoOfWomen(String noOfWomen) {
		this.noOfWomen = noOfWomen;
	}

	public String getParish() {
		return parish;
	}

	public void setParish(String parish) {
		this.parish = parish;
	}

	public String getSources() {
		return sources;
	}

	public void setSources(String sources) {
		this.sources = sources;
	}

	public String getSubcounty() {
		return subcounty;
	}

	public void setSubcounty(String subcounty) {
		this.subcounty = subcounty;
	}

	public String getWomenInKeyPositions() {
		return womenInKeyPositions;
	}

	public void setWomenInKeyPositions(String womenInKeyPositions) {
		this.womenInKeyPositions = womenInKeyPositions;
	}

	public String getWucEstablished() {
		return wucEstablished;
	}

	public void setWucEstablished(String wucEstablished) {
		this.wucEstablished = wucEstablished;
	}

	public String getWucTrained() {
		return wucTrained;
	}

	public void setWucTrained(String wucTrained) {
		this.wucTrained = wucTrained;
	}
}
