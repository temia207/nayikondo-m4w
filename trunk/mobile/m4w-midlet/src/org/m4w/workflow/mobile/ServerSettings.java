/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.m4w.workflow.mobile;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Hashtable;
import org.openxdata.db.util.Persistent;
import org.openxdata.db.util.PersistentHelper;

/**
 *
 * @author kay
 */
public class ServerSettings implements Persistent {

        private Hashtable table;
        private String msg;

        public ServerSettings() {
                table = new Hashtable();
        }

        public ServerSettings(Hashtable table) {
                this.table = table;
        }

        public int getInsptnFormID() {
                String formID = (String) table.get("inspectionFormID");
                if (formID == null) {
                        return -1;
                }
                try {
                        return Integer.parseInt(formID);
                } catch (NumberFormatException ex) {
                        ex.printStackTrace();
                        return -1;
                }

        }

        public int getInsptnStudyID() {
                return getParamIntValue("inspectionStudyID");
        }

        public int getParamIntValue(final String inspectionStudyID) {
                int studyId;
                String studyID = (String) table.get(inspectionStudyID);
                if (studyID == null) {
                        studyId = -1;
                } else {
                        try {
                                studyId = Integer.parseInt(studyID);
                        } catch (NumberFormatException ex) {
                                ex.printStackTrace();
                                studyId = -1;
                        }
                }
                return studyId;
        }

        public void setInspectionStudy(String studyId) {
                table.put("inspectionStudyID", studyId);
        }

        public void setInspectionForm(String formID) {
                table.put("inspectionFormID", formID);
        }

        public int getNewWaterPointFormID() {
                return getParamIntValue("newWaterPointFormID");
        }

        public int getNewWaterPointStudyID(){
                return getParamIntValue("newWaterPointStudyID");
        }

        public Hashtable getTable() {
                return table;
        }

        public void write(DataOutputStream stream) throws IOException {
                PersistentHelper.write(table, stream);
        }

        public void read(DataInputStream stream) throws IOException, InstantiationException, IllegalAccessException {
                table = PersistentHelper.read(stream);
        }
}
