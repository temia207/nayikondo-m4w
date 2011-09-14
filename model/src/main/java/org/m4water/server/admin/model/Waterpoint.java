package org.m4water.server.admin.model;
// Generated Sep 7, 2011 12:43:34 PM by Hibernate Tools 3.2.1.GA

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Waterpoint generated by hbm2java
 */
public class Waterpoint extends AbstractEditable {

    private String waterpointId;
    private Village village;
    private WaterpointTypes waterpointTypes;
    private String fundingOrg;
    private Date dateInstalled;
    private String eastings;
    private String northings;
    private String elevation;
    private String fundingSource;
    private String ownership;
    private String households;
    private String typeOfMagt;
    private Set problems = new HashSet(0);
    private Set waterFunctionality = new HashSet(0);

    public Waterpoint() {
    }

    public Waterpoint(String waterpointId, Village village, WaterpointTypes waterpointTypes, String fundingOrg, Date dateInstalled, String eastings, String northings, String elevation, String fundingSource, String ownership, String households, String typeOfMagt) {
        this.waterpointId = waterpointId;
        this.village = village;
        this.waterpointTypes = waterpointTypes;
        this.fundingOrg = fundingOrg;
        this.dateInstalled = dateInstalled;
        this.eastings = eastings;
        this.northings = northings;
        this.elevation = elevation;
        this.fundingSource = fundingSource;
        this.ownership = ownership;
        this.households = households;
        this.typeOfMagt = typeOfMagt;
    }

    public Waterpoint(String waterpointId, Village village, WaterpointTypes waterpointTypes, String fundingOrg, Date dateInstalled, String eastings, String northings, String elevation, String fundingSource, String ownership, String households, String typeOfMagt, Set problems, Set waterFunctionality) {
        this.waterpointId = waterpointId;
        this.village = village;
        this.waterpointTypes = waterpointTypes;
        this.fundingOrg = fundingOrg;
        this.dateInstalled = dateInstalled;
        this.eastings = eastings;
        this.northings = northings;
        this.elevation = elevation;
        this.fundingSource = fundingSource;
        this.ownership = ownership;
        this.households = households;
        this.typeOfMagt = typeOfMagt;
        this.problems = problems;
        this.waterFunctionality = waterFunctionality;
    }

    public String getWaterpointId() {
        return this.waterpointId;
    }

    public void setWaterpointId(String waterpointId) {
        this.waterpointId = waterpointId;
    }

    public Village getVillage() {
        return this.village;
    }

    public void setVillage(Village village) {
        this.village = village;
    }

    public WaterpointTypes getWaterpointTypes() {
        return this.waterpointTypes;
    }

    public void setWaterpointTypes(WaterpointTypes waterpointTypes) {
        this.waterpointTypes = waterpointTypes;
    }

    public String getFundingOrg() {
        return this.fundingOrg;
    }

    public void setFundingOrg(String fundingOrg) {
        this.fundingOrg = fundingOrg;
    }

    public Date getDateInstalled() {
        return this.dateInstalled;
    }

    public void setDateInstalled(Date dateInstalled) {
        this.dateInstalled = dateInstalled;
    }

    public String getEastings() {
        return this.eastings;
    }

    public void setEastings(String eastings) {
        this.eastings = eastings;
    }

    public String getNorthings() {
        return this.northings;
    }

    public void setNorthings(String northings) {
        this.northings = northings;
    }

    public String getElevation() {
        return this.elevation;
    }

    public void setElevation(String elevation) {
        this.elevation = elevation;
    }

    public String getFundingSource() {
        return this.fundingSource;
    }

    public void setFundingSource(String fundingSource) {
        this.fundingSource = fundingSource;
    }

    public String getOwnership() {
        return this.ownership;
    }

    public void setOwnership(String ownership) {
        this.ownership = ownership;
    }

    public String getHouseholds() {
        return this.households;
    }

    public void setHouseholds(String households) {
        this.households = households;
    }

    public String getTypeOfMagt() {
        return this.typeOfMagt;
    }

    public void setTypeOfMagt(String typeOfMagt) {
        this.typeOfMagt = typeOfMagt;
    }

    public Set getProblems() {
        return this.problems;
    }

    public void setProblems(Set problems) {
        this.problems = problems;
    }

    public void setWaterFunctionality(Set waterFunctionality) {
        this.waterFunctionality = waterFunctionality;
    }

    public Set getWaterFunctionality() {
        return this.waterFunctionality;
    }
}