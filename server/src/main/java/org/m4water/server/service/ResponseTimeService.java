/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.m4water.server.service;

import java.util.List;
import org.m4water.server.admin.model.reports.ResponseTime;

/**
 *
 * @author victor
 */
public interface ResponseTimeService {

	 List<ResponseTime> getResponseTimes(String year,String district);
}
