/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cwf.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import org.m4water.server.admin.model.User;

/**
 *
 * @author victor
 */
@RemoteServiceRelativePath("authentication")
public interface AuthenticationService extends RemoteService {

    /**
     * Authenticates a given <code>User</code>.
     *
     * @param username <code>User's</code> user name.
     * @param password <code>User's </code> password.
     *
     * @return <code>User</code> only and only if <code>User</code> is successfully authenticated.
     */
    User authenticate(String username, String password);

    /**
     * Validates a user's password without authenticating them in the security context.
     *
     * @param username
     * @param password
     * @return true if password matches user's password
     */
    Boolean isValidUserPassword(String username, String password);
}
