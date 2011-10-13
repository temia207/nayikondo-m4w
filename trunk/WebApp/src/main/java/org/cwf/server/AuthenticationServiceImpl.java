/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cwf.server;

import org.cwf.client.service.AuthenticationService;
import org.cwf.server.rpc.M4waterPersistentRemoteService;
import org.m4water.server.admin.model.User;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 *
 * @author victor
 */
public class AuthenticationServiceImpl extends M4waterPersistentRemoteService implements AuthenticationService {

    private org.m4water.server.service.AuthenticationService authenticationService;

    @Override
    public User authenticate(String username, String password) {
        return getAuthenticationService().authenticate(username, password);
    }

    @Override
    public Boolean isValidUserPassword(String username, String password) {
        return getAuthenticationService().isValidUserPassword(username, password);
    }

    public org.m4water.server.service.AuthenticationService getAuthenticationService() {
        if (authenticationService == null) {
            WebApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
            authenticationService = (org.m4water.server.service.AuthenticationService) ctx.getBean("authenticationService");
        }
        return authenticationService;
    }
}
