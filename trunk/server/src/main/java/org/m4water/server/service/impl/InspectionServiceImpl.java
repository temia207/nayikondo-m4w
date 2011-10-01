/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.m4water.server.service.impl;

import java.util.List;
import org.m4water.server.admin.model.Inspection;
import org.m4water.server.admin.model.InspectionQuestionType;
import org.m4water.server.dao.InspectionDao;
import org.m4water.server.dao.InspectionQuestionTypeDao;
import org.m4water.server.service.InspectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

/**
 *
 * @author victor
 */
@Service("inspectionService")
@Transactional
public class InspectionServiceImpl implements InspectionService{

    @Autowired
    private InspectionDao inspectionDao;
    @Autowired
    private InspectionQuestionTypeDao questionTypeDao;

    @Override
    @Transactional(readOnly = true)
    public List<Inspection> getInspections() {
        return inspectionDao.getInspections();
    }

    @Override
    @Transactional(readOnly = true)
    public Inspection getInspection(String id) {
        return inspectionDao.getInspection(id);
    }

    @Override
    public void saveInspection(Inspection inspection) {
        inspectionDao.saveInspection(inspection);
    }
    
    public InspectionQuestionType getQuestionType(String questionName){
        return questionTypeDao.searchUniqueByPropertyEqual("questionType", questionName);
    }
    
    

}
