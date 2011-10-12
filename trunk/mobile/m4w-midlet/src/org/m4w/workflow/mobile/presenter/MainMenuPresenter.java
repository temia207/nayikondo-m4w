/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.m4w.workflow.mobile.presenter;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.List;
import org.m4w.workflow.mobile.M4WDldManager;
import org.m4w.workflow.mobile.M4WMidlet;
import org.m4w.workflow.mobile.ServerSettings;
import org.m4w.workflow.mobile.StudyIDFormID;
import org.m4w.workflow.mobile.command.M4WCommands;
import org.m4w.workflow.mobile.db.M4WStorage;
import org.m4w.workflow.mobile.view.MainMenuView;
import org.openxdata.communication.ConnectionSettings;
import org.openxdata.communication.TransportLayer;
import org.openxdata.communication.TransportLayerListener;
import org.openxdata.db.util.Persistent;
import org.openxdata.db.util.StorageListener;
import org.openxdata.model.ResponseHeader;
import org.openxdata.util.AlertMessageListener;
import org.openxdata.workflow.mobile.Factory;
import org.openxdata.workflow.mobile.WorkitemManager;
import org.openxdata.workflow.mobile.command.ActionDispatcher;
import org.openxdata.workflow.mobile.command.ActionListener;
import org.openxdata.workflow.mobile.command.Viewable;
import org.openxdata.workflow.mobile.util.AlertMsgHelper;

/**
 *
 * @author kay
 */
public class MainMenuPresenter implements CommandListener, ActionListener, Viewable,
        AlertMessageListener, StorageListener, TransportLayerListener {

        private Display display;
        private MainMenuView view;
        private ActionDispatcher dispatcher;
        private static final int ASSIGNED_WORK_IDX = 0;
        private static final int SURVEY_IDX = 1;
        private static final int GET_INSPCTN_IDX = 2;
        private static final int COLLECT_DATA = 3;
        private final WorkitemManager wirManager;
        private Command currCmd;
        private int currIdx;
        private final M4WDldManager dldManager;

        public MainMenuPresenter(Display display,
                MainMenuView view, ActionDispatcher dispatcher,
                WorkitemManager wirManager,
                TransportLayer tlayer,
                M4WDldManager dldManager) {
                this.display = display;
                this.view = view;
                this.dispatcher = dispatcher;
                this.wirManager = wirManager;
                this.dldManager = dldManager;
                this.dispatcher.registerListener(M4WCommands.mainMenuCmd, this);
                initCommands();



        }

        private void initCommands() {
                view.addCommand("Available Work");
                view.addCommand("Do Inspection");
                view.addCommand("Update Forms");
                view.addCommand("Register New Waterpoint");
                view.addCommandListener(this);
                wirManager.getWirPresenter().getView().addCommand(M4WCommands.mainMenuCmd);

        }

        public void commandAction(Command cmnd, Displayable dsplbl) {
                currCmd = cmnd;
                try {
                        if (cmnd == List.SELECT_COMMAND) {
                                processSelection(view.getSelectedIndex());
                        } else if (cmnd == M4WCommands.mainMenuCmd) {
                        }
                } catch (Exception ex) {
                        AlertMsgHelper.showError(display, dsplbl, "Error Occured: " + ex.getMessage());
                        ex.printStackTrace();
                }

        }

        private void processSelection(int selectedIndex) {
                currIdx = selectedIndex;
                if (ASSIGNED_WORK_IDX == selectedIndex) {
                        wirManager.showWorkItems();
                } else if (SURVEY_IDX == selectedIndex) {
                        doSurvey();
                } else if (GET_INSPCTN_IDX == selectedIndex) {
                        downloadInspectionForm(true);
                } else if (COLLECT_DATA == selectedIndex) {
                       registerNewWaterPoint();

                }
        }

        public void show() {
                view.showYourSelf();
        }

        public boolean handle(Command cmnd, Viewable vwbl, Object o) {
                if (cmnd == M4WCommands.mainMenuCmd) {
                        view.showYourSelf();
                }
                return false;
        }

        public Displayable getDisplay() {
                return view.getDisplayable();

        }

        private void downloadInspectionForm(boolean confirm) {
                if (M4WStorage.hasInspectionData(this)) {
                        AlertMsgHelper.showMsg(display, view.getDisplayable(), "You already have collected Inspection Data Please First"
                                + " Upload It Or Delete It");
                        return;
                }

                if (confirm) {
                        currCmd = M4WCommands.dldInspection;
                        AlertMsgHelper.showConfirm(display, view.getDisplayable(), "Are You Sure You Want To Download New Forms", this);
                        return;
                }

                dldManager.getFormVersionID(this);

        }

        private void doSurvey() {
                StudyIDFormID sidfid = M4WStorage.getInspxnStudyIDFormID();
                if (sidfid == null) {
                        this.dldManager.getFormVersionID(this);
                        return;
                }
                sidfid.setAction("InspectUpload");

                dispatcher.fireAction(M4WCommands.inspctCmd, this, sidfid);
        }

        private void registerNewWaterPoint() {
                StudyIDFormID sidfid = M4WStorage.getNewWaterPointStudyIDFormID();
                if (sidfid == null) {
                        this.dldManager.getFormVersionID(this);
                        return;
                }
                sidfid.setAction("NewWaterPoint");
                dispatcher.fireAction(M4WCommands.inspctCmd, this, sidfid);
        }

        public void onAlertMessage(byte b) {
                if (b != AlertMessageListener.MSG_OK) {
                        view.showYourSelf();
                        return;
                }
                if (currCmd == M4WCommands.dldInspection) {
                        downloadInspectionForm(false);
                }
        }

        public void errorOccured(String string, Exception excptn) {
                AlertMsgHelper.showError(display, view.getDisplayable(), "" + string + ": " + excptn);
        }

        public void uploaded(Persistent prstnt, Persistent prstnt1) {
        }

        public void downloaded(Persistent dataOutParams, Persistent dataOut) {
                ResponseHeader rh = (ResponseHeader) dataOutParams;
                if (rh == null || !rh.isSuccess()) {
                        return;// TODO show appropriate message
                }
                try {
                        if (dataOut instanceof ServerSettings) {
                                if (((ServerSettings) dataOut).getTable().isEmpty()) {
                                        AlertMsgHelper.showMsg(display, view.getDisplayable(), "There Are Now Available Forms");
                                        return;
                                }

                                M4WStorage.saveStudyFormID((ServerSettings) dataOut);
                                onSettingDownloaded(dataOut);
                        }
                } catch (Exception e) {
                        M4WMidlet.handleEx(view.getDisplayable(), e);
                }
        }

        public void cancelled() {
        }

        public void updateCommunicationParams() {
                String url = ConnectionSettings.getHttpUrl();
                if (url != null) {
                        Factory.getTransportLayer().setCommunicationParameter(TransportLayer.KEY_HTTP_URL, url);
                }
        }

        private void onSettingDownloaded(Persistent dataOut) {
                if (currIdx == SURVEY_IDX) {
                        doSurvey();
                } else if (currIdx == COLLECT_DATA) {
                        registerNewWaterPoint();
                } else {
                        view.showYourSelf();
                }
        }
}
