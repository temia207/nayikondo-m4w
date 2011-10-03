package org.cwf.server;

import java.util.List;
import org.cwf.client.service.ProblemService;
import org.cwf.server.rpc.M4waterPersistentRemoteService;
import org.m4water.server.admin.model.Problem;
import org.m4water.server.admin.model.Waterpoint;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 *
 * @author victor
 */
public class ProblemServiceImpl extends M4waterPersistentRemoteService implements ProblemService {

    private org.m4water.server.service.TicketService ticketService;

    @Override
    public Problem getProblem(int id) {
        return getTicketService().getProblem(id);
    }

    @Override
    public List<Problem> getProblems() {
        return getTicketService().getProblems();
    }

    @Override
    public List<Problem> getProblemHistory(Waterpoint waterPointId) {
        return getTicketService().getProblemHistory(waterPointId);
    }
    @Override
    public void saveProblem(Problem ticket) {
        getTicketService().saveProblem(ticket);
    }

    @Override
    public void deleteProblem(Problem ticket) {
        getTicketService().deleteProblem(ticket);
    }

    public org.m4water.server.service.TicketService getTicketService() {
        if (ticketService == null) {
            WebApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
            ticketService = (org.m4water.server.service.TicketService) ctx.getBean("ticketService");
        }
        return ticketService;
    }

}
