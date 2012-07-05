/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.m4water.server.dao.hibernate;

import java.util.List;
import org.hibernate.SQLQuery;
import org.m4water.server.admin.model.Problem;
import org.m4water.server.admin.model.Waterpoint;
import org.m4water.server.dao.ProblemDao;
import org.springframework.stereotype.Repository;

/**
 *
 * @author victor
 */
@Repository("problemDao")
public class HibernateProblemDao extends BaseDAOImpl<Problem,Long> implements ProblemDao {

    @Override
    public List<Problem> getProblems() {
       return findAll();
    }

    @Override
    public Problem getProblem(int problemId) {
        return searchUniqueByPropertyEqual("problemId", problemId);
    }

    @Override
    public void deleteProblem(Problem problem) {
        remove(problem);
    }

    @Override
    public void saveProblem(Problem problem) {
        save(problem);
    }

    @Override
    public List<Problem> getProblemHistory(Waterpoint waterPointId) {
        return searchByPropertyEqual("waterpoint", waterPointId);
    }

    @Override
    public int getTotalProblems(String waterPointId) {
        int max = 0;
        String query = "SELECT count(problem_id) FROM problem WHERE waterpoint_id = '"+waterPointId+"'";
        SQLQuery createQuery = getSession().createSQLQuery(query);
        List res = createQuery.list();
        max = Integer.parseInt(res.get(0)+"");
        return max;
    }

    @Override
    public int getTotalProblems() {
	int max = 0;
        String query = "SELECT count(problem_id) FROM problem";
        SQLQuery createQuery = getSession().createSQLQuery(query);
        List res = createQuery.list();
        max = Integer.parseInt(res.get(0)+"");
        return max;
    }
}
