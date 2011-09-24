package org.m4water.server.admin.model;
// Generated Sep 7, 2011 12:43:34 PM by Hibernate Tools 3.2.1.GA


import java.util.HashSet;
import java.util.Set;

/**
 * District generated by hbm2java
 */
public class District  extends AbstractEditable<String> {


     
     private String name;
     private Set counties = new HashSet(0);

    public District() {
    }

	
    public District(String id, String name) {
        this.id = id;
        this.name = name;
    }
    public District(String id, String name, Set counties) {
       this.id = id;
       this.name = name;
       this.counties = counties;
    }
   
    public String getid() {
        return this.id;
    }
    
    public void setid(String id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    public Set getCounties() {
        return this.counties;
    }
    
    public void setCounties(Set counties) {
        this.counties = counties;
    }




}


