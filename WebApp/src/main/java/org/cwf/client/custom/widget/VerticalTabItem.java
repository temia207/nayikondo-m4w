package org.cwf.client.custom.widget;

/*
 * Ext GWT 2.2.5 - Ext for GWT
 * Copyright(c) 2007-2010, Ext JS, LLC.
 * licensing@extjs.com
 *
 * http://extjs.com/license
 */

import com.extjs.gxt.ui.client.GXT;
import com.extjs.gxt.ui.client.core.El;
import com.extjs.gxt.ui.client.core.Template;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.util.IconHelper;
import com.extjs.gxt.ui.client.util.Util;
import com.extjs.gxt.ui.client.widget.Component;
import com.extjs.gxt.ui.client.widget.IconSupport;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.layout.FlowLayout;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.Accessibility;
import com.google.gwt.user.client.ui.Frame;

/**
 * TabItems are added to a {@link com.extjs.gxt.ui.client.widget.TabPanel}. TabItems can be closable, disabled
 * and support icons.
 *
 * Code snippet:
 *
 * <pre>
 * TabItem ti = new TabItem(&quot;Tab One&quot;);
 * ti.setClosable(true);
 * ti.setEnabled(false);
 * tabPanel.add(ti);
 * </pre>
 *
 * <dl>
 * <dt><b>Events:</b></dt>
 *
 * <dd><b>BeforeClose</b> : TabPanelEvent(tabPanel, item)<br>
 * <div>Fires before an item is closed by the user clicking the close icon.
 * Listeners can cancel the action by calling
 * {@link com.extjs.gxt.ui.client.event.BaseEvent#setCancelled(boolean)}.</div>
 * <ul>
 * <li>tabPanel : this</li>
 * <li>item : the item that was closed.</li>
 * </ul>
 * </dd>
 *
 * <dd><b>Close</b> : TabPanelEvent(tabPanel, item)<br>
 * <div>Fires after an item is closed by the user clicking the close icon.</div>
 * <ul>
 * <li>tabPanel : this</li>
 * <li>item : the item that was closed.</li>
 * </ul>
 * </dd>
 *
 * <dd><b>Select</b> : TabPanelEvent(tabPanel, item)<br>
 * <div>Fires after the item is selected.</div>
 * <ul>
 * <li>tabPanel : this</li>
 * <li>item : the item that was selected.</li>
 * </ul>
 * </dd>
 * </dl>
 */
@SuppressWarnings("deprecation")
public class VerticalTabItem extends LayoutContainer implements IconSupport {

    public class HeaderItem extends Component implements IconSupport {

        public HeaderItem() {
            disableContextMenu(true);
        }

        protected String text;
        protected AbstractImagePrototype icon;

        /**
         * Returns the header's icon style
         *
         * @return the icon style
         */
        public AbstractImagePrototype getIcon() {
            return icon;
        }

        /**
         * Returns the header's text.
         *
         * @return the text
         */
        public String getText() {
            return text;
        }

        /** pass-through method for protected access **/
        protected void setParent(Component p) {
            super.setParent(p);
        }

        /** pass-through method for protected access **/
        protected void setDisabledStyle(String style) {
            disabledStyle = style;
        }

        @Override
        public void onComponentEvent(ComponentEvent ce) {
            super.onComponentEvent(ce);
            switch (ce.getEventTypeInt()) {
                case Event.ONCLICK:
                    onClick(ce);
                    break;
                case Event.ONMOUSEOVER:
                    onMouseOver(ce);
                    break;
                case Event.ONMOUSEOUT:
                    onMouseOut(ce);
                    break;
            }
        }

        /**
         * Sets the item's icon.
         *
         * @param icon the icon
         */
        public void setIcon(AbstractImagePrototype icon) {
            this.icon = icon;
            if (rendered) {
                El node = el().selectNode(".x-tab-image");
                if (node != null) {
                    node.remove();
                }
                if (icon != null) {
                    Element e = icon.createElement().cast();
                    e.setClassName("x-tab-image");
                    el().appendChild(e);
                }
                el().setStyleName("x-tab-with-icon", icon != null);
            }
        }

        public void setIconStyle(String icon) {
            setIcon(IconHelper.create(icon));
        }

        /**
         * Sets the header's text.
         *
         * @param text the text
         */
        public void setText(String text) {
            this.text = text;
            if (rendered) {
                el().child(".x-tab-strip-text").dom.setInnerHTML(Util.isEmptyString(text) ? "&#160;" : text);
                tabPanel.onItemTextChange(VerticalTabItem.this, this.text, text);
            }
        }

