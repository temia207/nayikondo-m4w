/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.m4water.server.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.m4water.server.admin.model.User;
import org.m4water.server.dao.UserDAO;
import org.m4water.server.security.M4waterSessionRegistry;
import org.m4water.server.security.M4waterSessionRegistryImpl;
import org.m4water.server.security.M4waterUserDetails;
import org.m4water.server.security.M4waterUserDetailsService;
import org.m4water.server.security.util.OpenXDataSecurityUtil;
import org.m4water.server.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;

import static org.apache.commons.lang.StringUtils.isBlank;
import static org.apache.commons.lang.StringUtils.isNotBlank;

/**
 *
 * @author victor
 */
@Service("authenticationService")
@Transactional
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private UserDAO userDao;
    @Autowired
    private M4waterUserDetailsService userDetailsService;
    @Autowired
    private M4waterSessionRegistryImpl sessionRegistry;

    @Override
    public User authenticate(String username, String password) {
        User user = null;
        if (isNotBlank(username) && isNotBlank(password)) {
            M4waterUserDetails userDetails = (M4waterUserDetails) userDetailsService.loadUserByUsername(username);
            if (userDetails != null) {
                // found the user, now checking the password etc
                user = userDetails.getM4WaterUser();
                if (isValidUserPassword(username, password)) {
                    String sessionIdBefeoreNewContext = OpenXDataSecurityUtil.getCurrentSession();
                    OpenXDataSecurityUtil.setSecurityContext(userDetails, sessionIdBefeoreNewContext);
                    if (sessionIdBefeoreNewContext != null) {
                        sessionRegistry.registerNewSession(sessionIdBefeoreNewContext, user.getUserName());
                    }
                } else {
                    //invalid password
                    user = null;
                }
            }
        }
        return user;
    }

    @Override
    public Boolean isValidUserPassword(String username, String password) {
        User user = userDao.getUser(username);
        String userPassword = user.getPassword();
        if (isBlank(userPassword)) {
            return false;
        } else if (password.equals(userPassword)) {
            return true;
        }
        return false;
    }
}
