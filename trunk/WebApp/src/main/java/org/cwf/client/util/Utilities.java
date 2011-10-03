/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cwf.client.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.cwf.client.model.Subcounty;
import org.m4water.server.admin.model.WaterPointSummary;

/**
 *
 * @author victor
 */
public class Utilities {

    public Utilities() {
    }

    public static List<Subcounty> filterSubcounties(List<WaterPointSummary> summary, String search) {
        Set items = new HashSet();
        List<Subcounty> subcountyList = new ArrayList<Subcounty>();
        for (WaterPointSummary point : summary) {
            String item = "";
            if (search.equalsIgnoreCase("district")) {
                item = point.getDistrict();
            } else if (search.equalsIgnoreCase("county")) {
                item = point.getCountyName();
            } else if (search.equalsIgnoreCase("subcounty")) {
                item = point.getSubcountyName();
            } else if (search.equalsIgnoreCase("parish")) {
                item = point.getParishName();
            } else if (search.equalsIgnoreCase("village")) {
                item = point.getVillageName();
            }
            if (!items.contains(item)) {
                items.add(item);
                subcountyList.add(new Subcounty(item));
            }
        }
        return subcountyList;
    }
}
