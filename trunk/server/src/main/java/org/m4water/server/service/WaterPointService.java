package org.m4water.server.service;

import java.util.Date;
import java.util.List;
import org.m4water.server.admin.model.WaterPointSummary;
import org.m4water.server.admin.model.Waterpoint;
import org.m4water.server.admin.model.WaterpointTypes;

/**
 *
 * @author victor
 */
public interface WaterPointService {
    List<Waterpoint> getWaterPoints();
    Waterpoint getWaterPoint(String referenceNumber);
    List<WaterPointSummary> getWaterPointSummaries();
    List<WaterPointSummary> getWaterPointSummaries(String district);
    void saveWaterPoint(Waterpoint waterPoint);
    public Date getBaselineSetDate();
    WaterpointTypes getWaterPointType(String name);
}
