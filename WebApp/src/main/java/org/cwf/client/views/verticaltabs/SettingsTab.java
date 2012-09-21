/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cwf.client.views.verticaltabs;

import com.extjs.gxt.ui.client.Style.Scroll;
import org.cwf.client.custom.widget.VerticalTabItem;

/**
 *
 * @author victor
 */
public class SettingsTab extends VerticalTabItem {

    public SettingsTab(String title) {
        super(title);
        addStyleName("pad-text");
        setScrollMode(Scroll.AUTO);
        addText("Coming Soon!");
    }
}
