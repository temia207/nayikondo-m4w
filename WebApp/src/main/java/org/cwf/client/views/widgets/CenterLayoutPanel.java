/*
 * used by classes that want to have the kind of view ie left
 * menu and center panel
 */
package org.cwf.client.views.widgets;

import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.util.Padding;
import com.extjs.gxt.ui.client.widget.CardPanel;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.VBoxLayout;
import com.extjs.gxt.ui.client.widget.layout.VBoxLayout.VBoxLayoutAlign;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.FocusPanel;
import org.cwf.client.AppMessages;
import com.google.gwt.user.client.ui.Label;

/**
 *
 * @author victor
 */
public class CenterLayoutPanel extends ContentPanel {

	final AppMessages appMessages = GWT.create(AppMessages.class);
	private ContentPanel cpWest;
	private ContentPanel cpCenter;

	public CenterLayoutPanel(String title) {
		initUI(title);
	}

	private void initUI(String title) {
		setHeading(title);
		setSize(600, 500);
		setLayout(new BorderLayout());
		cpWest = new ContentPanel();
		cpWest.setHeaderVisible(false);
		VBoxLayout westLayout = new VBoxLayout();
		westLayout.setPadding(new Padding(5));
		westLayout.setVBoxLayoutAlign(VBoxLayoutAlign.STRETCH);
		cpWest.setLayout(westLayout);
		BorderLayoutData west = new BorderLayoutData(LayoutRegion.WEST, 250, 300, 550);
		west.setMargins(new Margins(5));
		west.setSplit(true);
		//==========
		add(cpWest, west);
		cpCenter = new ContentPanel();
		cpCenter.setHeaderVisible(false);
		cpCenter.setLayout(new FitLayout());
		BorderLayoutData center = new BorderLayoutData(LayoutRegion.CENTER);
		center.setMargins(new Margins(5));
		add(cpCenter, center);
	}

	public FocusPanel addLeftMenu(String name, ClickHandler handler) {
		final FocusPanel panel = new FocusPanel();
		panel.setSize("300px", "20px");
		panel.addStyleName("focusPanel");
		final Label label = new Label(name);
		label.addStyleName("leftmenu-text");
		panel.add(label);
		label.addMouseOverHandler(new MouseOverHandler() {

			@Override
			public void onMouseOver(MouseOverEvent event) {
				label.setStyleName("leftMenuover");
//                panel.setStyleName("focusPanel-over");
			}
		});
		label.addMouseOutHandler(new MouseOutHandler() {

			@Override
			public void onMouseOut(MouseOutEvent event) {
				label.removeStyleName("leftMenuover");
				label.addStyleName("leftmenu-text");
			}
		});
		label.addClickHandler(handler);
		return panel;
	}

	public void addCenterPannel(CardPanel panel) {
		cpCenter.add(panel);
	}

	public ContentPanel getWestPanel() {
		return cpWest;
	}

	public ContentPanel getCenterPanel() {
		return cpCenter;
	}
}
