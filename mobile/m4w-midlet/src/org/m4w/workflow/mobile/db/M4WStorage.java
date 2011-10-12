package org.m4w.workflow.mobile.db;

import java.util.Enumeration;
import org.m4w.workflow.mobile.ServerSettings;
import org.m4w.workflow.mobile.StudyIDFormID;
import org.openxdata.db.OpenXdataDataStorage;
import org.openxdata.db.util.Settings;
import org.openxdata.db.util.Storage;
import org.openxdata.db.util.StorageFactory;
import org.openxdata.db.util.StorageListener;
import org.openxdata.model.FormDef;
import org.openxdata.model.StudyDef;

public class M4WStorage {

        public static final String M4W_SETTINGS = "m4w.settings";

        public static FormDef getCurrentFormDef(StudyIDFormID studyFormId) {
                StudyDef studyDef = OpenXdataDataStorage.getStudy(studyFormId.getStudyID());
                int formId = studyFormId.getFormID();
                FormDef frmDef = studyDef.getForm(formId);
                return frmDef;
        }

        public static ServerSettings getServerSettings() {
                Settings settings = new Settings(M4W_SETTINGS, true);
                String study = settings.getSetting("inspectionStudyID");
                String form = settings.getSetting("inspectionFormID");
                if (study != null && form != null) {
                        ServerSettings stdy = new ServerSettings();
                        stdy.setInspectionStudy(study);
                        stdy.setInspectionForm(form);
                        Enumeration keys = settings.keys();
                        while (keys.hasMoreElements()) {
                                String key = (String) keys.nextElement();
                                stdy.getTable().put(key, settings.getSetting(key));
                        }
                        return stdy;
                }
                return null;
        }

        public static StudyIDFormID getInspxnStudyIDFormID() {
                ServerSettings studyIDFormID = getServerSettings();
                if (studyIDFormID == null) {
                        return null;
                }
                StudyIDFormID sidfid = new StudyIDFormID(studyIDFormID.getInsptnStudyID(), studyIDFormID.getInsptnFormID(), null);
                if (sidfid.getStudyID() == -1 || sidfid.getFormID() == -1) {
                        return null;
                }
                return sidfid;
        }

        public static StudyIDFormID getNewWaterPointStudyIDFormID() {
                ServerSettings studyIDFormID = getServerSettings();
                if (studyIDFormID == null) {
                        return null;
                }
                StudyIDFormID sidfid = new StudyIDFormID(studyIDFormID.getNewWaterPointStudyID(), studyIDFormID.getNewWaterPointFormID(), null);
                if (sidfid.getStudyID() == -1 || sidfid.getFormID() == -1) {
                        return null;
                }
                return sidfid;
        }

        public static void saveStudyFormID(ServerSettings studyFormId) {
                Settings settings = new Settings(M4W_SETTINGS);
                settings.setSetting("inspectionStudyID", studyFormId.getInsptnStudyID() + "");
                settings.setSetting("inspectionFormID", studyFormId.getInsptnFormID() + "");
                Enumeration keys = studyFormId.getTable().keys();
                while (keys.hasMoreElements()) {
                        String kay = (String) keys.nextElement();
                        settings.setSetting(kay, (String) studyFormId.getTable().get(kay));
                }
                settings.saveSettings();

        }

        public static boolean hasInspectionData(StorageListener listener) {
                ServerSettings studyFormId = getServerSettings();
                if (studyFormId == null) {
                        return false;
                }
                String string = OpenXdataDataStorage.getFormDataStorageName(studyFormId.getInsptnStudyID(), studyFormId.getInsptnFormID());
                Storage storage = StorageFactory.getStorage(string, listener);
                return storage.getNumRecords() > 0;
        }
}
