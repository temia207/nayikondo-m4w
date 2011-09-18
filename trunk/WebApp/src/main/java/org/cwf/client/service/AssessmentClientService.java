/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.cwf.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import java.util.List;
import org.m4water.server.admin.model.FaultAssessment;

/**
 *
 * @author victor
 */
@RemoteServiceRelativePath("assessment")
public interface AssessmentClientService extends RemoteService{

    List<FaultAssessment> getFaultAssessments();

    FaultAssessment getFaultAssessment(String id);

    void saveAssessment(FaultAssessment assessment);
}
