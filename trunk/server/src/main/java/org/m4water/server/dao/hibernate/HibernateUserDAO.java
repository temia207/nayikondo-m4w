package org.m4water.server.dao.hibernate;

import java.util.List;

import org.m4water.server.admin.model.User;
import org.m4water.server.dao.UserDAO;
import org.springframework.stereotype.Repository;


/**
 * Provides a hibernate implementation
 * of the <code>UserDAO</code> data access <code> interface.</code>
 * 
 * @author victor
 *
 */
@Repository("userDAO")
public class HibernateUserDAO extends BaseDAOImpl<User, String> implements UserDAO {

    @Override
    public void deleteUser(User user) {
        remove(user);
    }

    @Override
    public User findUserByPhoneNo(String phoneNo) {
        return searchUniqueByPropertyEqual("contacts", phoneNo);
    }

    @Override
    public User getUser(String username) {
         User user = searchUniqueByPropertyEqual("userName", username);
        return user;
    }

    @Override
    public List<User> getUsers() {
        return findAll();

    }

    @Override
    public void saveUser(User user) {
        save(user);
    }

    @Override
    public void saveOnlineStatus(User user) {
        save(user);
    }
}
