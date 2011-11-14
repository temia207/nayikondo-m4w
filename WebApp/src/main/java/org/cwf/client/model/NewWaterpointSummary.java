/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cwf.client.model;

import com.extjs.gxt.ui.client.data.BaseModel;
import java.util.Date;
import org.m4water.server.admin.model.SettingGroup;

/**
 *
 * @author victor
 */
public class NewWaterpointSummary extends BaseModel {

    private SettingGroup waterPoint;

    public NewWaterpointSummary() {
    }

    public String getGroupSetting() {
        return "groupsetting";
    }

    public void setGroupSetting(String groupSetting) {
        set("groupsetting", groupSetting);
    }

    public SettingGroup getSettingGroup() {
        return waterPoint;
    }

    public void setSettingGroup(SettingGroup settingGroup) {
        this.waterPoint = settingGroup;
    } public void setId(String id) {
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

    public void setWaterPointName(String fundingOrg) {
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

    public String getId() {
        return get("id");
    }

    public String getVillage() {
        return get("village");
    }

    public String getWaterpointTypes() {
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
}
