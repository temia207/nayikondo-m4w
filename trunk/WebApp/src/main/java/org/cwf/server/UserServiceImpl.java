/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cwf.server;

import java.util.List;
import org.cwf.client.service.UserService;
import org.cwf.server.rpc.M4waterPersistentRemoteService;
import org.m4water.server.admin.model.User;
import org.m4water.server.admin.model.exception.M4waterSecurityException;
import org.m4water.server.admin.model.exception.M4waterSessionExpiredException;
import org.m4water.server.admin.model.exception.UserNotFoundException;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 *
 * @author victor
 */
public class UserServiceImpl extends M4waterPersistentRemoteService implements UserService {

    private org.m4water.server.service.UserService userService;
    private org.m4water.server.service.AuthenticationService authenticationService;

    @Override
    public User findUserByUsername(String username) throws UserNotFoundException {
        return getUserService().findUserByUsername(username);
    }

    @Override
    public User findUserByEmail(String email) throws UserNotFoundException {
        return getUserService().findUserByEmail(email);
    }

    @Override
    public User findUserByPhoneNo(String phoneNo) throws UserNotFoundException {
        return getUserService().findUserByPhoneNo(phoneNo);
    }

    @Override
    public void saveUser(User user) {
        getUserService().saveUser(user);
    }

    @Override
    public List<User> getUsers() {
        return getUserService().getUsers();
    }

    @Override
    public void deleteUser(User user) {
        getUserService().deleteUser(user);
    }

    @Override
    public User getLoggedInUser() throws M4waterSessionExpiredException {
        return getUserService().getLoggedInUser();
    }

    @Override
    public void logout() {
        getUserService().logout();
    }

    @Override
    public void saveUsers(List<User> users) {
        getUserService().saveUsers(users);
    }

    @Override
    public void deleteUsers(List<User> users) {
        getUserService().deleteUsers(users);
    }

    @Override
    public User authenticate(String username, String password) throws M4waterSecurityException {
        return getAuthenticationService().authenticate(username, password);
    }

    @Override
    public boolean validatePassword(User user) throws M4waterSecurityException {
        return getAuthenticationService().isValidUserPassword(user.getUserName(), user.getPassword());
    }

    public org.m4water.server.service.UserService getUserService() {
        if (userService == null) {
            WebApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
            userService = (org.m4water.server.service.UserService) ctx.getBean("userService");
        }
        return userService;
    }

    public org.m4water.server.service.AuthenticationService getAuthenticationService() {
        if (authenticationService == null) {
            WebApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
            authenticationService = (org.m4water.server.service.AuthenticationService) ctx.getBean("authenticationService");
        }
        return authenticationService;
    }
}
