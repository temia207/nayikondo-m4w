/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.cwf.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import java.util.List;
import org.m4water.server.admin.model.Inspection;

/**
 *
 * @author victor
 */
@RemoteServiceRelativePath("inspection")
public interface InspectionClientService extends RemoteService{

    List<Inspection> getInspections();

    Inspection getInspection(String id);

    void saveInspection(Inspection inspection);
}
