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
public class Subcounty extends BaseModel {

    public Subcounty() {
    }

    public Subcounty(String name) {
        setName(name);
    }

    public void setName(String name) {
        set("name", name);
    }

    public String getName() {
        return get("name");
    }

    public static List<Subcounty> getSampleSubcounties() {
        List<Subcounty> summary = new ArrayList<Subcounty>();
        summary.add(new Subcounty("BUKUKU"));
        summary.add(new Subcounty("BUHEESI"));
        summary.add(new Subcounty("BUSORO"));
        return summary;
    }
}
