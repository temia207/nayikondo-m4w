/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cwf.client.views;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.View;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.FormButtonBinding;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.RootPanel;
import org.cwf.client.AppMessages;
import org.cwf.client.controllers.LoginController;

/**
 *
 * @author victor
 */
public class LoginView extends View {

    AppMessages appMessages = GWT.create(AppMessages.class);
    private FormPanel loginForm;
    private Window window;

    public LoginView(Controller controller) {
        super(controller);
    }

    @Override
    protected void initialize() {
        window = new Window();
        window.setModal(true);
        window.setWidth("350");
        window.setClosable(false);
        window.setResizable(true);
        window.setDraggable(false);
        window.setPlain(false);
        window.setHeading("CWF-Login");
        window.setBorders(false);
        window.setBodyBorder(false);
        loginForm = new FormPanel();
        loginForm.setFrame(false);
        loginForm.setHeaderVisible(false);
        loginForm.setBorders(false);
        loginForm.setBodyBorder(false);
        loginForm.setLayout(new FormLayout());

        final TextField<String> username = new TextField<String>();
        username.setFieldLabel(appMessages.username());
        username.setAllowBlank(false);
        loginForm.add(username);

        final TextField<String> password = new TextField<String>();
        password.setFieldLabel(appMessages.passWord());
        password.setAllowBlank(false);
        password.setPassword(true);
        loginForm.add(password);

        Button submit = new Button(appMessages.login());
        submit.setType("submit");
        loginForm.addButton(submit);
        submit.addSelectionListener(new SelectionListener<ButtonEvent>() {

            @Override
            public void componentSelected(ButtonEvent ce) {
                ((LoginController) controller).doLogin(
                        username.getValue(), password.getValue());
                window.hide();
            }
        });
        Hyperlink link = new Hyperlink("Forgot Password", "#");
        loginForm.setButtonAlign(HorizontalAlignment.CENTER);
        FormButtonBinding binding = new FormButtonBinding(loginForm);
        binding.addButton(submit);
//        loginForm.add(link);
        window.add(loginForm);
    }

    public void showWindow() {
        window.show();
    }

    public void closeWindow() {
        window.hide();
    }

    @Override
    protected void handleEvent(AppEvent event) {
        System.out.println("============ loginview");
//        RootPanel.get().add(loginForm);
        window.show();
    }
}
