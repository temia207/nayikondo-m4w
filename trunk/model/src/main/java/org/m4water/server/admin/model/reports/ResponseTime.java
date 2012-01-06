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
public class ResponseTime extends AbstractEditable<String>{

	private String month;
	private String waterPointsFixed;
	private String waterPointsPending;
	private String averageResponseTime;
	private String standardDeviation;

	public ResponseTime() {
		//
	}

	public String getAverageResponseTime() {
		return averageResponseTime;
	}

	public void setAverageResponseTime(String averageResponseTime) {
		this.averageResponseTime = averageResponseTime;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getStandardDeviation() {
		return standardDeviation;
	}

	public void setStandardDeviation(String standardDeviation) {
		this.standardDeviation = standardDeviation;
	}

	public String getWaterPointsFixed() {
		return waterPointsFixed;
	}

	public void setWaterPointsFixed(String waterPointsFixed) {
		this.waterPointsFixed = waterPointsFixed;
	}

	public String getWaterPointsPending() {
		return waterPointsPending;
	}

	public void setWaterPointsPending(String waterPointsPending) {
		this.waterPointsPending = waterPointsPending;
	}
}
