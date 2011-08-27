package org.m4water.server;


import org.m4water.server.admin.model.User;
import org.m4water.server.security.M4waterUserDetails;
import org.m4water.server.security.M4waterUserDetailsService;
import org.m4water.server.security.util.OpenXDataSecurityUtil;
import org.springframework.security.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;

/**
 * The Server application context.
 * @author victor
 *
 */
@Transactional
public class Context {		

	private static UserDetailsService userDetailsService;

	public Context() { }
	
	public static void setAuthenticatedUser(User user) {
		M4waterUserDetails userDetails = ((M4waterUserDetailsService) userDetailsService).getUserDetailsForUser(user);
		if (userDetails != null){
			OpenXDataSecurityUtil.setSecurityContext(userDetails);
		}
	}
	
	public void setUserDetailsService(UserDetailsService userDetailsService) {
		Context.userDetailsService = userDetailsService;
	}
}
