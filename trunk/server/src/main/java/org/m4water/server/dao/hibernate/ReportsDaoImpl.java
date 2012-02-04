package org.m4water.server.dao.hibernate;

import java.util.*;
import org.hibernate.*;
import org.m4water.server.admin.model.reports.DistrictComparisons;
import org.m4water.server.dao.ReportsDao;
import org.springframework.stereotype.Repository;

@Repository("reports")
public class ReportsDaoImpl extends BaseDAOImpl<DistrictComparisons, Long> implements ReportsDao {

	public ReportsDaoImpl() {
	}

	@Override
	public List getDistrictComparisons() {
		List comparisons = new ArrayList();
		String query1 = "select district,subcounty,SUM(CASE WHEN type ='BH' "
				+ "THEN 1 ELSE 0 END) bh ,SUM(CASE WHEN type ='SW' "
				+ "THEN 1 ELSE 0 END) SW,SUM(CASE WHEN type ='YT' "
				+ "THEN 1 ELSE 0 END) YT,SUM(CASE WHEN type ='PS' "
				+ "THEN 1 ELSE 0 END) PS  from district_comparisons group by subcounty  order by district";
		SQLQuery createQuery = getSession().createSQLQuery(query1);
		List list = createQuery.list();
		DistrictComparisons comparison;
		for (Object object : list) {
			Object strings[] = (Object[]) object;
			comparison = new DistrictComparisons();
			comparison.setDistrict(strings[0] + "");
			comparison.setSubcounty(strings[1] + "");
			comparison.setParish("");
			comparison.setBoreholes(strings[2] + "");
			comparison.setShallowWells(strings[3] + "");
			comparison.setPublicTaps(strings[4] + "");
			comparison.setProtectedSprings(strings[5]+"");
			comparisons.add(comparison);
		}
		return comparisons;
	}
}
