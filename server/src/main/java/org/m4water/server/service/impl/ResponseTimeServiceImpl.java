package org.m4water.server.service.impl;

import java.util.List;
import org.m4water.server.admin.model.reports.ResponseTime;
import org.m4water.server.dao.ResponseTimeDao;
import org.m4water.server.service.ResponseTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author victor
 */
@Service("responseTimeService")
@Transactional
public class ResponseTimeServiceImpl implements ResponseTimeService {

	@Autowired
	private ResponseTimeDao responseTimeDao;

	@Override
	 @Transactional(readOnly = true)
	public List<ResponseTime> getResponseTimes(String year, String district) {
		return responseTimeDao.getResponseTimes(year, district);
	}
}
