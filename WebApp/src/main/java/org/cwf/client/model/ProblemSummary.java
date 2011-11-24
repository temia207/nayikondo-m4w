package org.cwf.client.model;

import com.extjs.gxt.ui.client.data.BaseModel;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.m4water.server.admin.model.FaultAssessment;
import org.m4water.server.admin.model.Problem;
import org.m4water.server.admin.model.Village;
import org.m4water.server.admin.model.Waterpoint;

/**
 *
 * @author victor
 */
public class ProblemSummary extends BaseModel {

    private Problem problem;

    public ProblemSummary(String id, String waterPoint, Date dateOfReport, String problemDescription,
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

    public void setId(String id) {
        set("id", id);
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
        setId(problem.getYawlid());
        setWaterPoint(problem.getWaterpoint().getWaterpointId());
        setDate(problem.getDateProblemReported());
        setProblemDescription(problem.getProblemDescsription());
        setProblemStatus(problem.getProblemStatus());
        setVillage(problem.getWaterpoint().getVillage().getVillagename());
        setSubcounty(problem.getWaterpoint().getVillage().getParish().
                getSubcounty().getSubcountyName());
        setDistrict(problem.getWaterpoint().getVillage().getParish().
                getSubcounty().getCounty().getDistrict().getName());
        List<FaultAssessment> assessment = new ArrayList<FaultAssessment>(problem.getFaultAssessments());
        if (!assessment.isEmpty()) {
            setRepairsDone(assessment.get(0).getRepairsDone());
        }
    }

    public String getId() {
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

    public void setRepairsDone(String fault) {
        set("repairs", fault);
    }

    public String getFault() {
        return get("repairs");
    }
}
