/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.m4water.server.dao;

import java.util.List;
import org.m4water.server.admin.model.Problem;

/**
 *
 * @author victor
 */
public interface ProblemDao extends BaseDAO<Problem,Long> {

    List<Problem> getProblems();

    Problem getProblem(int problemId);

    void deleteProblem(Problem problem);
    void saveProblem(Problem problem);
}
