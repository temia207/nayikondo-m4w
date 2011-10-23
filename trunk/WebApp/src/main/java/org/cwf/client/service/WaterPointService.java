/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.cwf.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import java.util.Date;
import java.util.List;
import org.m4water.server.admin.model.WaterPointSummary;
import org.m4water.server.admin.model.Waterpoint;

/**
 *
 * @author victor
 */
@RemoteServiceRelativePath("waterpoint")
public interface WaterPointService extends RemoteService{
    List<Waterpoint> getWaterPoints();
    Waterpoint getWaterPoint(String waterpointId);
    List<WaterPointSummary> getWaterPointSummaries();
    List<WaterPointSummary> getWaterPointSummaries(String district);
    void saveWaterPoint(Waterpoint waterPoint);
    public Date getBaselineSetDate();
}
