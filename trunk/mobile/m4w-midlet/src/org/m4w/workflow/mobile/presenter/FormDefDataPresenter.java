/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.m4w.workflow.mobile.presenter;

import java.util.Date;
import java.util.Vector;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.List;
import org.m4w.workflow.mobile.M4WDldManager;
import org.m4w.workflow.mobile.M4WFactory;
import org.m4w.workflow.mobile.M4WMidlet;
import org.m4w.workflow.mobile.StudyIDFormID;
import org.m4w.workflow.mobile.SurveyResponse;
import org.m4w.workflow.mobile.command.M4WCommands;
import org.m4w.workflow.mobile.db.M4WStorage;
import org.m4w.workflow.mobile.view.FormDefDataView;
import org.openxdata.db.OpenXdataDataStorage;
import org.openxdata.db.util.Persistent;
import org.openxdata.forms.FormManager;
import org.openxdata.forms.LogoutListener;
import org.openxdata.model.FormData;
import org.openxdata.model.FormDef;
import org.openxdata.model.ResponseHeader;
import org.openxdata.model.StudyDef;
import org.openxdata.model.UserListStudyDefList;
import org.openxdata.util.AlertMessageListener;
import org.openxdata.workflow.mobile.DownloadManager;
import org.openxdata.workflow.mobile.FormListenerAdaptor;
import org.openxdata.workflow.mobile.command.ActionDispatcher;
import org.openxdata.workflow.mobile.command.ActionListener;
import org.openxdata.workflow.mobile.command.Viewable;
import org.openxdata.workflow.mobile.db.WFStorage;
import org.openxdata.workflow.mobile.util.AlertMsgHelper;

/**
 *
 * @author kay
 */
public class FormDefDataPresenter extends FormListenerAdaptor implements ActionListener, CommandListener, Viewable, AlertMessageListener {

        private Display display;
        private ActionDispatcher dispatcher;
        private final M4WDldManager dldManager;
        private final DownloadManager wfDldManager;
        private Viewable vwbl;
        private final FormDefDataView view;
        private StudyIDFormID mStudyFormId;
        private static Command newDataCmd = new Command("New", Command.SCREEN, 1);
        private static Command cmdUploadData = new Command("Upload Data", Command.SCREEN, 0);
        private static Command cmdDelete = new Command("Delete", Command.SCREEN, 0);
        private Command currCommand;

        public FormDefDataPresenter(Display display, ActionDispatcher dispatcher,
                M4WDldManager dldManager,
                FormDefDataView view,
                DownloadManager wfDldManager) {
                this.display = display;
                this.dispatcher = dispatcher;
                this.dldManager = dldManager;
                this.view = view;
                this.wfDldManager = wfDldManager;
                dispatcher.registerListener(M4WCommands.inspctCmd, this);
                dispatcher.registerListener(M4WCommands.dldInspection, this);
                initCommands();

        }

        public boolean handle(Command cmnd, Viewable vwbl, Object o) {
                this.vwbl = vwbl;
                if (cmnd == M4WCommands.inspctCmd) {
                        mStudyFormId = (StudyIDFormID) o;
                        showFormDataOrDownload(mStudyFormId);
                }
                return false;
        }

        private void collectNewFormData(FormDef frmDef) {
                FormData formData = new FormData(frmDef);
                renderFormData(formData);
        }

        private void renderFormData(FormData formData) {
                getFormManager().showForm(true, formData, true, vwbl.getDisplay());
        }

        public void downloaded(Persistent dataOutParams, Persistent dataOut) {
                ResponseHeader rh = (ResponseHeader) dataOutParams;
                if (rh == null || !rh.isSuccess()) {
                        return;// TODO show appropriate message
                }
                if (dataOut instanceof UserListStudyDefList) {
                        WFStorage.saveUserListStudyDefList((UserListStudyDefList) dataOut);
                        showFormData(mStudyFormId);
                }
        }

        public void errorOccured(String errorMsg, Exception excptn) {
                AlertMsgHelper.showMsg(display, vwbl.getDisplay(), errorMsg);
                if (excptn != null) {
                        excptn.printStackTrace();
                }
        }

        private void showFormDataOrDownload(StudyIDFormID studyFormId) {
                StudyDef studyDef = OpenXdataDataStorage.getStudy(studyFormId.getStudyID());
                this.mStudyFormId = studyFormId;
                if (studyDef == null) {
                        wfDldManager.downloadStudies(this);
                } else {
                        showFormData(studyFormId);
                }
                //frmManager.showForm(true, "formVarName", false, display, vwbl.getDisplay(), this);
        }

