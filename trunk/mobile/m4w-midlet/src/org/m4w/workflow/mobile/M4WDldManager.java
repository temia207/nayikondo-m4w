/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.m4w.workflow.mobile;

import java.util.Vector;
import org.openxdata.communication.ConnectionSettings;
import org.openxdata.communication.TransportLayer;
import org.openxdata.communication.TransportLayerListener;
import org.openxdata.db.util.Persistent;
import org.openxdata.db.util.PersistentInt;
import org.openxdata.forms.UserManager;
import org.openxdata.model.ResponseHeader;
import org.openxdata.model.StudyData;
import org.openxdata.workflow.mobile.DownloadManager;
import org.openxdata.workflow.mobile.model.WFRequest;

/**
 *
 * @author kay
 */
public class M4WDldManager {

        private TransportLayer tLayer;
        private UserManager userMgr;
        private final DownloadManager wfDldMgr;

        public M4WDldManager(TransportLayer tLayer, DownloadManager wfDldMgr) {
                this.tLayer = tLayer;
                this.wfDldMgr = wfDldMgr;
        }

        public void setUserMgr(UserManager userMgr) {
                this.userMgr = userMgr;
        }

        public void getFormVersionID(TransportLayerListener listener) {
                setCommunicationParams();
                WFRequest req = wfDldMgr.getRequest("DldSurvey", null);
                ServerSettings infoList = new ServerSettings();
                PersistentInt nullDataFromIn = new PersistentInt(-1);
                ResponseHeader rh = new ResponseHeader();
                tLayer.download(req, nullDataFromIn, rh, infoList, listener,
                        userMgr.getUserName(), userMgr.getPassword(),
                        "Checking Current For Current Form");
        }

        public void uploadData(Vector formData, TransportLayerListener listener, String action) {
                setCommunicationParams();
                WFRequest req = wfDldMgr.getRequest(action, null);
                Persistent nullDataFromOut = new SurveyResponse();
                ResponseHeader rh = new ResponseHeader();
                StudyData studyData = new StudyData(-1);
                studyData.addForms(formData);
                tLayer.upload(req, studyData, rh, nullDataFromOut, listener, userMgr.getUserName(), userMgr.getPassword(), "Uploading Data..");

        }

        public void setCommunicationParams() {
                String url = ConnectionSettings.getHttpUrl();
                if (url != null) {
                        tLayer.setCommunicationParameter(TransportLayer.KEY_HTTP_URL, url);
                }
        }
	
	public void reportProblem(Problem problem, TransportLayerListener listener){
		setCommunicationParams();
		WFRequest req = wfDldMgr.getRequest("ReportProblem", null);
		SurveyResponse response = new SurveyResponse();
		ResponseHeader rh = new ResponseHeader();
		tLayer.upload(req, problem, rh, response, listener, userMgr.getUserName(), userMgr.getPassword(), "Reporting problem...");
	}
}
