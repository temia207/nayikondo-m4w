package com.ubos.yawl.sms.servlet;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.io.IOException;
import java.util.Collections;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.ubos.yawl.sms.service.UserService;

/**
 * Save phoneNo User servlet.
 * 
 * @author Jonathan
 * 
 */
@Singleton
public class SaveServlet extends HttpServlet {

        /** The Constant serialVersionUID. */
        private static final long serialVersionUID = 1L;
        /** The userService. */
        @Inject
        private UserService userService;
        /** Check if a new object is saved. */
        private boolean isSaved;

        /*
         * (non-Javadoc)
         *
         * @see
         * javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest
         * , javax.servlet.http.HttpServletResponse)
         */
        public void doGet(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
                doPost(request, response);
        }

        /*
         * (non-Javadoc)
         *
         * @see
         * javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest
         * , javax.servlet.http.HttpServletResponse)
         */
        public void doPost(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
                /** Access the session, if not create it */
                HttpSession httpSession = request.getSession(true);
                String username = request.getParameter("username");
                String phoneNo = request.getParameter("phoneNo");
                String oldusername = request.getParameter("oldusername");
                String action = request.getParameter("action");
                if (action != null && action.equals("delete")) {
                        String[] parameterValues = request.getParameterValues("phoneUser");
                        if(parameterValues!=null && parameterValues.length > 0){
                        for (String string : parameterValues) {
                                userService.deletePhoneNo(string);
                        }

                       httpSession.setAttribute("saveMsg", "SuccessFully Deleted");
                        }else{
                                httpSession.setAttribute("saveMsg", "No Item Deleted");
                        }
                } else {
                        if(oldusername == null)
                        isSaved = userService.savePhoneNo(username, phoneNo);
                        else
                                userService.savePhoneNo(oldusername, username, phoneNo);


                                httpSession.setAttribute("saveMsg", "New Record Saved!");
                        
                }

                httpSession.setAttribute("USER", request.getSession().getAttribute("USER"));
                httpSession.setAttribute("userList", userService.getPhoneNoUsers());
                ServletContext context = getServletContext();
                RequestDispatcher dispatcher = context.getRequestDispatcher("/securePage.jsp");
                dispatcher.forward(request, response);
        }
}
