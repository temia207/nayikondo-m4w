/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.m4water.server.security;

import org.m4water.server.admin.model.User;
import org.springframework.security.concurrent.SessionRegistry;

/**
 *
 * @author kay
 */
public interface M4waterSessionRegistry extends SessionRegistry{

    void addDisableUser(User user);

    boolean containsDisabledUser(User user);

    boolean containsDisabledUserName(String userName);

    void removeDisabledUser(User user);

    @Override
    void removeSessionInformation(String sessionId);

    void updateUserEntries(User user);
    
}
