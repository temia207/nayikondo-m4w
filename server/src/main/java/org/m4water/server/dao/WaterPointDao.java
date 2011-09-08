/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.m4water.server.dao;
import java.util.List;
import org.m4water.server.admin.model.Waterpoint;

/**
 *
 * @author victor
 */
public interface WaterPointDao extends  BaseDAO<Waterpoint>{
    List<Waterpoint> getWaterPoints();
    Waterpoint getWaterPoint(String waterPointId);
    void saveWaterPoint(Waterpoint waterPoint);

}
