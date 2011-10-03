/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cwf.client.controllers;

import com.extjs.gxt.ui.client.event.EventType;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.google.gwt.core.client.GWT;
import org.cwf.client.views.widgets.CommentView;

/**
 *
 * @author victor
 */
public class CommentController extends Controller {

    private CommentView commentPanel;
    public final static EventType COMMENT = new EventType();
    public CommentController() {
        super();
        registerEventTypes(COMMENT);
    }

    @Override
    public void initialize() {
        GWT.log("CommentController  : initialize");
        commentPanel = new CommentView(this, "DWO Comment");
    }

    @Override
    public void handleEvent(AppEvent event) {
        GWT.log("CommentController  :handle event");
        EventType type = event.getType();
        if (type == COMMENT) {
            forwardToView(commentPanel, event);
        }
    }
}
