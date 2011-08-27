package org.m4water.server.service.impl;


import java.util.List;
import org.m4water.server.admin.model.exception.M4waterSessionExpiredException;
import org.m4water.server.admin.model.exception.UserNotFoundException;
import org.m4water.server.admin.model.User;
import org.m4water.server.dao.UserDAO;
import org.m4water.server.security.M4waterSessionRegistry;
import org.m4water.server.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Default implementation for <code>UserService interface</code>.
 * 
 * @author dagmar@cell-life.org.za
 * 
 */
@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;
    
   @Autowired
    private M4waterSessionRegistry sessionRegistry;
    

    private Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public User findUserByUsername(String username) throws UserNotFoundException {
        return userDAO.getUser(username);
    }

    @Override
    public User findUserByEmail(String email) throws UserNotFoundException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public User findUserByPhoneNo(String phoneNo) throws UserNotFoundException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void saveUser(User user) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<User> getUsers() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void deleteUser(User user) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public User getLoggedInUser() throws M4waterSessionExpiredException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void logout() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void saveUsers(List<User> users) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void deleteUsers(List<User> users) {
        throw new UnsupportedOperationException("Not supported yet.");
    }


}
