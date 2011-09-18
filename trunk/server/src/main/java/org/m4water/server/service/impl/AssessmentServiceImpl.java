/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.m4water.server.service.impl;

import java.util.List;
import org.m4water.server.admin.model.FaultAssessment;
import org.m4water.server.dao.AssessmentDao;
import org.m4water.server.service.AssessmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author victor
 */
@Service("assessmentService")
@Transactional
public class AssessmentServiceImpl implements AssessmentService {

    @Autowired
    private AssessmentDao assessmentDao;

    @Override
    @Transactional(readOnly = true)
    public List<FaultAssessment> getFaultAssessments() {
        return assessmentDao.getFaultAssessments();
    }

    @Override
    @Transactional(readOnly = true)
    public FaultAssessment getFaultAssessment(String id) {
        return assessmentDao.getFaultAssessment(id);
    }

    @Override
    public void saveAssessment(FaultAssessment assessment) {
        assessmentDao.save(assessment);
    }
}
