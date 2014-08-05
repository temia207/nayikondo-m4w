package org.ubos.yawl.sms.smsservice;

import junit.framework.TestCase;
import org.openxdata.yawl.util.InterfaceBHelper;
import org.yawlfoundation.yawl.elements.data.YParameter;
import org.yawlfoundation.yawl.engine.interfce.WorkItemRecord;

/**
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

    public void testAdd256AddsOnlyToUgLikeNumber() {
        System.out.println("testAdd256AddsOnlyToUgLikeNumber");
        SMSCustomService instance = new SMSCustomService();

        String number = "782848700";
        String expResult = "256782848700";
        String result = instance.add256(number);
        assertEquals(expResult, result);

        number = "4782848700";
        expResult = "4782848700";
        result = instance.add256(number);
        assertEquals(expResult, result);

        number = "04782848700";
        expResult = "2564782848700";
        result = instance.add256(number);
        assertEquals(expResult, result);
    }

    public void testProcessCommaSeparated() {
        System.out.println("testAdd256AddsOnlyToUgLikeNumber");
        SMSCustomService instance = new SMSCustomService();

        String number = "782848700,256782848700,4782848700,256783,0783";
        String expResult = "256782848700,256782848700,4782848700,256783,256783";
        String result = instance.processNumbers(number);
        assertEquals(expResult, result);

        number = "782848700, ";
        expResult = "256782848700,256";
        result = instance.processNumbers(number);
        assertEquals(expResult, result);

    }


}
