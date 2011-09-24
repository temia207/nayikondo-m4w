package org.m4water.server.admin.model;
// Generated Sep 7, 2011 11:37:27 AM by Hibernate Tools 3.2.1.GA


import java.util.Date;

/**
 * WaterFunctionality generated by hbm2java
 */
public class WaterFunctionality  extends AbstractEditable<Date> {


     
     private Waterpoint waterpoint;
     private String functionalityStatus;
     private Date dayNonFunctional;
     private String reason;
     private Date dateLastRepaired;
     private String detailsLastRepair;
     private Date dateLastMinorService;
     private Date dateLastMajorService;

    public WaterFunctionality() {
    }

    public WaterFunctionality(Date date, Waterpoint waterpoint, String functionalityStatus, Date dayNonFunctional, String reason, Date dateLastRepaired, String detailsLastRepair, Date dateLastMinorService, Date dateLastMajorService) {
       this.id = date;
       this.waterpoint = waterpoint;
       this.functionalityStatus = functionalityStatus;
       this.dayNonFunctional = dayNonFunctional;
       this.reason = reason;
       this.dateLastRepaired = dateLastRepaired;
       this.detailsLastRepair = detailsLastRepair;
       this.dateLastMinorService = dateLastMinorService;
       this.dateLastMajorService = dateLastMajorService;
    }
   
    public Date getDate() {
        return this.id;
    }
    
    public void setDate(Date date) {
        this.id = date;
    }
    public Waterpoint getWaterpoint() {
        return this.waterpoint;
    }
    
    public void setWaterpoint(Waterpoint waterpoint) {
        this.waterpoint = waterpoint;
    }
    public String getFunctionalityStatus() {
        return this.functionalityStatus;
    }
    
    public void setFunctionalityStatus(String functionalityStatus) {
        this.functionalityStatus = functionalityStatus;
    }
    public Date getDayNonFunctional() {
        return this.dayNonFunctional;
    }
    
    public void setDayNonFunctional(Date dayNonFunctional) {
        this.dayNonFunctional = dayNonFunctional;
    }
    public String getReason() {
        return this.reason;
    }
    
    public void setReason(String reason) {
        this.reason = reason;
    }
    public Date getDateLastRepaired() {
        return this.dateLastRepaired;
    }
    
    public void setDateLastRepaired(Date dateLastRepaired) {
        this.dateLastRepaired = dateLastRepaired;
    }
    public String getDetailsLastRepair() {
        return this.detailsLastRepair;
    }
    
    public void setDetailsLastRepair(String detailsLastRepair) {
        this.detailsLastRepair = detailsLastRepair;
    }
    public Date getDateLastMinorService() {
        return this.dateLastMinorService;
    }
    
    public void setDateLastMinorService(Date dateLastMinorService) {
        this.dateLastMinorService = dateLastMinorService;
    }
    public Date getDateLastMajorService() {
        return this.dateLastMajorService;
    }
    
    public void setDateLastMajorService(Date dateLastMajorService) {
        this.dateLastMajorService = dateLastMajorService;
    }




}


