/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.m4water.server.service;

import java.util.List;
import org.m4water.server.admin.model.Inspection;

/**
 *
 * @author victor
 */
public interface InspectionService {

    List<Inspection> getInspections();

    Inspection getInspection(String id);

    void saveInspection(Inspection inspection);
}
