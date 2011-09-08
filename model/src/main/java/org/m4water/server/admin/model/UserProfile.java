package org.m4water.server.admin.model;
// Generated Sep 7, 2011 11:37:27 AM by Hibernate Tools 3.2.1.GA



/**
 * UserProfile generated by hbm2java
 */
public class UserProfile  extends AbstractEditable {


     private int userProfileId;
     private Permission permission;
     private String profileDescription;

    public UserProfile() {
    }

    public UserProfile(int userProfileId, Permission permission, String profileDescription) {
       this.userProfileId = userProfileId;
       this.permission = permission;
       this.profileDescription = profileDescription;
    }
   
    public int getUserProfileId() {
        return this.userProfileId;
    }
    
    public void setUserProfileId(int userProfileId) {
        this.userProfileId = userProfileId;
    }
    public Permission getPermission() {
        return this.permission;
    }
    
    public void setPermission(Permission permission) {
        this.permission = permission;
    }
    public String getProfileDescription() {
        return this.profileDescription;
    }
    
    public void setProfileDescription(String profileDescription) {
        this.profileDescription = profileDescription;
    }




}


