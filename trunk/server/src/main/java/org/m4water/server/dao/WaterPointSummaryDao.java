/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.m4water.server.dao;

import java.util.Date;
import org.m4water.server.admin.model.WaterPointSummary;
import org.m4water.server.admin.model.paging.PagingLoadConfig;
import org.m4water.server.admin.model.paging.PagingLoadResult;

/**
 *
 * @author victor
 */
public interface  WaterPointSummaryDao extends BaseDAO<WaterPointSummary,String>{
    public PagingLoadResult<WaterPointSummary> getWaterPointSummaries(String district, String baseLineType, PagingLoadConfig loadConfig);
    public Date getBaselineSetDate();
}
