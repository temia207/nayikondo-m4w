/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.m4water.server.dao.hibernate;

import org.m4water.server.admin.model.ProblemLog;
import org.m4water.server.dao.ProblemLogDao;
import org.springframework.stereotype.Repository;

/**
 *
 * @author kay
 */
@Repository("problemLogDao")
public class HibernateProblemLogDaoImpl extends BaseDAOImpl<ProblemLog, String> implements ProblemLogDao{
    
}
