/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cwf.client.views.verticaltabs;

import com.extjs.gxt.ui.client.Style.Scroll;
import org.cwf.client.custom.widget.VerticalTabItem;
import org.cwf.client.views.HomeView;

/**
 *
 * @author victor
 */
public class UsersTab extends VerticalTabItem {

    private HomeView parentView;

    public UsersTab(String title, HomeView parentView) {
        super(title);
        addStyleName("pad-text");
        setScrollMode(Scroll.AUTO);
        this.parentView = parentView;
        addText("Coming Soon!");
    }
}
