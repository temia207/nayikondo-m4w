/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cwf.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import org.m4water.server.admin.model.Waterpoint;

/**
 *
 * @author victor
 */
@RemoteServiceRelativePath("yawlservice")
public interface YawlService extends RemoteService {

    public void launchWaterPointBaseline(Waterpoint waterpoint);

    public void launchWaterPointBaseline(String waterpointId);
}
