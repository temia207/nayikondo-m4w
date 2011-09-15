package com.ubos.yawl.sms.authentication;

/**
 * Authenticates the a user
 * @author Jonathan
 *
 */
public interface AuthenticationService {
	
	public boolean authenticateUser(String userName, String phoneNo);
}
