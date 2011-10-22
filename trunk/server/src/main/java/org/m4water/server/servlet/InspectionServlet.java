package org.m4water.server.servlet;

import org.m4water.server.admin.model.District;
import org.m4water.server.admin.model.Village;
import org.m4water.server.xml.InspectionQuestion;
import org.m4water.server.xml.Inspection;
import org.m4water.server.xml.InspectionQuestions;
import java.io.IOException;
import java.io.StringReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.m4water.server.admin.model.InspectionQuestionType;
import org.m4water.server.admin.model.Subcounty;
import org.m4water.server.admin.model.WaterFunctionality;
import org.m4water.server.admin.model.WaterUserCommittee;
import org.m4water.server.admin.model.Waterpoint;
import org.m4water.server.security.util.UUID;
import org.m4water.server.service.DistrictService;
import org.m4water.server.service.InspectionService;
import org.m4water.server.service.UserService;
import org.m4water.server.service.WUCService;
import org.m4water.server.service.WaterFunctionalityService;
import org.m4water.server.service.WaterPointService;
import org.m4water.server.xml.WaterPointsList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.yawlfoundation.yawl.util.StringUtil;

/**
 *
 * @author kay
 */
public class InspectionServlet extends HttpServlet {

    @Autowired private UserService userService;
    @Autowired private WaterPointService waterPointService;
    @Autowired private InspectionService insptnSrvc;
    @Autowired private DistrictService districtService;
    @Autowired private WaterFunctionalityService funxService;
    @Autowired private WUCService wUCService;
    private JAXBContext jxbCntxt;
    @Autowired private PlatformTransactionManager transactionManager;
    private TransactionTemplate txTemplate;

    @Override
    public void init() throws ServletException {

        super.init();
        try {
            jxbCntxt = JAXBContext.newInstance(Inspection.class, WaterPointsList.class);
        } catch (JAXBException ex) {
            throw new ServletException(ex);
        }
        ServletContext sctx = this.getServletContext();
        WebApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(sctx);
    ctx.getAutowireCapableBeanFactory().autowireBean(this);
    txTemplate = new TransactionTemplate(transactionManager);
        // Manual Injection
//        userService = (UserService) ctx.getBean("userService");
//        waterPointService = (WaterPointService) ctx.getBean("waterpointService");
//        insptnSrvc = (InspectionService) ctx.getBean("inspectionService");
//        districtService = (DistrictService) ctx.getBean("districtService");
//        funxService = (WaterFunctionalityService) ctx.getBean("waterFunctionalityServiceImpl");
//        wUCService = (WUCService) ctx.getBean("wucService");
//        String[] beanNamesForType = ctx.getBeanNamesForType(PlatformTransactionManager.class);
//         ctx.getBean(beanNamesForType[0]);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String parameter = req.getParameter("username");
        String password = req.getParameter("password");
        String action = req.getParameter("action");
        String xml = req.getParameter("xml");
        try {
            if ("inspectionUpdate".equalsIgnoreCase(action))
                handleInspection(xml, resp);
            else if ("newWaterPoint".equalsIgnoreCase(action))
                handleNewWaterPoint(xml, resp);


        } catch (Exception ex) {
            ex.printStackTrace();
            resp.getWriter().print("<fail>Technical Error Occured.Please contact the technical team (" + ex.getMessage() + ")</fail>");
        }
    }

    private void handleInspection(String xml, HttpServletResponse resp) throws IOException, JAXBException, ParseException {
        Unmarshaller createUnmarshaller = jxbCntxt.createUnmarshaller();
        Inspection unmarshal = (Inspection) createUnmarshaller.unmarshal(new StringReader(xml));
        String waterpointId = unmarshal.getWaterpointId();
        Waterpoint waterPoint = waterPointService.getWaterPoint(waterpointId);
        if (waterPoint == null) {
            writeMessage(resp, "<fail>Water Point ID is not found</fail>");
        } else {
            org.m4water.server.admin.model.Inspection createInspection = createInspection(unmarshal);
            createInspection.setWaterpoint(waterPoint);
            insptnSrvc.saveInspection(createInspection);
            writeMessage(resp, "<success>Success</Success>");
        }
    }

