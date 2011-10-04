package org.m4w.workflow.mobile;

import javax.microedition.lcdui.Display;
import org.m4w.workflow.mobile.presenter.InspectionDataPresenter;
import org.m4w.workflow.mobile.presenter.MainMenuPresenter;
import org.m4w.workflow.mobile.view.InspectionDataView;
import org.m4w.workflow.mobile.view.MainMenuView;

import org.openxdata.communication.TransportLayer;
import org.openxdata.forms.UserManager;
import org.openxdata.workflow.mobile.Factory;
import org.openxdata.workflow.mobile.WorkitemManager;
import org.openxdata.workflow.mobile.command.ActionDispatcher;

public class M4WFactory {

        private static ActionDispatcher commandDispatcher;
        private static M4WMidlet m4wMainForm;
        private static M4WDldManager m4wDldManager;
        private static MainMenuPresenter mainMenuPresenter;
        private static InspectionDataPresenter inspectionDataPresenter;
        private static MainMenuView mainMenuView;
        private static TransportLayer tLayer;
        private static UserManager userMgr;
        private static Display display;

        static {
                commandDispatcher = org.openxdata.workflow.mobile.Factory.commandDispatcher;
        } 

        public static Display getDisplay() {
                return display;
        }

        public static void setWfMainForm(M4WMidlet wfMainForm) {
                M4WFactory.m4wMainForm = wfMainForm;
        }

        public static void setDisplay(Display display) {
                M4WFactory.display = display;
                initTransportLayer();
                Factory.setDisplay(display);
                Factory.settLayer(tLayer);
                Factory.setUserMgr(getUserMgr());
        }

        private static void initTransportLayer() {
                if (display == null) {
                        return;
                }
                tLayer = new TransportLayer();
                tLayer.setDisplay(display);
                tLayer.setPrevScreen(getMainMenuView().getDisplayable());
                tLayer.setDefaultCommunicationParameter(TransportLayer.KEY_HTTP_URL,
                        "http://localhost:8888/wirdownload");
        }

        public static UserManager getUserMgr() {
                if (userMgr == null) {
                        userMgr = new UserManager(display, getMainMenuView().getDisplayable(),
                                "Workflow Mobile", m4wMainForm);
                }
                return userMgr;
        }

        public static TransportLayer getTransportLayer() {
                return tLayer;
        }

        public static MainMenuPresenter getMainMenuPresenter() {
                if (mainMenuPresenter == null) {
                        mainMenuPresenter = new MainMenuPresenter(display, getMainMenuView(), commandDispatcher,
                                getWirManger(), getTransportLayer());
                }
                return mainMenuPresenter;
        }

        public static MainMenuView getMainMenuView() {
                if (mainMenuView == null) {
                        mainMenuView = new MainMenuView(display);
                }
                return mainMenuView;
        }

        public static void dispose() {
                commandDispatcher.clear();
                m4wMainForm = null;
                tLayer = null;
                userMgr = null;
                display = null;
                mainMenuPresenter = null;
                mainMenuView = null;
                inspectionDataPresenter = null;
                m4wDldManager = null;
                Factory.dispose();

        }

        public static WorkitemManager getWirManger() {
                WorkitemManager wirManager = org.openxdata.workflow.mobile.Factory.getWirManager();
                return wirManager;
        }

        public static InspectionDataPresenter getInspctnDataPrsntr() {
                if (inspectionDataPresenter == null) {
                        inspectionDataPresenter = new InspectionDataPresenter(display, commandDispatcher, getM4WDldManager(),                        new InspectionDataView(display), Factory.getDldMgr());
                }
                return inspectionDataPresenter;
        }

        public static M4WDldManager getM4WDldManager() {
                if (m4wDldManager == null) {
                        m4wDldManager = new M4WDldManager(tLayer, Factory.getDldMgr());
                        m4wDldManager.setUserMgr(getUserMgr());
                }
                return m4wDldManager;
        }

        public static void init() {
                getInspctnDataPrsntr();
        }
}
