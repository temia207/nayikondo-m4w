package org.cwf.client.model;

import com.extjs.gxt.ui.client.data.BaseModel;
import java.util.Date;
import org.m4water.server.admin.model.Problem;
import org.m4water.server.admin.model.Village;
import org.m4water.server.admin.model.Waterpoint;

/**
 *
 * @author victor
 */
public class ProblemSummary extends BaseModel {

    private Problem problem;

    public ProblemSummary(int id, String waterPoint, Date dateOfReport, String problemDescription,
            String problemStatus) {
        setId(id);
        setDate(dateOfReport);
        setWaterPoint(waterPoint);
        setProblemDescription(problemDescription);
        setProblemStatus(problemStatus);
    }

    public ProblemSummary(Problem problem) {
        setProblem(problem);
    }

    public void setId(int id) {
        set("id",new Integer(id));
    }

    public void setWaterPoint(String waterPoint) {
        set("waterpoint", waterPoint);
    }

    public void setDate(Date date) {
        set("date", date);
    }

    public void setProblemDescription(String problemDescription) {
        set("problemdescription", problemDescription);
    }

    public void setProblemStatus(String problemStatus) {
        set("status", problemStatus);
    }

    public void setVillage(String village) {
        set("village", village);
    }

    public void setDistrict(String district) {
        set("district", district);
    }

    public void setSubcounty(String subCounty) {
        set("subcounty", subCounty);
    }

    public void setCounty(String county) {
        set("county", county);
    }

    public void setParish(String parish) {
        set("parish", parish);
    }

    public void setProblem(Problem problem) {
        this.problem = problem;
        updateProblem(problem);
    }

    public void updateProblem(Problem problem) {
        setId(problem.getProblemId());
        setWaterPoint(problem.getWaterpoint().getWaterpointId());
        setDate(problem.getDateProblemReported());
        setProblemDescription(problem.getProblemDescsription());
        setProblemStatus(problem.getProblemStatus());
        setVillage(problem.getWaterpoint().getVillage().getVillagename());
        setSubcounty(problem.getWaterpoint().getVillage().getParish().
                getSubcounty().getSubcountyName());
        setDistrict(problem.getWaterpoint().getVillage().getParish().
                getSubcounty().getCounty().getDistrict().getName());
    }

    public Integer getId() {
        return get("id");
    }

    public Date getDate() {
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

    public String getVillage() {
        return get("village");
    }

    public String getParish() {
        return get("parish");
    }

    public String getSubCounty() {
        return get("subcounty");
    }

    public String getCounty() {
        return get("county");
    }

    public String getDistrict() {
        return get("district");
    }
}
