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
import org.ubos.yawl.sms.service.impl.UserServiceImpl;

import com.ubos.yawl.sms.authentication.AuthenticationService;

// TODO: Auto-generated Javadoc
/**
 * servlet handles the login process.
 * 
 * @author Jonathan
 */
@Singleton
public class LoginServlet extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** URL for a form to display. */
	private String redirect;
	
	/** The authenticationService. */
        @Inject
	private AuthenticationService authenticationService;
	
	/** The userService. */
         @Inject
	private UserService userService;
	
	/** Authenticate the user. */
	private boolean isAuthenticated;

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
		if(request.getParameter("logout") != null && request.getParameter("logout").equals("1")){
			logout(request, response);
		}else{
			login(request, response);
		}
		

	}

	/**
	 * Handles the logut process
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession httpSession = request.getSession(true);
		httpSession.invalidate();
		request.setAttribute("msg", "** LoggedOut Sucessfully! **");
		ServletContext context = getServletContext();
		RequestDispatcher dispatcher = context.getRequestDispatcher("/index.jsp");
		dispatcher.forward(request, response);
		
	}

	/**
	 * used for login process.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @throws ServletException
	 *             the servlet exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		/** Access the session, if not create it */
		HttpSession httpSession = request.getSession(true);

		String username = request.getParameter("username");
		String phoneNo = request.getParameter("phoneNo");
		if (username.equals("") || phoneNo.equals("")) {
			request.setAttribute("msg", " ** Username && Password both required! **");
			redirect = "/index.jsp";
		} else {
			isAuthenticated = authenticationService.authenticateUser(username,
					phoneNo);
			if (isAuthenticated) {
				userService = new UserServiceImpl();
				httpSession.setAttribute("USER", username);				
				httpSession.setAttribute("userList", userService.getPhoneNoUsers());
				redirect = "/securePage.jsp";
			} else {
				request.setAttribute("msg", "** Wrong credentials! **");
				redirect = "/index.jsp";
			}
		}

		ServletContext context = getServletContext();
		RequestDispatcher dispatcher = context.getRequestDispatcher(redirect);
		dispatcher.forward(request, response);
	}

}