        protected void onClick(ComponentEvent ce) {
            tabPanel.onItemClick(VerticalTabItem.this, ce);
        }

        protected void onMouseOut(ComponentEvent ce) {
            tabPanel.onItemOver(VerticalTabItem.this, false);
        }

        protected void onMouseOver(BaseEvent be) {
            tabPanel.onItemOver(VerticalTabItem.this, true);
        }

        protected void onRender(Element target, int pos) {
            tabPanel.onItemRender(VerticalTabItem.this, target, pos);
        }

        @Override
        protected void onRightClick(ComponentEvent ce) {
            ce.stopEvent();
            final int x = ce.getClientX();
            final int y = ce.getClientY();
            DeferredCommand.addCommand(new Command() {
                public void execute() {
                    tabPanel.onItemContextMenu(VerticalTabItem.this, x, y);
                }
            });

        }
    }

  protected Template template;
  protected VerticalTabPanel tabPanel;
  protected HeaderItem header;

  private String textStyle;
  private boolean closable;
  private RequestBuilder autoLoad;

  /**
   * Creates a new tab item.
   */
  public VerticalTabItem() {
    header = new HeaderItem();
    header.setParent(this);
    getFocusSupport().setIgnore(true);
  }

  /**
   * Creates a new tab item with the given text.
   *
   * @param text the item's text
   */
  public VerticalTabItem(String text) {
    this();
    setText(text);
  }

  /**
   * Closes the tab item.
   */
  public void close() {
    tabPanel.close(this);
  }

  @Override
  public void disable() {
    super.disable();
    header.disable();
  }

  @Override
  public void enable() {
    super.enable();
    header.enable();
  }

  /**
   * Returns the item's header component.
   *
   * @return the header component
   */
  public HeaderItem getHeader() {
    return header;
  }

  /**
   * Returns the item's icon style.
   *
   * @return the icon style
   */
  public AbstractImagePrototype getIcon() {
    return header.getIcon();
  }

  /**
   * Returns the item's tab panel.
   *
   * @return the tab panel
   */
  public VerticalTabPanel getTabPanel() {
    return tabPanel;
  }

  /**
   * Returns the item's text.
   *
   * @return the text
   */
  public String getText() {
    return header.getText();
  }

  /**
   * Returns the item's text style name.
   *
   * @return the style name
   */
  public String getTextStyle() {
    return textStyle;
  }

  /**
   * Returns true if the item can be closed.
   *
   * @return the closable the close state
   */
  public boolean isClosable() {
    return closable;
  }

  /**
   * Sends a remote request and sets the item's content using the returned HTML.
   *
   * @param requestBuilder the request builder
   */
  public void setAutoLoad(RequestBuilder requestBuilder) {
    this.autoLoad = requestBuilder;
  }

  /**
   * Sets whether the tab may be closed (defaults to false).
   *
   * @param closable the closable state
   */
  public void setClosable(boolean closable) {
    this.closable = closable;
  }

  /**
   * Sets the item's icon.
   *
   * @param icon the icon
   */
  public void setIcon(AbstractImagePrototype icon) {
    header.setIcon(icon);
  }

  public void setIconStyle(String icon) {
    setIcon(IconHelper.create(icon));
  }

  /**
   * Sets the item's text.
   *
   * @param text the new text
   */
  public void setText(String text) {
    header.setText(text);
  }

  /**
   * Sets the style name to be applied to the item's text element.
   *
   * @param textStyle the style name
   */
  public void setTextStyle(String textStyle) {
    this.textStyle = textStyle;
  }

  /**
   * Sets a url for the content area of the item.
   *
   * @param url the url
   * @return the frame widget
   */
  public Frame setUrl(String url) {
    Frame f = new Frame(url);
    f.getElement().setPropertyInt("frameBorder", 0);
    f.setSize("100%", "100%");
    setLayout(new FlowLayout());
    removeAll();
    add(f);
    layout();
    return f;
  }

  @Override
  public String toString() {
    return el() != null ? el().toString() : super.toString();
  }

  @Override
  protected void onRender(Element parent, int index) {
    super.onRender(parent, index);
    if (autoLoad != null) {
      el().load(autoLoad);
    }
    if (GXT.isAriaEnabled()) {
      Accessibility.setRole(getElement(), Accessibility.ROLE_TABPANEL);
    }
  }

}

