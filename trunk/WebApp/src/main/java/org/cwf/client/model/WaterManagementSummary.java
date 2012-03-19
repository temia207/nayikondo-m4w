/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cwf.client.model;

import com.extjs.gxt.ui.client.data.BaseModel;
import org.m4water.server.admin.model.reports.WucManagement;

/**
 *
 * @author victor
 */
public class WaterManagementSummary extends BaseModel {

	private WucManagement wucManagement;

	public WaterManagementSummary(String subcounty,String parish,String sources,String wucEstablished,
			String wucTrained,String noOfWomen,String womenInKeyPositions) {
		setSubcounty(subcounty);
		setParish(parish);
		setSources(sources);
		setWucEstablished(wucEstablished);
		setWucTrained(wucTrained);
		setNoOfWomen(noOfWomen);
		setWomenInKeyPositions(womenInKeyPositions);
	}

	public String getNoOfWomen() {
		return get("noOfWomen");
	}

	public void setNoOfWomen(String noOfWomen) {
		set("noOfWomen",noOfWomen);
	}

	public String getParish() {
		return get("parish");
	}

	public void setParish(String parish) {
		set("parish",parish);
	}

	public String getSources() {
		return get("sources");
	}

	public void setSources(String sources) {
		set("sources",sources);
	}

	public String getSubcounty() {
		return get("subcounty");
	}

	public void setSubcounty(String subcounty) {
		set("subcounty",subcounty);
	}

	public String getWomenInKeyPositions() {
		return get("womenInKeyPositions");
	}

	public void setWomenInKeyPositions(String womenInKeyPositions) {
		set("womenInKeyPositions",womenInKeyPositions);
	}

	public String getWucEstablished() {
		return get("wucEstablished");
	}

	public void setWucEstablished(String wucEstablished) {
		set("wucEstablished",wucEstablished);
	}

	public WucManagement getWucManagement() {
		return wucManagement;
	}

	public void setWucManagement(WucManagement wucManagement) {
		this.wucManagement = wucManagement;
	}

	public String getWucTrained() {
		return get("wucTrained");
	}

	public void setWucTrained(String wucTrained) {
		set("wucTrained",wucTrained);
	}
}
