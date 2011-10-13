package org.cwf.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import java.util.List;
import org.m4water.server.admin.model.User;
import org.m4water.server.admin.model.exception.M4waterSecurityException;
import org.m4water.server.admin.model.exception.M4waterSessionExpiredException;
import org.m4water.server.admin.model.exception.UserNotFoundException;

@RemoteServiceRelativePath("user")
public interface UserService extends RemoteService {

    /**
     * Gets a user based on their user name
     * @param username String login name
     * @return User, or null if no match found
     */
    User findUserByUsername(String username) throws UserNotFoundException;

    /**
     * Finds a user based on their email
     * @param email String
     * @return User, or null if no match found
     */
    User findUserByEmail(String email) throws UserNotFoundException;

    /**
     * Finds a user based on their phone number (msisdn)
     * @param phoneNo String
     * @return User, or null if no match found
     */
    User findUserByPhoneNo(String phoneNo) throws UserNotFoundException;

    /**
     * Saves a new and modified users to the database.
     *
     * @param user the user to save.
     */
    void saveUser(User user);

    /**
     * Gets a list of users in the database.
     *
     * @return the user list.
     */
    List<User> getUsers();

    /**
     * Removes a user from the database.
     *
     * @param user the user to remove.
     */
    void deleteUser(User user);

    /**
     * Gets the currently logged in <code>User</code>.
     *
     * @return <code>User</code> only and only <code>if(Context.getAuthenticatedUser() != null)</code>
     * @throws OpenXDataSessionExpiredException <code>If(Context.getAuthenticatedUser == null)</code>
     */
    User getLoggedInUser() throws M4waterSessionExpiredException;

    /**
     * Log the current user out of the system
     */
    void logout();

    /**
     * Persists a given list of Users to the database.
     *
     * @param users List of Users to persist.
     */
    void saveUsers(List<User> users);

    /**
     * Deletes a given list of Users from the database.
     *
     * @param users List of Users to delete.
     */
    void deleteUsers(List<User> users);

    /**
     * Authenticates the user
     * @param username String username
     * @param password String hashed password
     * @return User or null if not authenticated
     * @throws OpenXDataSecurityException For any <tt>security related</tt> that occurs on the <tt>service layer.</tt>
     */
    User authenticate(String username, String password) throws M4waterSecurityException;

    /**
     * Determines if the user has a valid password or not
     * @param user User to validate
     * @return boolean true if password is valid
     * @throws OpenXDataSecurityException For any <tt>security related</tt> that occurs on the <tt>service layer.</tt>
     */
    boolean validatePassword(User user) throws M4waterSecurityException;
}
