package org.ubos.yawl.sms.service.impl;

import junit.framework.TestCase;

/**
 *
 * @author kay
 */
public class SMSServiceImplTest extends TestCase {

        public SMSServiceImplTest(String testName) {
                super(testName);
        }

        /**
         * Test of sendSMS method, of class SMSServiceImpl.
         */
        public void testSendSMS() {
                System.out.println("sendSMS");
                String number = "256712075579";
                String message = "Oblangatana";
                SMSServiceImpl instance = new SMSServiceImpl();
                instance.sendSMS(number, message);
        }

}
