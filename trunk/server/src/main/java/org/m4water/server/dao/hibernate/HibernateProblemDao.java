/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.m4water.server.dao.hibernate;

import java.util.List;
import org.m4water.server.admin.model.Problem;
import org.m4water.server.dao.ProblemDao;
import org.springframework.stereotype.Repository;

/**
 *
 * @author victor
 */
@Repository("Problem")
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
}
