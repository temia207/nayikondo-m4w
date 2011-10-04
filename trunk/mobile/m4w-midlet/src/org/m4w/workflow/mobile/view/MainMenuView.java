/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.m4w.workflow.mobile.view;

import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.List;

/**
 *
 * @author kay
 */
public class MainMenuView {

        private List list;
        private Display display;

        public MainMenuView(Display display) {
                this.list = new List("M4W Menu", List.IMPLICIT);
                this.display = display;
        }

        public void addCommand(String string) {
                list.append(string, null);
        }

        public void addCommandListener(CommandListener listerner) {
                list.setCommandListener(listerner);
        }

        public int getSelectedIndex() {
                return list.getSelectedIndex();
        }

        public Displayable getDisplayable(){
                return list;
        }

        public void showYourSelf() {
                display.setCurrent(list);
        }
}
