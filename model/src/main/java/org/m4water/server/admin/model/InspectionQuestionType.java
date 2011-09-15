package org.m4water.server.admin.model;
// Generated Sep 15, 2011 12:10:51 PM by Hibernate Tools 3.2.1.GA


import java.util.HashSet;
import java.util.Set;

/**
 * InspectionQuestionType generated by hbm2java
 */
public class InspectionQuestionType  implements java.io.Serializable {


     private String id;
     private String questionType;
     private Set inspectionQuestionses = new HashSet(0);

    public InspectionQuestionType() {
    }

	
    public InspectionQuestionType(String id) {
        this.id = id;
    }
    public InspectionQuestionType(String id, String questionType, Set inspectionQuestionses) {
       this.id = id;
       this.questionType = questionType;
       this.inspectionQuestionses = inspectionQuestionses;
    }
   
    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    public String getQuestionType() {
        return this.questionType;
    }
    
    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }
    public Set getInspectionQuestionses() {
        return this.inspectionQuestionses;
    }
    
    public void setInspectionQuestionses(Set inspectionQuestionses) {
        this.inspectionQuestionses = inspectionQuestionses;
    }




}


