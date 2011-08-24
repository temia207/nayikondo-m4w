package org.m4water.server.admin.model;

/**
 *
 * @author victor
 */
public class WaterPointDef extends AbstractEditable {

    private String date;
    private String waterPointId;
    private String district;
    private String subCounty;
    private String village;
    private String latitude;
    private String longitude;

    public WaterPointDef() {
        //
    }

    public WaterPointDef(String date, String id, String district, String subCounty, String village, String longitude, String latitude) {
        this.date = date;
        this.waterPointId = id;
        this.district = district;
        this.subCounty = subCounty;
        this.village = village;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setWaterPointId(String id) {
        this.waterPointId = id;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public void setSubCounty(String subCounty) {
        this.subCounty = subCounty;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getDate() {
        return this.date;
    }

    public String getWaterPointId() {
        return this.waterPointId;
    }

    public String getDistrict() {
        return this.district;
    }

    public String getSubCounty() {
        return this.subCounty;
    }

    public String getVillage() {
        return this.village;
    }

    public String getLongitude() {
        return this.longitude;
    }

    public String getLatitude() {
        return this.latitude;
    }
}
