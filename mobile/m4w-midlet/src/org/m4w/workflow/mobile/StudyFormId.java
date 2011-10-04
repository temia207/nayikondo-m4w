/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.m4w.workflow.mobile;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import org.openxdata.db.util.Persistent;

/**
 *
 * @author kay
 */
public class StudyFormId implements Persistent {

        private int studyID;
        private int formID;

        public StudyFormId() {
        }

        public StudyFormId(int studyID, int formID) {
                this.studyID = studyID;
                this.formID = formID;
        }

        public void setStudyID(int studyID) {
                this.studyID = studyID;
        }

        public void setFormID(int formID) {
                this.formID = formID;
        }

        public int getFormID() {
                return formID;
        }

        public int getStudyID() {
                return studyID;
        }

        public void write(DataOutputStream stream) throws IOException {
                stream.writeInt(studyID);
                stream.writeInt(formID);
        }

        public void read(DataInputStream stream) throws IOException, InstantiationException, IllegalAccessException {
                studyID = stream.readInt();
                formID = stream.readInt();
        }
}
