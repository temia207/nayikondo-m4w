/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.m4water.server.dao;
import java.util.Date;
import java.util.List;
import org.m4water.server.admin.model.Waterpoint;
import org.m4water.server.admin.model.WaterPointSummary;

/**
 *
 * @author victor
 */
public interface WaterPointDao extends  BaseDAO<Waterpoint, String>{
    List<Waterpoint> getWaterPoints();
    Waterpoint getWaterPoint(String waterPointId);
    void saveWaterPoint(Waterpoint waterPoint);
    public List<WaterPointSummary> getWaterPointSummaries();
    public List<WaterPointSummary> getWaterPointSummaries(String district);
    List<WaterPointSummary> getWaterPointSummaries(String district, String baseLineType);
    public Date getBaselineSetDate();
    public List<Waterpoint> getWaterpoints(String baselineDone);

}
