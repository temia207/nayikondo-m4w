/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cwf.client.model;

import com.extjs.gxt.ui.client.data.BaseModel;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author victor
 */
public class ProblemTwits extends BaseModel {

    private UserSummary user;

    public ProblemTwits() {
    }

    public ProblemTwits(UserSummary user, String comment) {
        setUser(user);
        setComment(comment);
    }

    public void setUser(UserSummary summary) {
        this.user = summary;
    }

    public UserSummary getUser() {
        return user;
    }

    public void setComment(String comment) {
        set("comment", comment);
    }

    public String getComment() {
        return get("comment");
    }

    public static List<ProblemTwits> getSampleTwits() {
        List<ProblemTwits> ticketTwits = new ArrayList<ProblemTwits>();
        ticketTwits.add(new ProblemTwits(new UserSummary("0756505033", "vkakama"), "Assigned to Juma"));
        ticketTwits.add(new ProblemTwits(new UserSummary("0756346475", "Nats"), "I cant find the water pump"));
        ticketTwits.add(new ProblemTwits(new UserSummary("0756283947", "Ocero"), "Call this number 0798463758"));
        return ticketTwits;
    }
}
