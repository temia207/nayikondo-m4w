/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.m4w.workflow.mobile.view;

import java.util.Vector;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.List;
import org.openxdata.model.FormData;
import org.openxdata.model.FormDef;

/**
 *
 * @author kay
 */
public class InspectionDataView {

        private Display display;
        private List list;
        private Vector formDataVctr;

        public InspectionDataView(Display display) {
                this.display = display;
                list = new List("Inspection Data", List.IMPLICIT);
        }

        public void show() {
                display.setCurrent(list);
        }
        public void clear(){
                formDataVctr = null;
                list.deleteAll();
        }

        public void setFormData(Vector formDataVctr, FormDef def) {
                this.formDataVctr = formDataVctr;
                list.deleteAll();
                for (int i = 0; i < formDataVctr.size(); i++) {
                        FormData data = (FormData) formDataVctr.elementAt(i);
                        data.setDef(def);
                        data.buildDataDescription();
                        list.append(data.toString(), null);
                }

        }

        public void setCommandListener(CommandListener listener) {
                list.setCommandListener(listener);
        }

        public void addCommand(Command command) {
                list.addCommand(command);
        }

        public FormData getSelected() {
                int idx = list.getSelectedIndex();
                if (idx == -1) {
                        return null;
                }
                Object obj = formDataVctr.elementAt(idx);
                return (FormData) obj;
        }
        public Displayable getDisplayable(){
                return list;
        }

        public Vector getFormDataVctr() {
                return formDataVctr;
        }

        public void remove(FormData formData) {
        }

        
}
