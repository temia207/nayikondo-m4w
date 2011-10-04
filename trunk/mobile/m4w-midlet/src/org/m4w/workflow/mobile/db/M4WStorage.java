package org.m4w.workflow.mobile.db;

import org.m4w.workflow.mobile.StudyFormId;
import org.openxdata.db.OpenXdataDataStorage;
import org.openxdata.db.util.Settings;
import org.openxdata.db.util.Storage;
import org.openxdata.db.util.StorageFactory;
import org.openxdata.db.util.StorageListener;
import org.openxdata.model.FormDef;
import org.openxdata.model.StudyDef;

public class M4WStorage {

        public static final String M4W_SETTINGS = "m4w.settings";

        public static FormDef getCurrentFormDef(StudyFormId studyFormId) {
                StudyDef studyDef = OpenXdataDataStorage.getStudy(studyFormId.getStudyID());
                int formId = studyFormId.getFormID();
                FormDef frmDef = studyDef.getForm(formId);
                return frmDef;
        }

        public static StudyFormId getStudyFormID() {
                Settings settings = new Settings(M4W_SETTINGS, true);
                String study = settings.getSetting("inspectionStudyID");
                String form = settings.getSetting("inspectionFormID");
                if (study != null && form != null) {
                        int studyID = Integer.parseInt(study);
                        int formID = Integer.parseInt(form);
                        return new StudyFormId(studyID, formID);
                }
                return null;
        }

        public static void saveStudyFormID(StudyFormId studyFormId) {
                Settings settings = new Settings(M4W_SETTINGS);
                settings.setSetting("inspectionStudyID", studyFormId.getStudyID() + "");
                settings.setSetting("inspectionFormID", studyFormId.getFormID() + "");
                settings.saveSettings();

        }

        public static boolean hasInspectionData(StorageListener listener){
                StudyFormId studyFormId = getStudyFormID();
                String string = OpenXdataDataStorage.getFormDataStorageName(studyFormId.getStudyID(), studyFormId.getFormID());
                Storage storage = StorageFactory.getStorage(string, listener);
                return storage.getNumRecords()>0;       
        }
}
