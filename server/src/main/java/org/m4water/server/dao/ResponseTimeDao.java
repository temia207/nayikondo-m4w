package org.m4water.server.dao;

import java.util.List;
import org.m4water.server.admin.model.reports.ResponseTime;

/**
 * @author victor
 */
public interface ResponseTimeDao extends BaseDAO<ResponseTime,Long>{
	List<ResponseTime> getResponseTimes(String year,String district);
}
