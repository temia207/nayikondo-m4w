package org.m4water.server.admin.model;
// Generated Sep 18, 2011 5:57:04 PM by Hibernate Tools 3.2.1.GA


import java.util.HashSet;
import java.util.Set;

/**
 * Permission generated by hbm2java
 */
public class Permission  extends AbstractEditable<String>{


     
     private String reports;
     private String read;
     private String write;
     private String data;
     private String view;
     private Set<UserProfile> userProfiles = new HashSet<UserProfile>(0);

    public Permission() {
    }

	
    public Permission(String permissionId, String reports, String read, String write, String data, String view) {
        this.id = permissionId;
        this.reports = reports;
        this.read = read;
        this.write = write;
        this.data = data;
        this.view = view;
    }
    public Permission(String permissionId, String reports, String read, String write, String data, String view, Set<UserProfile> userProfiles) {
       this.id = permissionId;
       this.reports = reports;
       this.read = read;
       this.write = write;
       this.data = data;
       this.view = view;
       this.userProfiles = userProfiles;
    }
   
    public String getPermissionId() {
        return this.id;
    }
    
    public void setPermissionId(String permissionId) {
        this.id = permissionId;
    }
    public String getReports() {
        return this.reports;
    }
    
    public void setReports(String reports) {
        this.reports = reports;
    }
    public String getRead() {
        return this.read;
    }
    
    public void setRead(String read) {
        this.read = read;
    }
    public String getWrite() {
        return this.write;
    }
    
    public void setWrite(String write) {
        this.write = write;
    }
    public String getData() {
        return this.data;
    }
    
    public void setData(String data) {
        this.data = data;
    }
    public String getView() {
        return this.view;
    }
    
    public void setView(String view) {
        this.view = view;
    }
    public Set<UserProfile> getUserProfiles() {
        return this.userProfiles;
    }
    
    public void setUserProfiles(Set<UserProfile> userProfiles) {
        this.userProfiles = userProfiles;
    }




}