        private void showFormData(StudyIDFormID studyFormId) {
                FormDef frmDef = M4WStorage.getCurrentFormDef(studyFormId);
                Vector formDataVctr = OpenXdataDataStorage.getFormData(studyFormId.getStudyID(), studyFormId.getFormID());
                if (formDataVctr == null) {
                        collectNewFormData(frmDef);
                } else {
                        view.setFormData(formDataVctr, frmDef);
                        view.show();
                }

        }

        public FormManager getFormManager() {

                FormManager formManager = new FormManager("Workflow Mobile", display,
                        this, view.getDisplayable(),
                        M4WFactory.getTransportLayer(), this, new LogoutListener() {

                        public void onLogout() {
                        }
                });
                formManager.setUserManager(M4WFactory.getUserMgr());
                return formManager;
        }

        public void saveFormData(FormData data, boolean isNew) {
                String formName = data.getDef().getVariableName();
                data.setDateValue("/" + formName + "/endtime", new Date());
                OpenXdataDataStorage.saveFormData(mStudyFormId.getStudyID(), data);
        }

        public boolean beforeFormSaved(FormData data, boolean isNew) {
                saveFormData(data, isNew);
                showFormData(mStudyFormId);
                return false;
        }

        public void commandAction(Command cmnd, Displayable dsplbl) {
                currCommand = cmnd;
                try {
                        if (cmnd == newDataCmd) {
                                collectNewFormData(M4WStorage.getCurrentFormDef(mStudyFormId));
                        } else if (cmnd == cmdUploadData) {
                                uploadData();
                        } else if (cmnd == List.SELECT_COMMAND) {
                                openSelectedFormData();
                        } else if (cmnd == cmdDelete) {
                                deleteFormData(true);
                        } else {
                                dispatcher.fireAction(cmnd, this, null);
                        }
                } catch (Exception ec) {
                        AlertMsgHelper.showMsg(display, dsplbl, "Error Occured: " + ec.getMessage());
                        ec.printStackTrace();
                }

        }

        public void show() {
                view.show();
        }

        public Displayable getDisplay() {
                return view.getDisplayable();
        }

        private void initCommands() {
                view.setCommandListener(this);
                view.addCommand(M4WCommands.mainMenuCmd);
                view.addCommand(newDataCmd);
                view.addCommand(cmdUploadData);
                view.addCommand(cmdDelete);
        }

        private void uploadData() {
                Vector formData = //OpenXdataDataStorage.getFormData(studyFormId.getStudyID(), studyFormId.getFormID());
                        view.getFormDataVctr();
                this.dldManager.uploadData(formData, this, this.mStudyFormId.getAction());
        }

        public void uploaded(Persistent dataOutParams, Persistent dataOut) {
                try {
                        SurveyResponse response = (SurveyResponse) dataOut;
                        String message = response.isSuccess() ? "Data Uploaded Succesfully" : response.getMessage();
                        AlertMsgHelper.showMsg(display, vwbl.getDisplay(), message);
                        if (!response.isSuccess()) {
                                return;
                        }
                        Vector formData = view.getFormDataVctr();
                        for (int i = 0; i < formData.size(); i++) {
                                FormData formData1 = (FormData) formData.elementAt(i);
                                OpenXdataDataStorage.deleteFormData(mStudyFormId.getStudyID(), formData1);
                        }
                } catch (Exception ex) {
                        M4WMidlet.handleEx(view.getDisplayable(), ex);
                }
        }

        private void openSelectedFormData() {
                FormData frmData = view.getSelected();
                if (frmData != null) {
                        renderFormData(frmData);
                }
        }

        public boolean beforeFormDelete(FormData data) {
                OpenXdataDataStorage.deleteFormData(mStudyFormId.getStudyID(), data);
                view.clear();
                showFormData(mStudyFormId);
                return false;
        }

        private void deleteFormData(boolean confirm) {
                if (confirm) {
                        AlertMsgHelper.showConfirm(display, view.getDisplayable(), "Are You Sure?", this);
                        return;
                }
                FormData formData = view.getSelected();
                OpenXdataDataStorage.deleteFormData(mStudyFormId.getStudyID(), formData);
                view.clear();
                showFormData(mStudyFormId);
                view.show();
        }

        public void onAlertMessage(byte b) {
                if (b != AlertMessageListener.MSG_OK) {
                        view.show();
                        return;
                }
                if (currCommand == cmdDelete) {
                        deleteFormData(false);
                }
        }
}
