package org.m4water.server.service;

import java.util.List;
import org.m4water.server.admin.model.Problem;

/**
 *
 * @author victor
 */
public interface TicketService {

    Problem getProblem(int id);

    List<Problem> getProblems();

    void saveProblem(Problem problem);

    void deleteProblem(Problem problem);
}
