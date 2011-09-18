/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.m4water.server.dao;

import java.util.List;
import org.m4water.server.admin.model.Inspection;

/**
 *
 * @author victor
 */
public interface InspectionDao extends BaseDAO<Inspection, Long>{
    List<Inspection> getInspections();
    Inspection getInspection(String id);
    void saveInspection(Inspection inspection);
}
