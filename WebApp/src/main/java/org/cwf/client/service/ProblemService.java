package org.cwf.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import java.util.List;
import org.m4water.server.admin.model.Problem;
import org.m4water.server.admin.model.Waterpoint;

/**
 *
 * @author victor
 */
@RemoteServiceRelativePath("ticket")
public interface  ProblemService extends RemoteService{
    Problem getProblem(int id);

    List<Problem> getProblems();
    List<Problem> getProblemHistory(Waterpoint waterPointId);
    void saveProblem(Problem ticket);

    void deleteProblem(Problem ticket);
}
