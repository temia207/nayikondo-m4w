/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cwf.client.controllers;

import com.extjs.gxt.ui.client.event.EventType;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.MessageBoxEvent;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.google.gwt.core.client.GWT;
import org.cwf.client.AppMessages;
import org.cwf.client.M4waterAsyncCallback;
import org.cwf.client.service.UserServiceAsync;
import org.cwf.client.util.ProgressIndicator;
import org.cwf.client.views.LoginView;
import org.m4water.server.admin.model.User;

/**
 *
 * @author victor
 */
public class LoginController extends Controller {

    final AppMessages appMessages = GWT.create(AppMessages.class);
    public static EventType LOGIN = new EventType();
    public final static EventType SESSIONTIMEOUT = new EventType();
    public final static EventType CHECKADMINPASS = new EventType();
    private LoginView loginView;
    private UserServiceAsync userService;

    public LoginController(UserServiceAsync userService) {
        super();
        this.userService = userService;
        registerEventTypes(LOGIN);
    }

    @Override
    protected void initialize() {
        GWT.log("LoginController : initialize");
        loginView = new LoginView(this);
    }

    @Override
    public void handleEvent(AppEvent event) {
        GWT.log("LoginController : handleEvent");
        forwardToView(loginView, event);
    }

    public void doLogin(String userName, String password) {
        //TO DO do rpc here for login
        performLogin(userName, password);
    }

    public void performLogin(String username, String password) {
        ProgressIndicator.showProgressBar();
        userService.authenticate(username, password, new M4waterAsyncCallback<User>() {

            @Override
            public void onSuccess(User result) {

                if (result != null && hasPrivillages(result)) {
                    Dispatcher dispatcher = Dispatcher.get();
                    AppEvent event = new AppEvent(HomeController.HOME);
                    event.setData(result);
                    dispatcher.dispatch(event);
                } else {
                    MessageBox.alert(appMessages.error(), appMessages.unsuccessfulLogin(), new Listener<MessageBoxEvent>() {

                        @Override
                        public void handleEvent(MessageBoxEvent be) {
                            loginView.showWindow();
                        }
                    });
                }
                ProgressIndicator.hideProgressBar();
            }
        });
    }

    private boolean hasPrivillages(User user) {
        String profile= user.getUserProfile().getProfileDescription();
        if (profile.equalsIgnoreCase("DWO")) {
            return true;
        }
        return false;
    }
}
