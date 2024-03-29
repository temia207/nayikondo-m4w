package org.m4water.server.admin.model;
// Generated Sep 15, 2011 12:10:51 PM by Hibernate Tools 3.2.1.GA




/**
 * InspectionQuestions generated by hbm2java
 */
public class InspectionQuestions  extends AbstractEditable<String> {


     
     private InspectionQuestionType inspectionQuestionType;
     private Inspection inspection;
     private String question;
     private String answer;

    public InspectionQuestions() {
    }

	
    public InspectionQuestions(String id) {
        this.id = id;
    }
    public InspectionQuestions(String id, InspectionQuestionType inspectionQuestionType, Inspection inspection, String question, String answer) {
       this.id = id;
       this.inspectionQuestionType = inspectionQuestionType;
       this.inspection = inspection;
       this.question = question;
       this.answer = answer;
    }
   
    public String getQuestionId() {
        return this.id;
    }
    
    public void setQuestionId(String id) {
        this.id = id;
    }
    public InspectionQuestionType getInspectionQuestionType() {
        return this.inspectionQuestionType;
    }
    
    public void setInspectionQuestionType(InspectionQuestionType inspectionQuestionType) {
        this.inspectionQuestionType = inspectionQuestionType;
    }
    public Inspection getInspection() {
        return this.inspection;
    }
    
    public void setInspection(Inspection inspection) {
        this.inspection = inspection;
    }
    public String getQuestion() {
        return this.question;
    }
    
    public void setQuestion(String question) {
        this.question = question;
    }
    public String getAnswer() {
        return this.answer;
    }
    
    public void setAnswer(String answer) {
        this.answer = answer;
    }




}


