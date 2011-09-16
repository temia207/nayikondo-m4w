/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cwf.client.model;

import com.extjs.gxt.ui.client.data.BaseModel;
import java.util.Date;
import org.m4water.server.admin.model.Village;
import org.m4water.server.admin.model.WaterPointSummary;
import org.m4water.server.admin.model.WaterpointTypes;

/**
 *
 * @author victor
 */
public class WaterPointModel extends BaseModel {

    private WaterPointSummary waterPoint;

    public WaterPointModel() {
    }

    public WaterPointModel(WaterPointSummary waterPointSummary) {
        setWaterPoint(waterPointSummary);
    }

    public WaterPointModel(String waterPointId, String village, String types,
            String fundingOrg, Date date, String eastings, String northings, String elevation,
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

    public WaterPointModel(String waterPointId, String villageName, String parishName,
            String subcountyName, String countyName, String district,Date date) {
        setId(waterPointId);
        setVillage(villageName);
        setParish(parishName);
        setSubcounty(subcountyName);
        setCounty(countyName);
        setDistrict(district);
        setDate(date);
    }

    public void setId(String id) {
        set("id", id);
    }

    public void setVillage(String village) {
        set("village", village);
    }

    public void setDistrict(String district) {
        set("district", district);
    }

    public void setSubcounty(String subCounty) {
        set("subcounty", subCounty);
    }

    public void setCounty(String county) {
        set("county", county);
    }

    public void setParish(String parish) {
        set("parish", parish);
    }

    public void setWaterpointTypes(String types) {
        set("waterpointtypes", types);
    }

    public void setFundingOrganisation(String fundingOrg) {
        set("fundingorg", fundingOrg);
    }

    public void setDate(Date date) {
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

    public void setWaterPoint(WaterPointSummary waterPointSummary) {
        this.waterPoint = waterPointSummary;
        updateWaterPoint(waterPointSummary);
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

    public Date getDate() {
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

    public String getParish() {
        return get("parish");
    }

    public String getSubCounty() {
        return get("subcounty");
    }

    public String getCounty() {
        return get("county");
    }

    public String getDistrict() {
        return get("district");
    }

    public WaterPointSummary getWaterPointSummary() {
        return this.waterPoint;
    }

    public void updateWaterPoint(WaterPointSummary summary) {
        this.waterPoint = summary;
        setDate(summary.getDate());
        setId(summary.getWaterPointId());
        setVillage(summary.getVillageName());
        setParish(summary.getParishName());
        setSubcounty(summary.getSubcountyName());
        setCounty(summary.getCountyName());
        setDistrict(summary.getDistrict());
//        setWaterpointTypes(summary.getWaterpointTypes().getName());
//        setEastings(summary.getEastings());
//        setNorthings(summary.getNorthings());
//        setElevation(summary.getElevation());
//        setFundingSrc(summary.getFundingSource());
//        setOwnership(summary.getOwnership());
//        setHouseHolds(summary.getHouseholds());
//        setTypeOfMagt(summary.getTypeOfMagt());
    }
}
