package org.m4water.server.security;

import org.m4water.server.admin.model.User;
import org.m4water.server.admin.model.exception.M4waterDisabledUserException;
import org.m4water.server.admin.model.exception.M4waterSecurityException;
import org.m4water.server.admin.model.exception.M4waterSessionExpiredException;
import org.m4water.server.security.util.OpenXDataSecurityUtil;
import org.m4water.server.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.AccessDeniedException;
import org.springframework.security.Authentication;
import org.springframework.security.ConfigAttributeDefinition;
import org.springframework.security.context.SecurityContext;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.vote.AffirmativeBased;

/**
 *  
 * @author dagmar@cell-life.org.za
 */
public class M4waterAccessDecisionManager extends AffirmativeBased {
	
	@Autowired
	private UserService userService;
        @Autowired
        private M4waterSessionRegistry sessionRegistry;
	
    /** The logger. */
    private Logger log = LoggerFactory.getLogger(M4waterAccessDecisionManager.class);

	@Override
	public void decide(Authentication auth, Object obj,
			ConfigAttributeDefinition config) throws AccessDeniedException {
		try {
                        mayBeFireUserDisabledException();
			super.decide(auth, obj, config);
		} catch (AccessDeniedException exception) {
		        if (isUserLoggedIn()) {
		        	//We handle a security exception after
		        	//confirming the User is still logged in
		        	//But lacks permission to accomplish operation.
		        	logAndFireSecurityException(exception);
		        } else {
		        	//We handle a session expiry after affirming
		        	//that the User is no longer in the Security Context.
		        	logAndFireSessionExpiredException(exception);
		        }
		}
	}

	/**
	 * Handles the {@link OpenXDataSecurityException}.
	 * 
	 * @param exception <tt>Exception</tt> thrown.
	 * @throws OpenXDataSecurityException With meaningful <tt>Message</tt> to the <tt>User.</tt>
	 */
	private void logAndFireSecurityException(Exception exception) throws M4waterSecurityException {
		
		//TODO This message should be internationalized. 
		String exMsg = "Access to restricted operation is denied";
		
		// log the error on the server so it is not lost
		log.debug("Caught server side Access Denied exception, throwing new exception to the client '"+ 
				exception.getMessage() +"'", exception);
		
		//Re throw known exception to the User.
		throw new M4waterSecurityException(exMsg);
	}

	/**
	 * Handles the {@link OpenXDataSessionExpiredException}.
	 * 
	 * @param exception <tt>Exception</tt> thrown.
	 * @throws OpenXDataSessionExpiredException With meaningful <tt>Message</tt> to the <tt>User.</tt>
	 */
	private void logAndFireSessionExpiredException(Exception exception) throws M4waterSessionExpiredException {
		
		
		//TODO This message should be internationalized.	        	
		String exMsg = "Your session has expired. Re-Login to proceed.";
		
		// log the error on the server so it is not lost
		log.debug("Caught server side Session Expired exception, throwing new exception to the client '"+ 
				exception.getMessage()+"'", exception);
		
		// Logout User.
		userService.logout();
		
		//Re throw know exception to the User.
		throw new M4waterSessionExpiredException(exMsg);
	}
	
	/**
	 * Checks if the <code>User</code> is still 
	 * registered in the <code>Spring security context.</code>
	 * 
	 * @return 
	 * 		<code>true</code> 
	 * <code>if(auth instanceof OpenXDataUserDetails)</code>
	 * <p>
	 * else
	 * 	<code>false</code>
	 * </p>
	 */
	private boolean isUserLoggedIn() {
		
		// see if the authentication object is intact.
        SecurityContext context = SecurityContextHolder.getContext();
        if (context != null) {
        	
        	// Get the User's authentication if it is still intact.
            Authentication auth = context.getAuthentication();            
            if (auth != null) {
            	
            	// Check if the security context is still intact.
                if (auth.getPrincipal() instanceof M4waterUserDetails
                		|| auth.getPrincipal() instanceof User) {
                    return true;
                }
            }
        }
     
        // Return false if Security Context has been invalidated.
        return false;
	}

    private void mayBeFireUserDisabledException() {
        User user = null;
        try {
            user = OpenXDataSecurityUtil.getLoggedInUser();
        } catch (M4waterSessionExpiredException e) {
        }

        if(sessionRegistry.containsDisabledUser(user))
            throw new M4waterDisabledUserException("User : "+user.getUserId()+ " is Disabled");
    }

}