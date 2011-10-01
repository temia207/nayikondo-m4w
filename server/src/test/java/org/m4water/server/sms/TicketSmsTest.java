
package org.m4water.server.sms;

import java.util.List;
import junit.framework.TestCase;

import org.easymock.classextension.EasyMock;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.m4water.server.admin.model.Problem;
import org.m4water.server.admin.model.Waterpoint;
import org.m4water.server.dao.ProblemDao;
import org.m4water.server.dao.WaterPointDao;
import org.muk.fcit.results.sms.SMSMessage;
import org.powermock.api.easymock.PowerMock;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 *
 * @author kay
 */
@RunWith(PowerMockRunner.class)
public class TicketSmsTest extends TestCase {
    
    public TicketSmsTest(String testName) {
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
     * Test of afterPropertiesSet method, of class TicketSms.
     */
    @Ignore
    public void testAfterPropertiesSet() throws Exception {
        System.out.println("afterPropertiesSet");
        TicketSms instance = new TicketSms();
        instance.afterPropertiesSet();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of processMessage method, of class TicketSms.
     */
    @Test
    public void testProcessMessage() {
        System.out.println("processMessage");
        String sourceId = "405bhs006";
        String complaint = "Smelly";
        String sender = "07126896";
        TicketSms instance = new TicketSms();
        
        WaterPointDao dao = PowerMock.createMock(WaterPointDao.class);
        Waterpoint waterpoint = PowerMock.createMock(Waterpoint.class);
        EasyMock.expect(waterpoint.hasOpenProblems()).andReturn(true);
        EasyMock.expect(dao.getWaterPoint(sourceId)).andReturn(waterpoint);
        SMSServiceImpl smsSevice = PowerMock.createMock(SMSServiceImpl.class);
        smsSevice.sendSMS(sender, "Waterpoint problem has already been reported");
        EasyMock.expectLastCall();
        
        
        instance.setWaterPointDao(dao);
        instance.setSmsService(smsSevice);
        
        PowerMock.replayAll();
       
        instance.processMessage(sourceId, complaint, sender);
        
        PowerMock.verifyAll();

    }

    /**
     * Test of saveAndCacheMessage method, of class TicketSms.
     */
    @Ignore
    public void testSaveAndCacheMessage() {
        System.out.println("saveAndCacheMessage");
        SMSMessage request = null;
        TicketSms instance = new TicketSms();
        instance.saveAndCacheMessage(request);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getProblem method, of class TicketSms.
     */
    @Ignore
    public void testGetProblem() {
        System.out.println("getProblem");
        int id = 0;
        TicketSms instance = new TicketSms();
        Problem expResult = null;
        Problem result = instance.getProblem(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getProblems method, of class TicketSms.
     */
    @Ignore
    public void testGetProblems() {
        System.out.println("getProblems");
        TicketSms instance = new TicketSms();
        List expResult = null;
        List result = instance.getProblems();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of saveProblem method, of class TicketSms.
     */
    @Ignore
    public void testSaveProblem() {
        System.out.println("saveProblem");
        Problem problem = null;
        TicketSms instance = new TicketSms();
        instance.saveProblem(problem);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of deleteProblem method, of class TicketSms.
     */
    @Ignore
    public void testDeleteProblem() {
        System.out.println("deleteProblem");
        Problem problem = null;
        TicketSms instance = new TicketSms();
        instance.deleteProblem(problem);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of launchCase method, of class TicketSms.
     */
    @Ignore
    public void testLaunchCase() {
        System.out.println("launchCase");
        Problem problem = null;
        TicketSms instance = new TicketSms();
        instance.launchCase(problem);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isMessageNew method, of class TicketSms.
     */
    @Ignore
    public void testIsMessageNew() {
        System.out.println("isMessageNew");
        SMSMessage message = null;
        boolean loadFromDb = false;
        TicketSms instance = new TicketSms();
        boolean expResult = false;
        boolean result = instance.isMessageNew(message, loadFromDb);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of mayBeLoadMsgIds method, of class TicketSms.
     */
    @Ignore
    public void testMayBeLoadMsgIds() {
        System.out.println("mayBeLoadMsgIds");
        TicketSms instance = new TicketSms();
        instance.mayBeLoadMsgIds();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of cleanSms method, of class TicketSms.
     */
    @Ignore
    public void testCleanSms() {
        System.out.println("cleanSms");
        SMSMessage request = null;
        TicketSms instance = new TicketSms();
        String expResult = "";
        String result = instance.cleanSms(request);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    public ProblemDao getProblemDaoMock(){
        ProblemDao problemDao;
        return null;
    }
    

}
