package com.ubos.yawl.sms.authentication;

import java.util.List;

import org.ubos.yawl.sms.service.UserService;
import org.ubos.yawl.sms.service.impl.UserServiceImpl;

import com.ubos.yawl.sms.utils.SmsUser;

/**
 * Implements the authenticationService Interface
 * 
 * @author Jonathan
 * 
 */
public class AuthenticationServiceImpl implements AuthenticationService {

	private UserService userService;
	private List<SmsUser> users;

	public boolean authenticateUser(String userName, String phoneNo) {
		userService = new UserServiceImpl();
		users = userService.getPhoneNoUsers();
		SmsUser smsUser = getSpecifSmsUser(userName, phoneNo, users);
		if (smsUser != null) {
			return true;
		}
		return false;
	}

	public SmsUser getSpecifSmsUser(String userName, String phoneNo, List<SmsUser> smsUsers) {
		for (SmsUser smsUser : smsUsers) {
			if (smsUser.getUsername().equalsIgnoreCase(userName) && smsUser.getPhoneNo().equalsIgnoreCase(phoneNo)) {
				return smsUser;
			}
		}
		return null;
	}

}
