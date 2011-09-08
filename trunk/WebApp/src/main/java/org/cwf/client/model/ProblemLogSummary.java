package org.cwf.client.model;

import com.extjs.gxt.ui.client.data.BaseModel;

/**
 *
 * @author victor
 */
public class ProblemLogSummary extends BaseModel {

    public ProblemLogSummary(int problemLogId, int problemId, String telNumber, String date, String issue) {
    }

    public void setProblemLogId(int problemLogId) {
        set("logid", problemLogId);
    }

    public void setProblemId(int problemId) {
        set("problemid", problemId);
    }

    public void setTelNumber(String telNumber) {
        set("telephone", telNumber);
    }

    public void setdate(String date) {
        set("date", date);
    }

    public void setIssue(String issue) {
        set("issue", issue);
    }

    public Integer getProblemLogId() {
        return get("logid");
    }

    public Integer getProblemId() {
        return get("problemid");
    }

    public String getTelNumber() {
        return get("telephone");
    }

    public String getDate() {
        return get("date");
    }

    public String getIssue() {
        return get("issue");
    }
}
