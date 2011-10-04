package org.m4water.server.admin.model;
// Generated Sep 18, 2011 5:57:04 PM by Hibernate Tools 3.2.1.GA


import java.util.HashSet;
import java.util.Set;

/**
 * UserProfile generated by hbm2java
 */
public class UserProfile  extends AbstractEditable<String> {


     
     private Permission permission;
     private String profileDescription;
     private Set<User> users = new HashSet<User>(0);

    public UserProfile() {
    }

	
    public UserProfile(String userProfileId, Permission permission, String profileDescription) {
        this.id = userProfileId;
        this.permission = permission;
        this.profileDescription = profileDescription;
    }
    public UserProfile(String userProfileId, Permission permission, String profileDescription, Set<User> users) {
       this.id = userProfileId;
       this.permission = permission;
       this.profileDescription = profileDescription;
       this.users = users;
    }
   
    public String getUserProfileId() {
        return this.id;
    }
    
    public void setUserProfileId(String userProfileId) {
        this.id = userProfileId;
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
    public Set<User> getUsers() {
        return this.users;
    }
    
    public void setUsers(Set<User> users) {
        this.users = users;
    }




}

