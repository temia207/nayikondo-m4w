package org.cwf.client.custom.widget;

import com.extjs.gxt.ui.client.event.ContainerEvent;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.TabPanel;

public class VerticalTabEvent extends ContainerEvent<VerticalTabPanel, VerticalTabItem> {

  public VerticalTabEvent(VerticalTabPanel container) {
    super(container);
  }

  public VerticalTabEvent(VerticalTabPanel container, VerticalTabItem item) {
    super(container, item);
  }

}
