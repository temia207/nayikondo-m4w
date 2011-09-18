/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.m4water.server.service;

import java.util.List;
import org.m4water.server.admin.model.FaultAssessment;

/**
 *
 * @author victor
 */
public interface AssessmentService {

    List<FaultAssessment> getFaultAssessments();

    FaultAssessment getFaultAssessment(String id);

    void saveAssessment(FaultAssessment assessment);
}
