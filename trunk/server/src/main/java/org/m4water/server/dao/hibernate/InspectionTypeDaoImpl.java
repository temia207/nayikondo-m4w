/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.m4water.server.dao.hibernate;

import org.m4water.server.admin.model.InspectionQuestionType;
import org.m4water.server.dao.InspectionQuestionTypeDao;
import org.springframework.stereotype.Repository;

/**
 *
 * @author kay
 */
@Repository("inspectionQestionTyoe")
public class InspectionTypeDaoImpl extends BaseDAOImpl<InspectionQuestionType,String> implements InspectionQuestionTypeDao {
    
}
