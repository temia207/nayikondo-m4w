/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.m4water.server.database.customsql;

import java.util.List;
import org.m4water.server.admin.model.WaterPointSummary;

/**
 *
 * @author victor
 */
public interface WaterPointSummaryDao {

    List<WaterPointSummary> getWaterPointSummaries();
}
