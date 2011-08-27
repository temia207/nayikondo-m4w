package org.m4water.server.admin.model;
// Generated Aug 26, 2011 5:58:15 PM by Hibernate Tools 3.2.1.GA

/**
 * Ticket generated by hbm2java
 */
public class Ticket extends AbstractEditable {

    private Waterpoint waterpoint;
    private String tickectId;
    private String creatorTel;
    private String message;

    public Ticket() {
    }

    public Ticket(Waterpoint waterpoint, String tickectId, String creatorTel, String message) {
        this.waterpoint = waterpoint;
        this.tickectId = tickectId;
        this.creatorTel = creatorTel;
        this.message = message;
    }

    @Override
    public int getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Waterpoint getWaterpoint() {
        return this.waterpoint;
    }

    public void setWaterpoint(Waterpoint waterpoint) {
        this.waterpoint = waterpoint;
    }

    public String getTickectId() {
        return this.tickectId;
    }

    public void setTickectId(String tickectId) {
        this.tickectId = tickectId;
    }

    public String getCreatorTel() {
        return this.creatorTel;
    }

    public void setCreatorTel(String creatorTel) {
        this.creatorTel = creatorTel;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
