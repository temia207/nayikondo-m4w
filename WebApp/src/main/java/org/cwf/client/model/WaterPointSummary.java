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
public class WaterPointSummary extends BaseModel {

    private String date;
    private String id;
    private String district;
    private String subCounty;
    private String village;

    public WaterPointSummary() {
    }

    public WaterPointSummary(String date, String id, String district, String subCounty, String village, String longitude, String latitude) {
        setDate(date);
        setId(id);
        setDistrict(district);
        setSubCounty(subCounty);
        setVillage(village);
        setLongitude(longitude);
        setLatitude(latitude);
    }

    public void setDate(String date) {
        set("date", date);
    }

    public void setId(String id) {
        set("id", id);
    }

    public void setDistrict(String district) {
        set("district", district);
    }

    public void setSubCounty(String subCounty) {
        set("subcounty", subCounty);
    }

    public void setVillage(String village) {
        set("village", village);
    }

    public void setLongitude(String longitude) {
        set("longitude", longitude);
    }

    public void setLatitude(String latitude) {
        set("latitude", latitude);
    }

    public String getDate() {
        return get("date");
    }

    public String getId() {
        return get("id");
    }

    public String getDistrict() {
        return get("district");
    }

    public String getSubCounty() {
        return get("subcounty");
    }

    public String getVillage() {
        return get("village");
    }

    public String getLongitude() {
        return get("longitude");
    }

    public String getLatitude() {
        return get("latitude");
    }

    public static List<WaterPointSummary> getSampleNewWaterPoints() {
        List<WaterPointSummary> newWaterPoints = new ArrayList<WaterPointSummary>();
        newWaterPoints.add(new WaterPointSummary("19/5/2011", "UMAS01236", "Masaka", "Kigasa", "Buddi","1234.908","1234.000"));
        newWaterPoints.add(new WaterPointSummary("20/8/2011", "UKLE01222", "Kabale", "Kamuganguzi", "Buranga","1234.908","1234.000"));
        return newWaterPoints;
    }
}
