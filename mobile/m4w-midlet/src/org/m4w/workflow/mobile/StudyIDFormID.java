/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.m4w.workflow.mobile;

/**
 *
 * @author kay
 */
public class StudyIDFormID {

        private int studyId;
        private int formID;
        private String action;

        public StudyIDFormID() {
        }

        public StudyIDFormID(int studyId, int formID, String action) {
                this.studyId = studyId;
                this.formID = formID;
                this.action = action;
        }

        public int getFormID() {
                return formID;
        }

        public void setFormID(int formID) {
                this.formID = formID;
        }

        public int getStudyID() {
                return studyId;
        }

        public void setStudyId(int studyId) {
                this.studyId = studyId;
        }

        public String getAction() {
                return action;
        }

        public void setAction(String action) {
                this.action = action;
        }

        
}
