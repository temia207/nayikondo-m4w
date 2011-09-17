/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cwf.client.model;

import com.extjs.gxt.ui.client.data.BaseModel;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author victor
 */
public class StatusSummary extends BaseModel {

    public StatusSummary() {
    }

    public StatusSummary(String status) {
        setStatus(status);
    }

    public void setStatus(String status) {
        set("status", status);
    }

    public String getStatus() {
        return get("status");
    }
    public static List<StatusSummary> getSampleStatus(){
     List<StatusSummary> summary = new ArrayList<StatusSummary>();
     summary.add(new StatusSummary("open"));
     summary.add(new StatusSummary("closed"));
     summary.add(new StatusSummary("suspended"));
     return summary;
    }
}
