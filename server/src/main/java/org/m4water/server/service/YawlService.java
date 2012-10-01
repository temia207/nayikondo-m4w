package org.m4water.server.service;

import org.m4water.server.admin.model.Problem;
import org.m4water.server.admin.model.Waterpoint;
import org.m4water.server.admin.model.exception.M4waterCaseLauchException;
import org.m4water.server.admin.model.exception.M4waterYawlDownException;

/**
 *
 * @author kay
 */
public interface YawlService {

        public String launchWaterPointFlow(Problem problem) throws M4waterYawlDownException,M4waterCaseLauchException;
        
        public String launchWaterPointBaseline(Waterpoint waterpoint);
        public String launchWaterPointBaseline(String waterpointId);
	    public void cancelProblem(int problemID);
	     public void cancelCase(String caseID);
}
