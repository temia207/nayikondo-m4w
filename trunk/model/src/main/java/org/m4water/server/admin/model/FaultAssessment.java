package org.m4water.server.admin.model;
// Generated Sep 7, 2011 11:37:27 AM by Hibernate Tools 3.2.1.GA


import java.util.Date;

/**
 * FaultAssessment generated by hbm2java
 */
public class FaultAssessment  extends AbstractEditable {


     private int assessmentId;
     private Problem problem;
     private String faults;
     private Date date;
     private String assessedBy;
     private String typeOfRepairesNeeded;
     private String problemFixed;
     private String reasonNotFixed;
     private String repairsDone;
     private String recommendations;
     private String userId;

    public FaultAssessment() {
    }

	
    public FaultAssessment(int assessmentId, Problem problem, String userId) {
        this.assessmentId = assessmentId;
        this.problem = problem;
        this.userId = userId;
    }
    public FaultAssessment(int assessmentId, Problem problem, String faults, Date date, String assessedBy, String typeOfRepairesNeeded, String problemFixed, String reasonNotFixed, String repairsDone, String recommendations, String userId) {
       this.assessmentId = assessmentId;
       this.problem = problem;
       this.faults = faults;
       this.date = date;
       this.assessedBy = assessedBy;
       this.typeOfRepairesNeeded = typeOfRepairesNeeded;
       this.problemFixed = problemFixed;
       this.reasonNotFixed = reasonNotFixed;
       this.repairsDone = repairsDone;
       this.recommendations = recommendations;
       this.userId = userId;
    }
   
    public int getAssessmentId() {
        return this.assessmentId;
    }
    
    public void setAssessmentId(int assessmentId) {
        this.assessmentId = assessmentId;
    }
    public Problem getProblem() {
        return this.problem;
    }
    
    public void setProblem(Problem problem) {
        this.problem = problem;
    }
    public String getFaults() {
        return this.faults;
    }
    
    public void setFaults(String faults) {
        this.faults = faults;
    }
    public Date getDate() {
        return this.date;
    }
    
    public void setDate(Date date) {
        this.date = date;
    }
    public String getAssessedBy() {
        return this.assessedBy;
    }
    
    public void setAssessedBy(String assessedBy) {
        this.assessedBy = assessedBy;
    }
    public String getTypeOfRepairesNeeded() {
        return this.typeOfRepairesNeeded;
    }
    
    public void setTypeOfRepairesNeeded(String typeOfRepairesNeeded) {
        this.typeOfRepairesNeeded = typeOfRepairesNeeded;
    }
    public String getProblemFixed() {
        return this.problemFixed;
    }
    
    public void setProblemFixed(String problemFixed) {
        this.problemFixed = problemFixed;
    }
    public String getReasonNotFixed() {
        return this.reasonNotFixed;
    }
    
    public void setReasonNotFixed(String reasonNotFixed) {
        this.reasonNotFixed = reasonNotFixed;
    }
    public String getRepairsDone() {
        return this.repairsDone;
    }
    
    public void setRepairsDone(String repairsDone) {
        this.repairsDone = repairsDone;
    }
    public String getRecommendations() {
        return this.recommendations;
    }
    
    public void setRecommendations(String recommendations) {
        this.recommendations = recommendations;
    }
    public String getUserId() {
        return this.userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }




}


