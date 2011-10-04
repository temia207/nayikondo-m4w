/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cwf.client.views.widgets;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.View;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.FlowLayout;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.google.gwt.core.client.GWT;
import org.cwf.client.AppMessages;
import org.cwf.client.controllers.CommentController;
import org.m4water.server.admin.model.Problem;

/**
 *
 * @author victor
 */
public class CommentView extends View {

    private TextArea commentTfld;
    private Window window;
    private FormData formData;
    private FormPanel summaryPanel;
    private String heading;
    private String action;
    private Problem problem;
    AppMessages appMessages = GWT.create(AppMessages.class);

    public CommentView(Controller controller, String title, String action) {
        super(controller);
        this.heading = title;
        this.action = action;
    }

    @Override
    protected void initialize() {
        window = new Window();
//        window.setHeight("500px");
        window.setWidth("425px");
        window.setPlain(true);
        window.setDraggable(true);
        window.setResizable(true);
        window.setScrollMode(Scroll.AUTO);
        window.setModal(true);
        formData = new FormData("-20");
        summaryPanel = new FormPanel();
        summaryPanel.setFrame(true);
        summaryPanel.setHeading(heading);
        summaryPanel.setWidth("98%");
        summaryPanel.setLayout(new FlowLayout());
        initFields();
        summaryPanel.add(commentTfld);
        Button saveBtn;
        saveBtn = new Button("Save");
        saveBtn.addSelectionListener(new SelectionListener<ButtonEvent>() {

            @Override
            public void componentSelected(ButtonEvent ce) {
                if (action.equals(appMessages.close())) {
                    problem.setProblemStatus("closed");
                    ((CommentController) CommentView.this.getController()).saveTicket(problem);
                }
            }
        });
        window.add(summaryPanel);
        window.addButton(saveBtn);
        window.setButtonAlign(HorizontalAlignment.CENTER);
        System.out.println("filter added");
    }

    private void initFields() {
        commentTfld = new TextArea();
        commentTfld.setFieldLabel("Comment");
        commentTfld.setName("comment");
        commentTfld.setHeight("50px");
        commentTfld.setWidth("100%");
        commentTfld.setAllowBlank(false);
    }

    public String getComment() {
        return commentTfld.getValue();
    }

    public void setComment(String value) {
        this.commentTfld.setValue(value);
    }

    public void showWindow() {
        window.show();
    }

    public void closeWindow() {
        window.hide();
    }

    @Override
    protected void handleEvent(AppEvent event) {
        problem = event.getData();
        showWindow();
    }
}
