package com.ubos.yawl.sms.servlet;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.ubos.yawl.sms.service.UserService;

/**
 * This servlet handles updating of records that are stored.
 */
@Singleton
public class UpdateServlet extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The userservice. */
         @Inject
	private UserService userService;

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
		System.out.println("updating..<<<" + username + ">>");
		String phoneNo = userService.getPhoneNoByUsername(username);

		request.setAttribute("phone", phoneNo);
		request.setAttribute("username", username);
		httpSession.setAttribute("USER",request.getSession().getAttribute("USER"));
		httpSession.setAttribute("userList", userService.getPhoneNoUsers());
		ServletContext context = getServletContext();
		RequestDispatcher dispatcher = context
				.getRequestDispatcher("/securePage.jsp");
		dispatcher.forward(request, response);

	}

}
