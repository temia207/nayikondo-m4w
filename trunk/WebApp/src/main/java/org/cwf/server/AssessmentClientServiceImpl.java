/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cwf.server;

import java.util.List;
import org.cwf.client.service.AssessmentClientService;
import org.cwf.server.rpc.M4waterPersistentRemoteService;
import org.m4water.server.admin.model.FaultAssessment;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 *
 * @author victor
 */
public class AssessmentClientServiceImpl extends M4waterPersistentRemoteService implements AssessmentClientService {

    private org.m4water.server.service.AssessmentService assessmentService;

    @Override
    public List<FaultAssessment> getFaultAssessments() {
        return getAssessmentService().getFaultAssessments();
    }

    @Override
    public FaultAssessment getFaultAssessment(String id) {
       return getAssessmentService().getFaultAssessment(id);
    }

    @Override
    public void saveAssessment(FaultAssessment assessment) {
        getAssessmentService().saveAssessment(assessment);
    }

    public org.m4water.server.service.AssessmentService getAssessmentService() {
        if (assessmentService == null) {
            WebApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
            assessmentService = (org.m4water.server.service.AssessmentService) ctx.getBean("assessmentService");
        }
        return assessmentService;
    }
}
