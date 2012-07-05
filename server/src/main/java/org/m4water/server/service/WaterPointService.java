package org.m4water.server.service;

import java.util.Date;
import java.util.List;
import org.m4water.server.admin.model.WaterPointSummary;
import org.m4water.server.admin.model.Waterpoint;
import org.m4water.server.admin.model.WaterpointTypes;
import org.m4water.server.admin.model.paging.PagingLoadConfig;
import org.m4water.server.admin.model.paging.PagingLoadResult;

/**
 *
 * @author victor
 */
public interface WaterPointService {
    List<Waterpoint> getWaterPoints();
    Waterpoint getWaterPoint(String referenceNumber);
    List<WaterPointSummary> getWaterPointSummaries();
    List<WaterPointSummary> getWaterPointSummaries(String district);
    List<WaterPointSummary> getWaterPointSummaries(String district, String baseLineType);
    void saveWaterPoint(Waterpoint waterPoint);
    public Date getBaselineSetDate();
    WaterpointTypes getWaterPointType(String name);
    public List<Waterpoint> getWaterpoints(String baselineDone);
    public PagingLoadResult<WaterPointSummary> getWaterPointSummaries(String district, String baseLineType, PagingLoadConfig loadConfig);
}
