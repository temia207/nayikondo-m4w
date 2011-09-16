/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cwf.client.views;

import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.google.gwt.core.client.GWT;
import org.cwf.client.AppMessages;

/**
 *
 * @author victor
 */
public class DashBoardView extends ContentPanel {

    final AppMessages appMessages = GWT.create(AppMessages.class);

    public DashBoardView() {
        initialize();
    }

    private void initialize() {
    }
}
