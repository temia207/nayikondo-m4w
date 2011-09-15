package com.ubos.yawl.sms.utils;

public class SmsUser {
 
	/** username for the phone owner */
	private String username;
	
	/** user phone no */
	private String phoneNo;
	
	/** default constructor */
	public SmsUser(){}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
}
