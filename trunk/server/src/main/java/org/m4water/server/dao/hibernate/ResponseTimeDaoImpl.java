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
	public List<ResponseTime> getResponseTimes(String year,String district) {
		List<ResponseTime> responseTimes = new ArrayList<ResponseTime>();
		String query = "SELECT pending_waterpoints.month,pending,fixed from pending_waterpoints "
				+ "left join fixed_waterpoints on pending_waterpoints.month = fixed_waterpoints.month";
		SQLQuery createQuery = getSession().createSQLQuery(query);
		List list = createQuery.list();
		for (Object object : list) {
			Object[] strings = (Object[]) object;
			ResponseTime resp = new ResponseTime();
			resp.setMonth(strings[0] + "");
			resp.setWaterPointsFixed(strings[1]  + "");
			resp.setWaterPointsPending(strings[2] + "");
			resp.setAverageResponseTime("");
			resp.setStandardDeviation("");
			responseTimes.add(resp);
			System.out.println("========= "+strings[0]+"========= "+strings[1]+"========= "+strings[2]);
		}
		return responseTimes;
	}
}
