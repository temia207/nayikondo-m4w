package org.m4water.server.admin.model;
// Generated Sep 16, 2011 6:01:51 AM by Hibernate Tools 3.2.1.GA



/**
 * Smsmessagelog generated by hbm2java
 */
public class Smsmessagelog  implements java.io.Serializable {


     private String id;
     private long textmeId;
     private String sender;
     private String time;
     private String message;
     private String status;

    public Smsmessagelog() {
    }

	
    public Smsmessagelog(String id) {
        this.id = id;
    }
    public Smsmessagelog(String id, long textmeId, String sender, String time, String message) {
       this.id = id;
       this.textmeId = textmeId;
       this.sender = sender;
       this.time = time;
       this.message = message;
    }
   
    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    public long getTextmeId() {
        return this.textmeId;
    }
    
    public void setTextmeId(long textmeId) {
        this.textmeId = textmeId;
    }
    public String getSender() {
        return this.sender;
    }
    
    public void setSender(String sender) {
        this.sender = sender;
    }
    public String getTime() {
        return this.time;
    }
    
    public void setTime(String time) {
        this.time = time;
    }
    public String getMessage() {
        return this.message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}




}


