package org.m4water.server.dao.hibernate;

import java.util.*;
import org.hibernate.*;
import org.m4water.server.admin.model.reports.DistrictComparisons;
import org.m4water.server.admin.model.reports.WucManagement;
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
			comparison.setProtectedSprings(strings[5] + "");
			comparisons.add(comparison);
		}
		return comparisons;
	}

	@Override
	public List<WucManagement> getWucManagementReport() {
		List report = new ArrayList();
		String query1 = "select subcounty.subcounty_name,count(waterpoint.waterpoint_id) number_of_sources,"
				+ "count(yr_established) wuc_established,SUM(CASE WHEN trained ='yes' THEN 1 ELSE 0 END) "
				+ "wuc_trained,sum(no_of_women) number_women,sum(no_of_women_keypos) women_in_key_positions "
				+ " from water_user_committee inner join waterpoint on water_user_committee.waterpoint_id = "
				+ "waterpoint.waterpoint_id inner join village on waterpoint.village_id = village.village_id "
				+ "inner join parish on village.parish_id = parish.parish_id inner join subcounty "
				+ "on parish.subcounty_id = subcounty.id group by subcounty.subcounty_name";
		SQLQuery createQuery = getSession().createSQLQuery(query1);
		List list = createQuery.list();
		WucManagement management;
		for (Object object : list) {
			Object strings[] = (Object[]) object;
			management = new WucManagement();
			management.setSubcounty(strings[0] + "");
			management.setParish("");
			management.setSources(strings[1] + "");
			management.setWucEstablished(strings[2] + "");
			management.setWucTrained(strings[3] + "");
			management.setNoOfWomen(strings[4] + "");
			management.setWomenInKeyPositions(strings[5] + "");
			report.add(management);
		}
		return report;
	}

	@Override
	public String[] getRepairCosts(String district) {
		String[] result = new String[12];
		String query = "select year(date),date,sum(cost_of_materials+cost_of_labour) as sum from fault_assessment "
				+ "inner join problem on fault_assessment.problem_id = problem.problem_id "
				+ "inner join district_waterpoint on problem.waterpoint_id = district_waterpoint.waterpoint_id "+
				" group by month(date)";
		SQLQuery createQuery = getSession().createSQLQuery(query);
		List list = createQuery.list();
		int ctr = 0;
		for (Object object : list) {
			Object strings[] = (Object[]) object;
			result[ctr] = strings[2] + "";
		}
		return result;
	}
}