    private org.m4water.server.admin.model.Inspection createInspection(Inspection jxbInspctn) throws ParseException {
        org.m4water.server.admin.model.Inspection ins = new org.m4water.server.admin.model.Inspection(java.util.UUID.randomUUID().toString());
        DateFormat df = new SimpleDateFormat("dd-mm-yyyy");

        ins.setInspectionDate(df.parse(jxbInspctn.getDate()));
        ins.setInspectorName(jxbInspctn.getInspectorName());
        ins.setInspectorTitle(null);
        InspectionQuestions inspectionQuestions = jxbInspctn.getInspectionQuestions();
        List<InspectionQuestion> inspectionQuestion = inspectionQuestions.getInspectionQuestion();
        Set<org.m4water.server.admin.model.InspectionQuestions> m4wQuestions = new HashSet<org.m4water.server.admin.model.InspectionQuestions>();
        Map<String, InspectionQuestionType> qnType = new HashMap<String, InspectionQuestionType>();
        for (InspectionQuestion inspectionQuestion1 : inspectionQuestion) {
            org.m4water.server.admin.model.InspectionQuestions qestin = new org.m4water.server.admin.model.InspectionQuestions(UUID.jUuid());
            qestin.setQuestion(inspectionQuestion1.getQuestion());
            qestin.setAnswer(inspectionQuestion1.getAnswer());
            InspectionQuestionType questionType = qnType.get(inspectionQuestion1.getType());
            if (questionType == null)
                questionType = insptnSrvc.getQuestionType(inspectionQuestion1.getType());
            if (questionType == null) {
                questionType = new InspectionQuestionType(UUID.jUuid());
                questionType.setQuestionType(inspectionQuestion1.getType());
            }
            qestin.setInspectionQuestionType(questionType);
            questionType.setInspectionQuestionses(m4wQuestions);
            m4wQuestions.add(qestin);
            qnType.put(inspectionQuestion1.getType(), questionType);
            qestin.setInspection(ins);
        }
        ins.setInspectionQuestionses(m4wQuestions);
        return ins;
    }

    private void handleNewWaterPoint(String xml, HttpServletResponse resp) throws JAXBException, IOException {
        Unmarshaller createUnmarshaller = jxbCntxt.createUnmarshaller();
        WaterPointsList waterPointsList = (WaterPointsList) createUnmarshaller.unmarshal(new StringReader(xml));


        List<Waterpoint> waterPoints = new ArrayList<Waterpoint>();

        for (org.m4water.server.xml.Waterpoint jxbWaterPoint : waterPointsList) {
            //Validate that the district exist
            District districtByName = districtService.getDistrictByName(jxbWaterPoint.getDistrict());
            if (districtByName == null) {
                writeMessage(resp, "<fail>District [" + jxbWaterPoint.getDistrict() + "] does not exist</fail>");
                return;
            }

            Subcounty subcounty = districtByName.getSubcouty(jxbWaterPoint.getSubcounty());

            if (subcounty == null) {
                writeMessage(resp, "<fail>Subcounty [" + jxbWaterPoint.getSubcounty() + "] does not exist</fail>");
                return;
            }

            Village village = subcounty.getVillage(jxbWaterPoint.getVillage());

            if (village == null) {
                writeMessage(resp, StringUtil.wrap("Village name [" + jxbWaterPoint.getVillage() + "] does not exist", "fail"));
                return;
            }

            Waterpoint waterpoint = new Waterpoint();
            waterpoint.setWaterpointId(UUID.jUuid());
            waterpoint.setBaselineDate(new Date(1));
            waterpoint.setName(jxbWaterPoint.getSourceName());
            waterpoint.setVillage(village);

            String non_functional_reason = jxbWaterPoint.getReasonNonFunctional();
            String functionality = jxbWaterPoint.getIsFunctional();
            //String lastRepair = jxbWaterPoint.get

            WaterFunctionality funx = new WaterFunctionality(new Date(),
                    waterpoint,
                    functionality,
                    new Date(1),
                    non_functional_reason,
                    new Date(1),
                    "No Last Repair",
                    new Date(1),
                    new Date(1));

            funx.setId(new Date());
            funx.setWaterpoint(waterpoint);
            funx.setFunctionalityStatus(functionality);
            funx.setReason(non_functional_reason);
            funx.setDetailsLastRepair("");

            waterpoint.getWaterFunctionality().add(funx);

            if (jxbWaterPoint.getIsWucEstablished().equalsIgnoreCase("yes")) {
                WaterUserCommittee wuc = new WaterUserCommittee(UUID.jUuid(),
                        waterpoint,
                        jxbWaterPoint.getCurrentOwnership(),
                        "1900",
                        jxbWaterPoint.getIsWucTrained(),
                        "N/A Collect Fees",
                        jxbWaterPoint.getIsWucFunctional(),
                        "Regular Service Not Known",
                        jxbWaterPoint.getWomenKeypositionsNumberWuc(),
                        jxbWaterPoint.getWomenNumberWuc(),
                        jxbWaterPoint.getCurrentOwnership());
                waterpoint.getWaterUserCommittees().add(wuc);
            }
            waterPoints.add(waterpoint);
        }

        for (Waterpoint waterpoint : waterPoints) {
            waterPointService.saveWaterPoint(waterpoint);
            WaterFunctionality next = (WaterFunctionality) waterpoint.getWaterFunctionality().iterator().next();
            funxService.save(next);
            if (!waterpoint.getWaterUserCommittees().isEmpty()) {
                WaterUserCommittee wuc = (WaterUserCommittee) waterpoint.getWaterUserCommittees().iterator().next();
                wUCService.save(wuc);
            }

        }
        writeMessage(resp, "<success>Success</Success>");
    }

    private void writeMessage(HttpServletResponse resp, String msg) throws IOException {
        resp.getWriter().print(msg);
        resp.getWriter().flush();
    }
}
//Get district
//Get Subcounty
//Get Village
//