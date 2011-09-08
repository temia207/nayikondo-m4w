package org.cwf.client.model;

import com.extjs.gxt.ui.client.data.BaseModel;
import org.m4water.server.admin.model.Problem;
import org.m4water.server.admin.model.Waterpoint;

/**
 *
 * @author victor
 */
public class ProblemSummary extends BaseModel {

    private Problem problem;

    public ProblemSummary(int id, Waterpoint waterPoint, String dateOfReport, String problemDescription,
            String problemStatus) {
        setId(id);
        setDate(dateOfReport);
        setWaterPoint(waterPoint);
        setProblemDescription(problemDescription);
        setProblemStatus(problemStatus);
    }

    public ProblemSummary(Problem problem) {
        this.problem = problem;
    }

    public void setId(int id) {
        set("id", id);
    }

    public void setWaterPoint(Waterpoint waterPoint) {
        set("waterpoint", waterPoint);
    }

    public void setDate(String date) {
        set("date", date);
    }

    public void setProblemDescription(String problemDescription) {
        set("problemdescription", problemDescription);
    }

    public void setProblemStatus(String problemStatus) {
        set("status", problemStatus);
    }

    public void setProblem(Problem problem) {
        this.problem = problem;
    }

    public Integer getId() {
        return get("id");
    }

    public String getDate() {
        return get("date");
    }

    public Waterpoint getWaterPoint() {
        return get("waterpoint");
    }

    public String getProblemDescription() {
        return get("problemdescription");
    }

    public String getProblemStatus() {
        return get("status");
    }

    public Problem getProblem() {
        return problem;
    }
//    public static List<ProblemSummary> getOpenTickets() {
//        List<ProblemSummary> ticketDetails = new ArrayList<ProblemSummary>();
//        ticketDetails.add(new ProblemSummary("20/8/2011", "UKLE01222", "Kabale", "Kamuganguzi", "Buranga","0714505033","Borehole Broken"));
//        return ticketDetails;
//    }
//
//    public static List<ProblemSummary> getClosedTickets() {
//        List<ProblemSummary> ticketDetails = new ArrayList<ProblemSummary>();
//        ticketDetails.add(new ProblemSummary("19/5/2011", "UMAS01236", "Masaka", "Kigasa", "Buddi","0793546572","the pond is dirty"));
//        ticketDetails.add(new ProblemSummary("20/8/2011", "UKLE01222", "Kabale", "Kamuganguzi", "Buranga","0777342635","amazi gawunya"));
//        return ticketDetails;
//    }
//
//    public static List<ProblemSummary> getSuspendedTickets() {
//        List<ProblemSummary> ticketDetails = new ArrayList<ProblemSummary>();
//        ticketDetails.add(new ProblemSummary("19/5/2011", "UMAS01236", "Masaka", "Kigasa", "Buddi","0782364756","no water"));
//        return ticketDetails;
//    }
}
