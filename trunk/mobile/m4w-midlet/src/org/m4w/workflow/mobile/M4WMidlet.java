package org.m4w.workflow.mobile;

import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;
import org.m4w.workflow.mobile.presenter.MainMenuPresenter;

import org.openxdata.forms.LogonListener;
import org.openxdata.forms.LogoutListener;

public class M4WMidlet extends MIDlet implements LogonListener, LogoutListener {

       private MainMenuPresenter menuPresenter;

        public M4WMidlet() {
                super();
                M4WFactory.setWfMainForm(this);
                M4WFactory.setDisplay(Display.getDisplay(this));
                M4WFactory.init();
                menuPresenter = M4WFactory.getMainMenuPresenter();

        }

        protected void startApp() throws MIDletStateChangeException {
                try {
                        M4WFactory.getUserMgr().logOn();
                } catch (Throwable e) {
                        e.printStackTrace();
                        throw new RuntimeException(e.toString());
                }
        }

  

        public boolean onLoggedOn() {
                menuPresenter.show();
                return false;
        }

        protected void pauseApp() {
        }

        protected void destroyApp(boolean arg0) {
                M4WFactory.dispose();
        }

        public void onLogonCancel() {
                destroyApp(false);
                notifyDestroyed();
        }

        public void onLogout() {
        }
}
