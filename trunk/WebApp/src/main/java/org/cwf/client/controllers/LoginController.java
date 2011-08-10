/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cwf.client.controllers;

import com.extjs.gxt.ui.client.event.EventType;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.google.gwt.core.client.GWT;
import org.cwf.client.views.LoginView;

/**
 *
 * @author victor
 */
public class LoginController extends Controller {

    public static EventType LOGIN = new EventType();
    private LoginView loginView;

    public LoginController() {
        super();
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
        Dispatcher dispatcher = Dispatcher.get();
        AppEvent event = new AppEvent(HomeController.HOME);
        dispatcher.dispatch(event);
    }
}
