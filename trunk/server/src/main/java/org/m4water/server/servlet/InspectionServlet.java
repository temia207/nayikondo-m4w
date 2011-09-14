package org.m4water.server.servlet;

import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

        @Override
        public void init() throws ServletException {
                super.init();
                ServletContext sctx = this.getServletContext();
                WebApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(sctx);

                // Manual Injection
                userService = (UserService) ctx.getBean("userService");
                waterPointService = (WaterPointService) ctx.getBean("waterpointService");
        }

        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                String parameter = req.getParameter("username");
                String password = req.getParameter("password");
                String action = req.getParameter("action");
                String xml = req.getParameter("xml");

                resp.getWriter().print("<success>Success</Success>");
                resp.getWriter().flush();

        }
}
