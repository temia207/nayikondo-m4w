package org.m4water.server.service;

import org.m4water.server.admin.model.Problem;
import org.m4water.server.admin.model.Waterpoint;

/**
 *
 * @author kay
 */
public interface YawlService {

        public String launchWaterPointFlow(Problem problem);
        
        public String launchWaterPointBaseline(Waterpoint waterpoint);
        public String launchWaterPointBaseline(String waterpointId);
}
