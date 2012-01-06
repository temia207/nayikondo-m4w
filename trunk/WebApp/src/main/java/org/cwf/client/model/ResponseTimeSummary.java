
package org.cwf.client.model;

import com.extjs.gxt.ui.client.data.BaseModel;

/**
 *
 * @author victor
 */
public class ResponseTimeSummary extends BaseModel {

	public ResponseTimeSummary(String month, String fixed, String pending, String responseTime, String deviation) {
		setMonth(month);
		setFixed(fixed);
		setPending(pending);
		setResponseTime(responseTime);
		setDeviation(deviation);
	}

	public String getDeviation() {
		return get("deviation");
	}

	public void setDeviation(String deviation) {
		set("deviation", deviation);
	}

	public String getFixed() {
		return get("fixed");
	}

	public void setFixed(String fixed) {
		set("fixed",fixed);
	}

	public String getMonth() {
		return get("month");
	}

	public void setMonth(String month) {
		set("month",month);
	}

	public String getPending() {
		return get("pending");
	}

	public void setPending(String pending) {
		set("pending",pending);
	}

	public String getResponseTime() {
		return get("responsetime");
	}

	public void setResponseTime(String responseTime) {
		set("responsetime",responseTime);
	}
}
