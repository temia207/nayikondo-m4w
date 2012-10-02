package org.m4water.server.admin.model;
// Generated Sep 7, 2011 12:43:34 PM by Hibernate Tools 3.2.1.GA

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Waterpoint generated by hbm2java
 */
public class Waterpoint extends AbstractEditable<String> {

    private Village village;
    private String waterpointId;
    private WaterpointTypes waterpointTypes;
    private String name;
    private Date dateInstalled;
    private String eastings = "";
    private String northings = "";
    private String elevation = "";
    private String fundingSource;
    private String ownership;
    private String households ="N/A";
    private String typeOfMagt;
    private Date baselineDate;
    private String baselinePending;
    private String careTakerName;
    private String careTakerTel;
    private Set problems = new HashSet(0);
    private Set inspections = new HashSet(0);
    private Set waterUserCommittees = new HashSet(0);
     private Set waterFunctionalities = new HashSet(0);

    public Waterpoint() {
    }

    public Waterpoint(String waterpointId, Village village, WaterpointTypes waterpointTypes, String fundingOrg, Date dateInstalled, String eastings, String northings, String elevation, String fundingSource, String ownership, String households, String typeOfMagt, Date baselineDate, String baselinePending) {
        this.waterpointId = waterpointId;
        this.village = village;
        this.waterpointTypes = waterpointTypes;
        this.name = fundingOrg;
        this.dateInstalled = dateInstalled;
        this.eastings = eastings;
        this.northings = northings;
        this.elevation = elevation;
        this.fundingSource = fundingSource;
        this.ownership = ownership;
        this.households = households;
        this.typeOfMagt = typeOfMagt;
        this.baselineDate = baselineDate;
        this.baselinePending = baselinePending;
    }

    public Waterpoint(String waterpointId, Village village, WaterpointTypes waterpointTypes, String name, Date dateInstalled, String eastings, String northings, String elevation, String fundingSource, String ownership, String households, String typeOfMagt, Set problems, Set waterFunctionality, Set inspections, Set waterUserCommittee, Date baselineDate, String baselinePending, Set waterFunctionalities) {
        this.waterpointId = waterpointId;
        this.village = village;
        this.waterpointTypes = waterpointTypes;
        this.name = name;
        this.dateInstalled = dateInstalled;
        this.eastings = eastings;
        this.northings = northings;
        this.elevation = elevation;
        this.fundingSource = fundingSource;
        this.ownership = ownership;
        this.households = households;
        this.typeOfMagt = typeOfMagt;
        this.problems = problems;
        this.waterFunctionalities = waterFunctionalities;
        this.inspections = inspections;
        this.waterUserCommittees = waterUserCommittee;
        this.baselineDate = baselineDate;
        this.baselinePending = baselinePending;
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

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Set getWaterFunctionalities() {
        return this.waterFunctionalities;
    }

    public void setWaterFunctionalities(Set waterFunctionalities) {
        this.waterFunctionalities = waterFunctionalities;
    }
    public boolean hasOpenProblems() {
        return getOpenProblem() != null;
    }

    public Problem getOpenProblem() {
        for (Object object : problems) {
            Problem prob = (Problem) object;
            if (prob.isOpen()) {
                return prob;
            }
        }
        return null;
    }

    public Set getInspections() {
        return this.inspections;
    }

    public void setInspections(Set inspections) {
        this.inspections = inspections;
    }

    public Date getBaselineDate() {
        return this.baselineDate;
    }

    public void setBaselineDate(Date baselineDate) {
        this.baselineDate = baselineDate;
    }

    public Set getWaterUserCommittees() {
        return this.waterUserCommittees;
    }

    public void setWaterUserCommittees(Set waterUserCommittees) {
        this.waterUserCommittees = waterUserCommittees;
    }

    public String getBaselinePending() {
        return this.baselinePending;
    }

    public void setBaselinePending(String baselinePending) {
        this.baselinePending = baselinePending;
    }

	public void setCareTakerName(String careTakerName) {
		this.careTakerName = careTakerName;
	}

	public void setCareTakerTel(String careTakerTel) {
		this.careTakerTel = careTakerTel;
	}

	public String getCareTakerName() {
		return careTakerName;
	}

	public String getCareTakerTel() {
		return careTakerTel;
	}   
}
