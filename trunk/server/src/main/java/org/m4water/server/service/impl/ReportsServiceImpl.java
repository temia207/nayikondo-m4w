package org.m4water.server.service.impl;

import java.util.List;
import org.m4water.server.admin.model.reports.DistrictComparisons;
import org.m4water.server.admin.model.reports.ResponseTime;
import org.m4water.server.dao.ReportsDao;
import org.m4water.server.dao.ResponseTimeDao;
import org.m4water.server.service.ReportsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author victor
 */
@Service("reportService")
@Transactional
public class ReportsServiceImpl implements ReportsService {

	@Autowired
	private ResponseTimeDao responseTimeDao;
	@Autowired
	private ReportsDao reportsDao;

	@Override
	@Transactional(readOnly = true)
	public List<ResponseTime> getResponseTimes(String year, String district) {
		return responseTimeDao.getResponseTimes(year, district);
	}

	@Override
	public List<DistrictComparisons> getDistrictComparisons() {
		return reportsDao.getDistrictComparisons();
	}
}
