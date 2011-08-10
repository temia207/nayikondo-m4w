package org.cwf.client;

import com.google.gwt.i18n.client.Messages;

public interface AppMessages extends Messages {
	String title();
	String logo();
	String logoUrl();
	String user();
	String login();
	String logout();
	String userProfile();	
	String myDetails();
	String username();
	String passWord();
	String oldPassWord();
	String changeMyPassword();
	String newPassWord();
	String confirmPassword();
	String resetPassword();
	String oldPasswordNotValid();
	String passwordNotSame();
	String profileNotSaved();
	String profileSaved();
	String userNotFound();
	String save();
	String cancel();
	String close();
	String firstName();
	String lastName();
	String phoneNo();
	String eMail();
	String language();


	String unsuccessfulLogin();

	String loading();
	String success();
	String error();
	String pleaseTryAgainLater(String technicalMessage);
	String disclaimer();
	String and();

}
