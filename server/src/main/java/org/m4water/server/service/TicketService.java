package org.m4water.server.service;

import java.util.List;
import org.m4water.server.admin.model.Problem;
import org.m4water.server.admin.model.ProblemLog;
import org.m4water.server.admin.model.Waterpoint;

/**
 *
 * @author victor
 */
public interface TicketService {

    Problem getProblem(int id);

    List<Problem> getProblems();

    List<Problem> getProblemHistory(Waterpoint waterPointId);

    void saveProblem(Problem problem);

    void deleteProblem(Problem problem);

	public void saveProblemLog(ProblemLog problemLog);
}
