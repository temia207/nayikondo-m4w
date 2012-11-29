package org.ubos.yawl.sms.smsservice;

import junit.framework.TestCase;
import org.openxdata.yawl.util.InterfaceBHelper;
import org.yawlfoundation.yawl.elements.data.YParameter;
import org.yawlfoundation.yawl.engine.interfce.WorkItemRecord;

/**
 *
 * @author kay
 */
public class SMSCustomServiceTest extends TestCase {
	
	public SMSCustomServiceTest(String testName) {
		super(testName);
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}
	
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}


	/**
	 * Test of add256 method, of class SMSCustomService.
	 */
	public void testAdd256() {
		System.out.println("add256");
		String number = "0783";
		SMSCustomService instance = new SMSCustomService();
		String expResult = "256783";
		String result = instance.add256(number);
		assertEquals(expResult, result);
		
		 number = "256783";
		expResult = "256783";
		 result = instance.add256(number);
		assertEquals(expResult, result);
		
		number = "783";
		expResult = "256783";
		 result = instance.add256(number);
		assertEquals(expResult, result);
		
		
		
	}

	
}
