package org.m4water.server.servlet;

import org.m4water.server.xml.InspectionQuestion;
import org.m4water.server.xml.Inspection;
import org.m4water.server.xml.InspectionQuestions;
import java.io.IOException;
import java.io.StringReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.m4water.server.admin.model.InspectionQuestionType;
import org.m4water.server.admin.model.Waterpoint;
import org.m4water.server.security.util.UUID;
import org.m4water.server.service.InspectionService;
import org.m4water.server.service.UserService;
import org.m4water.server.service.WaterPointService;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 *
 * @author kay
 */
public class InspectionServlet extends HttpServlet {

    private UserService userService;
    private WaterPointService waterPointService;
    private InspectionService insptnSrvc;
    private JAXBContext jxbCntxt;

    @Override
    public void init() throws ServletException {

        super.init();
        try {
            jxbCntxt = JAXBContext.newInstance(Inspection.class);
        } catch (JAXBException ex) {
            throw new ServletException(ex);
        }
        ServletContext sctx = this.getServletContext();
        WebApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(sctx);

        // Manual Injection
        userService = (UserService) ctx.getBean("userService");
        waterPointService = (WaterPointService) ctx.getBean("waterpointService");
        insptnSrvc = (InspectionService) ctx.getBean("inspectionService");

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String parameter = req.getParameter("username");
        String password = req.getParameter("password");
        String action = req.getParameter("action");
        String xml = req.getParameter("xml");
        try {
            Unmarshaller createUnmarshaller = jxbCntxt.createUnmarshaller();
            Inspection unmarshal = (Inspection) createUnmarshaller.unmarshal(new StringReader(xml));
            String waterpointId = unmarshal.getWaterpointId();
            Waterpoint waterPoint = waterPointService.getWaterPoint(waterpointId);
            if (waterPoint == null) {
                resp.getWriter().print("<fail>Water Point ID is not found</fail>");
                resp.getWriter().flush();
                return;
            }
            org.m4water.server.admin.model.Inspection createInspection = createInspection(unmarshal);
            createInspection.setWaterpoint(waterPoint);
            insptnSrvc.saveInspection(createInspection);
            resp.getWriter().print("<success>Success</Success>");
            resp.getWriter().flush();
        } catch(Exception ex){
           ex.printStackTrace();
            resp.getWriter().print("<fail>Technical Error Occured.Please contact the technical team ("+ex.getMessage()+")</fail>");
        }




    }

    private org.m4water.server.admin.model.Inspection createInspection(Inspection jxbInspctn) throws ParseException {
        org.m4water.server.admin.model.Inspection ins = new org.m4water.server.admin.model.Inspection(java.util.UUID.randomUUID().toString());
        DateFormat df = new SimpleDateFormat("dd-mm-yyyy");

        ins.setInspectionDate(df.parse(jxbInspctn.date));
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
}
