package org.m4water.server.service;

import java.util.List;
import org.m4water.server.admin.model.WaterPointSummary;
import org.m4water.server.admin.model.Waterpoint;

/**
 *
 * @author victor
 */
public interface WaterPointService {
    List<Waterpoint> getWaterPoints();
    Waterpoint getWaterPoint(String referenceNumber);
    List<WaterPointSummary> getWaterPointSummaries();
    void saveWaterPoint(Waterpoint waterPoint);
}