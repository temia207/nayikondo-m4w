package org.m4water.server.security;

import java.util.ArrayList;
import java.util.List;
import org.m4water.server.admin.model.User;
import org.m4water.server.dao.UserDAO;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

/**
 * <tt>OpenXData UserDetailsService</tt> - used by 
 * spring security to retrieve user details and authenticate.
 * <p>
 * Note: the <tt>StudyManagerService.authenticate method</tt>
 * is bypassed because Spring Security implements "remember me" 
 * functionality that requires the retrieval of <tt>User</tt> details without a password.
 * </p>
 * 
 * @author dagmar@cell-life.org.za
 */
@Transactional (readOnly=true)
public class M4waterUserDetailsServiceImpl implements M4waterUserDetailsService {
    
	@Autowired
	private UserDAO userDAO;
	
//	@Autowired
//	private RoleService roleService;

	/** Logger for this class.*/
	private Logger log = LoggerFactory.getLogger(M4waterUserDetailsServiceImpl.class);
	
	/**
	 * Constructs a <tt>User</tt> with appropriate security context details given a name.
	 * 
	 * @param username User name for the <tt>User</tt> we attempting to get.
	 * 
	 * @throws UsernameNotFoundException If Name is not existent in the database.
	 * @throws DataAccessException For any <tt>Data Access Layer Exception.</tt>
	 * 
	 */
    @Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {

		User user = userDAO.getUser(username);
		M4waterUserDetails userDetails = getUserDetailsForUser(user);

		return userDetails;
    }

	@Override
	public M4waterUserDetails getUserDetailsForUser(User user) {
		M4waterUserDetails userDetails = null;
		if (user != null) {
			// Object to hold User permissions.
			List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
			
			// Construct a User Details Object for Spring Security Context.
			userDetails  = new M4waterUserDetails(user, true, true, true, true, authorities
					.toArray(new GrantedAuthority[authorities.size()]));
		}
		return userDetails;
	}


	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

}