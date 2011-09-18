/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.m4water.server.dao.hibernate;

import java.util.List;
import org.m4water.server.admin.model.FaultAssessment;
import org.m4water.server.dao.AssessmentDao;
import org.springframework.stereotype.Repository;

/**
 *
 * @author victor
 */
@Repository("assessment")
public class AssessmentDaoImpl extends BaseDAOImpl<FaultAssessment, Long> implements AssessmentDao{

    @Override
    public List<FaultAssessment> getFaultAssessments() {
        return findAll();
    }

    @Override
    public FaultAssessment getFaultAssessment(String id) {
        return searchUniqueByPropertyEqual("id", id);
    }

    @Override
    public void saveAssessment(FaultAssessment assessment) {
        save(assessment);
    }

}
