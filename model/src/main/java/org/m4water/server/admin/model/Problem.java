package org.m4water.server.admin.model;
// Generated Sep 7, 2011 12:43:34 PM by Hibernate Tools 3.2.1.GA


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Problem generated by hbm2java
 */
public class Problem  extends AbstractEditable<String> {


     private Waterpoint waterpoint;
     private Date dateProblemReported;
     private String problemDescsription;
     private String problemStatus;
     private Set problemLogs = new HashSet(0);

    public Problem() {
        
    }

	
    public Problem(String problemId, Waterpoint waterpoint, Date dateProblemReported, String problemDescsription, String problemStatus) {
        this.id = problemId;
        this.waterpoint = waterpoint;
        this.dateProblemReported = dateProblemReported;
        this.problemDescsription = problemDescsription;
        this.problemStatus = problemStatus;
    }
    public Problem(String problemId, Waterpoint waterpoint, Date dateProblemReported, String problemDescsription, String problemStatus, Set problemLogs) {
       this.id = problemId;
       this.waterpoint = waterpoint;
       this.dateProblemReported = dateProblemReported;
       this.problemDescsription = problemDescsription;
       this.problemStatus = problemStatus;
       this.problemLogs = problemLogs;
    }
   
    public String getProblemId() {
        return this.id;
    }
    
    public void setProblemId(String problemId) {
        this.id = problemId;
    }
    public Waterpoint getWaterpoint() {
        return this.waterpoint;
    }
    
    public void setWaterpoint(Waterpoint waterpoint) {
        this.waterpoint = waterpoint;
    }
    public Date getDateProblemReported() {
        return this.dateProblemReported;
    }
    
    public void setDateProblemReported(Date dateProblemReported) {
        this.dateProblemReported = dateProblemReported;
    }
    public String getProblemDescsription() {
        return this.problemDescsription;
    }
    
    public void setProblemDescsription(String problemDescsription) {
        this.problemDescsription = problemDescsription;
    }
    public String getProblemStatus() {
        return this.problemStatus;
    }
    
    public void setProblemStatus(String problemStatus) {
        this.problemStatus = problemStatus;
    }
    public Set getProblemLogs() {
        return this.problemLogs;
    }
    
    public void setProblemLogs(Set problemLogs) {
        this.problemLogs = problemLogs;
    }

    public boolean isOpen() {
        return problemStatus!=null&&problemStatus.equals("open");
    }




}


