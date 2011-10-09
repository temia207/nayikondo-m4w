package org.m4water.server.service;

import org.m4water.server.admin.model.Problem;
import org.m4water.server.admin.model.Waterpoint;

/**
 *
 * @author kay
 */
public interface YawlService {

        public void launchWaterPointFlow(Problem problem);
        
        public void launchWaterPointBaseline(Waterpoint waterpoint);
}
