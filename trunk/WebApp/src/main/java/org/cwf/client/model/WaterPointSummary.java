/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cwf.client.model;

import com.extjs.gxt.ui.client.data.BaseModel;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.m4water.server.admin.model.County;
import org.m4water.server.admin.model.District;
import org.m4water.server.admin.model.Parish;
import org.m4water.server.admin.model.Subcounty;
import org.m4water.server.admin.model.Village;
import org.m4water.server.admin.model.WaterpointTypes;

/**
 *
 * @author victor
 */
public class WaterPointSummary extends BaseModel {

    public WaterPointSummary() {
    }

    public WaterPointSummary(String waterPointId, Village village, WaterpointTypes types,
            String fundingOrg, String date, String eastings, String northings, String elevation,
            String fundingSrce, String ownership, String households, String typeOfMgt) {
        setDate(date);
        setId(waterPointId);
        setVillage(village);
        setWaterpointTypes(types);
        setEastings(eastings);
        setNorthings(northings);
        setElevation(elevation);
        setFundingSrc(fundingSrce);
        setOwnership(ownership);
        setHouseHolds(households);
        setTypeOfMagt(typeOfMgt);
    }

    public void setId(String id) {
        set("id", id);
    }

    public void setVillage(Village village) {
        set("village", village);
    }

    public void setWaterpointTypes(WaterpointTypes types) {
        set("waterpointtypes", types);
    }

    public void setFundingOrganisation(String fundingOrg) {
        set("fundingorg", fundingOrg);
    }

    public void setDate(String date) {
        set("date", date);
    }

    public void setEastings(String eastings) {
        set("eastings", eastings);
    }

    public void setNorthings(String northings) {
        set("northings", northings);
    }

    public void setElevation(String elevation) {
        set("elevation", elevation);
    }

    public void setFundingSrc(String fundingfSrc) {
        set("fundingsrc", fundingfSrc);
    }

    public void setOwnership(String ownership) {
        set("ownership", ownership);
    }

    public void setHouseHolds(String houseHolds) {
        set("households", houseHolds);
    }

    public void setTypeOfMagt(String typeOfManagement) {
        set("typeOfManagement", typeOfManagement);
    }

    public String getId() {
        return get("id");
    }

    public Village getVillage() {
        return get("village");
    }

    public WaterpointTypes getWaterpointTypes() {
        return get("waterpointtypes");
    }

    public String getFundingOrganisation() {
        return get("fundingorg");
    }

    public String getDate() {
        return get("date");
    }

    public String getEastings() {
        return get("eastings");
    }

    public String getNorthings() {
        return get("northings");
    }

    public String getElevation() {
        return get("elevation");
    }

    public String getFundingSrc() {
        return get("fundingsrc");
    }

    public String getOwnership() {
        return get("ownership");
    }

    public String getHouseHolds() {
        return get("households");
    }

    public String getTypeOfMagt() {
        return get("typeOfManagement");
    }

    public Parish getParish() {
        Village village = get("village");
        return village.getParish();
    }

    public Subcounty getSubCounty() {
        return getParish().getSubcounty();
    }

    public County getCounty() {
        return getSubCounty().getCounty();
    }

    public District getDistrict() {
        return getCounty().getDistrict();
    }
//    public static List<WaterPointSummary> getSampleNewWaterPoints() {
//        List<WaterPointSummary> newWaterPoints = new ArrayList<WaterPointSummary>();
//        newWaterPoints.add(new WaterPointSummary( "UMAS01236",new Village, "Masaka", "Kigasa", "Buddi", "1234.908", "1234.000"));
//        newWaterPoints.add(new WaterPointSummary("20/8/2011", "UKLE01222", "Kabale", "Kamuganguzi", "Buranga", "1234.908", "1234.000"));
//        return newWaterPoints;
//    }
//
//    public static List<WaterPointSummary> getSampleAvailableWaterPoints() {
//        List<WaterPointSummary> newWaterPoints = new ArrayList<WaterPointSummary>();
//        newWaterPoints.add(new WaterPointSummary("19/5/2011", "UMAS01236", "Masaka", "Kigasa", "Buddi", "1234.908", "1234.000"));
//        newWaterPoints.add(new WaterPointSummary("20/8/2011", "UKLE01222", "Kabale", "Kamuganguzi", "Buranga", "1234.908", "1234.000"));
//        newWaterPoints.add(new WaterPointSummary("19/5/2011", "MBRA01236", "Mbarara", "Luti", "Maryhill", "1234.908", "1234.000"));
//        newWaterPoints.add(new WaterPointSummary("20/8/2011", "MBRA01222", "Mbarara", "Municipality", "Kirigime", "1234.908", "1234.000"));
//        return newWaterPoints;
//    }
}
