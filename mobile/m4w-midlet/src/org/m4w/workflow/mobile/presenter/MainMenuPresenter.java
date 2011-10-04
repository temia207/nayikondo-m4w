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
import org.m4w.workflow.mobile.command.M4WCommands;
import org.m4w.workflow.mobile.db.M4WStorage;
import org.m4w.workflow.mobile.view.MainMenuView;
import org.openxdata.communication.TransportLayer;
import org.openxdata.db.util.StorageListener;
import org.openxdata.util.AlertMessageListener;
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
        AlertMessageListener, StorageListener {

        private Display display;
        private MainMenuView view;
        private ActionDispatcher dispatcher;
        private static final int ASSIGNED_WORK_IDX = 0;
        private static final int SURVEY_IDX = 1;
        private static final int GET_INSPCTN_IDX = 2;
        private final WorkitemManager wirManager;
        private Command currCmd;

        public MainMenuPresenter(Display display,
                MainMenuView view, ActionDispatcher dispatcher,
                WorkitemManager wirManager,
                TransportLayer tlayer) {
                this.display = display;
                this.view = view;
                this.dispatcher = dispatcher;
                this.wirManager = wirManager;
                this.dispatcher.registerListener(M4WCommands.mainMenuCmd, this);
                initCommands();


        }

        private void initCommands() {
                view.addCommand("Available Work");
                view.addCommand("Do Inspection");
                view.addCommand("Get Inspection Form");
                view.addCommandListener(this);
                wirManager.getWirPresenter().getView().addCommand(M4WCommands.mainMenuCmd);

        }

        public void commandAction(Command cmnd, Displayable dsplbl) {
                if (cmnd == List.SELECT_COMMAND) {
                        processSelection(view.getSelectedIndex());
                } else if (cmnd == M4WCommands.mainMenuCmd) {
                }

        }

        private void processSelection(int selectedIndex) {
                if (ASSIGNED_WORK_IDX == selectedIndex) {
                        wirManager.showWorkItems();
                } else if (SURVEY_IDX == selectedIndex) {
                        dispatcher.fireAction(M4WCommands.inspctCmd, this, null);
                } else if (GET_INSPCTN_IDX == selectedIndex) {
                        downloadInspectionForm(true);
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

                dispatcher.fireAction(M4WCommands.dldInspection, this, null);

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
}
