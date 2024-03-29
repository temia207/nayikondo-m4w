package org.m4water.server.admin.model;
// Generated Sep 18, 2011 5:57:04 PM by Hibernate Tools 3.2.1.GA



/**
 * User generated by hbm2java
 */
public class User extends AbstractEditable<String> {



     private String userId;
     private Subcounty subcounty;
     private UserProfile userProfile;
     private String userName;
     private String firstname;
     private String lastname;
     private String contacts;
     private String password;
     private String oxdName;
     private String phoneId;
     private String assignedNumber;

    public User() {
    }

	
    public User(String userId, Subcounty subcounty, String contacts) {
        this.userId = userId;
        this.subcounty = subcounty;
        this.contacts = contacts;
    }
    public User(String userId, Subcounty subcounty, String username, UserProfile userProfile, String firstname, String lastname, String contacts, String password, String oxdName,String phoneId, String assignedNumber) {
       this.userId = userId;
       this.subcounty = subcounty;
       this.userProfile = userProfile;
       this.userName = username;
       this.firstname = firstname;
       this.lastname = lastname;
       this.contacts = contacts;
       this.password = password;
       this.oxdName = oxdName;
       this.phoneId = phoneId;
       this.assignedNumber = assignedNumber;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    public Subcounty getSubcounty() {
        return this.subcounty;
    }
    
    public void setSubcounty(Subcounty subcounty) {
        this.subcounty = subcounty;
    }
    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String username) {
        this.userName = username;
    }
    public UserProfile getUserProfile() {
        return this.userProfile;
    }
    
    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }
    public String getFirstname() {
        return this.firstname;
    }
    
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
    public String getLastname() {
        return this.lastname;
    }
    
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    public String getContacts() {
        return this.contacts;
    }
    
    public void setContacts(String contacts) {
        this.contacts = contacts;
    }
    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    public String getOxdName() {
        return this.oxdName;
    }
    
    public void setOxdName(String oxdName) {
        this.oxdName = oxdName;
    }
    public String getPhoneId() {
        return this.phoneId;
    }

    public void setPhoneId(String phoneId) {
        this.phoneId = phoneId;
    }
    public String getAssignedNumber() {
        return this.assignedNumber;
    }

    public void setAssignedNumber(String assignedNumber) {
        this.assignedNumber = assignedNumber;
    }




}


