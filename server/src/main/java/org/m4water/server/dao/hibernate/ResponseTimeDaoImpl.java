/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.m4water.server.dao.hibernate;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.SQLQuery;
import org.m4water.server.admin.model.reports.ResponseTime;
import org.m4water.server.dao.ResponseTimeDao;
import org.springframework.stereotype.Repository;

/**
 *
 * @author victor
 */
@Repository("responseTime")
public class ResponseTimeDaoImpl extends BaseDAOImpl<ResponseTime, Long> implements ResponseTimeDao {

	public int computeStandardDeviation() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public int getAverageResponseTime() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public List<ResponseTime> getResponseTimes(String year, String district) {
		List<ResponseTime> responseTimes = new ArrayList<ResponseTime>();
		String query = "select name,monthname(date_problem_reported) as month,sum(if(problem_status ="
				+ " 'open',1,0)) as open,sum(if(problem_status = 'closed',1,0)) as closed,"
				+ "sum(if(problem_status = 'Suspended',1,0)) as suspended from problem inner join district_waterpoint"
				+ " on problem.waterpoint_id = district_waterpoint.waterpoint_id where district_waterpoint.name = '" + district + "' "
				+ "and year(date_problem_reported) = '"+year+"'"
				+ "group by month(date_problem_reported) order by date_problem_reported ";
		SQLQuery createQuery = getSession().createSQLQuery(query);
		List list = createQuery.list();
		for (Object object : list) {
			Object[] strings = (Object[]) object;
			ResponseTime resp = new ResponseTime();
			resp.setMonth(strings[1] + "");
			resp.setWaterPointsPending(strings[2] + "");
			resp.setWaterPointsFixed(strings[3] + "");
			resp.setAverageResponseTime("");
			resp.setStandardDeviation("");
			responseTimes.add(resp);
			System.out.println("========= " + strings[0] + "========= " + strings[1] + "========= " + strings[2]);
		}
		return responseTimes;
	}
}
