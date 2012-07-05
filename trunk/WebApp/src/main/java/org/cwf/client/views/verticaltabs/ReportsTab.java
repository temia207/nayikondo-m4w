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
public class ReportsTab extends VerticalTabItem {
private HomeView parentView;
    public ReportsTab(String title,HomeView parentView) {
        super(title);
        addStyleName("pad-text");
        setScrollMode(Scroll.AUTO);
        this.parentView =parentView;
        addText("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas id tellus lorem, a facilisis lacus. Duis semper, risus vitae convallis rhoncus, purus purus suscipit sem, at venenatis purus massa id eros. Mauris suscipit tristique elementum. Fusce vitae nibh sed elit sagittis vulputate nec id libero. Fusce sit amet massa urna. Curabitur sit amet diam eget lacus auctor commodo. Curabitur eget ante sapien, quis pellentesque metus. Pellentesque ac augue libero, ut facilisis orci. Nunc feugiat hendrerit nibh, ac sagittis lacus aliquet nec.\n"
                + "\n"
                + "Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Morbi eu lectus augue. Mauris eget nisi et ante tincidunt lacinia. In pulvinar, est sit amet scelerisque porta, eros sem dignissim velit, id feugiat lorem ante non nibh. Nulla nec suscipit nisi. Nulla fringilla dui eu ligula tempor auctor. Duis dui massa, faucibus eget pellentesque at, fermentum non orci. Aliquam metus lacus, gravida vel dignissim vitae, auctor in elit. Ut non nisl erat, eget gravida est. Quisque lorem ligula, rhoncus eget aliquet ut, molestie vel nibh. Etiam tempor condimentum pharetra.\n"
                + "\n"
                + "Fusce pulvinar lacus auctor nunc interdum at sagittis odio ultrices. Vivamus nunc est, dapibus euismod vestibulum non, placerat posuere justo. Maecenas sit amet neque enim, sit amet porttitor mauris. Aliquam leo mauris, bibendum vel interdum a, euismod sed nibh. Etiam sagittis dui sed sapien imperdiet sagittis. Suspendisse potenti. Suspendisse rutrum iaculis vehicula. Quisque lobortis suscipit hendrerit. Sed eget accumsan nibh.\n"
                + "\n"
                + "Etiam faucibus dui neque, vel placerat tellus. Ut vehicula, odio vulputate pellentesque rutrum, metus nulla luctus quam, vitae mollis lectus lectus at dui. Ut ultricies aliquam convallis. In hac habitasse platea dictumst. Donec in diam et neque volutpat venenatis. Nunc semper enim vel nibh luctus suscipit. Nulla ullamcorper posuere sem, a placerat tortor blandit nec.\n"
                + "\n"
                + "Duis posuere facilisis accumsan. Phasellus id dolor felis, vitae congue est. Donec urna quam, aliquam non ultrices sed, porttitor nec mauris. Donec vitae libero mauris. Nunc viverra ante placerat nisi pharetra eu posuere ligula sollicitudin. Donec blandit posuere justo, ut fringilla nulla faucibus ut. Donec ante lectus, pharetra vitae bibendum non, ultrices eget nunc. Nulla non odio eros, eget condimentum sem. Curabitur dictum, ligula ut faucibus congue, dui odio sollicitudin velit, sed tempus massa est vel risus. Vestibulum convallis, neque eu tincidunt tristique, nibh quam posuere ipsum, ultricies vestibulum risus diam nec odio. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Quisque nec urna a sapien tincidunt rutrum.");

    }
}
