package apse503;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UserController
 */
public class UserController extends ActionController {
	/*
	 *  Just let eclipse generate one of these for your class.
	 */
	private static final long serialVersionUID = -5850400225608661058L;

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public UserController() {
		// Like UrlMappings in grails, only you don't have to worry about which order they're in!
		addDefaultGetAction(new login());
		addGetAction("/login", new login());
		addPostAction("/login", new authenticate());
		addGetAction("/signup", new signup());
		addPostAction("/signup", new register());
		addGetAction("/logout", new logout());
		addGetAction("/home", new home());
	}
	
	// Like a controller method in grails
	public class home extends Action{

		@Override
		public void start(HttpServletRequest request, HttpServletResponse response) {
			if(null == request.getSession().getAttribute("user")) // not logged in
				redirect(request.getContextPath() + "/user/login",request,response);
			else // logged in
				render("/home.jsp",request,response);
		}
	}
	
	// Like a controller method in grails
	public class login extends Action{

		@Override
		public void start(HttpServletRequest request, HttpServletResponse response) {
			if(null == request.getSession().getAttribute("user"))
				render("/login.jsp",request,response);
			else 
				redirect(request.getContextPath() + "/user/home",request,response);
		}
	}
	
	// Like a controller method in grails
	public class logout extends Action{

		@Override
		public void start(HttpServletRequest request, HttpServletResponse response) {
			request.getSession().setAttribute("user",null);
			redirect(request.getContextPath() + "/user/login",request,response);
		}
	}
	
	public class signup extends Action{

		@Override
		public void start(HttpServletRequest request, HttpServletResponse response) {
			render("/signup.jsp",request,response);
		}
	}
	
	public class authenticate extends Action{
	
		@Override
		public void start(HttpServletRequest request, HttpServletResponse response) {
			User someone = new User().findByUserName(request.getParameter("username"));
			if(null == someone){
				// Username couldn't be found
				request.setAttribute("flash","Invalid username or password, please try again.");
				render("/login.jsp",request,response);
			}else if(!someone.authenticate(request.getParameter("password"))) {
				// Wrong password
				request.setAttribute("flash","Invalid username or password, please try again.");
				render("/login.jsp",request,response);
			} else {
				// Authenticated!
				//request.setAttribute("flash","Welcome back, " + someone.firstName + "!");
				request.getSession().setAttribute("user",(Object)someone);
				redirect(request.getContextPath() + "/user/home",request,response);
			}
		}
	}
	
	public class register extends Action{

		@Override
		public void start(HttpServletRequest request, HttpServletResponse response) {
			// If the passwords do not match, go back to registration
			if(!request.getParameter("password").equals(request.getParameter("confirm_password"))){
				request.setAttribute("flash","Confirm password does not match password.");
				render("/signup.jsp",request,response);
			}
			// Build the user
			User newUser = new User();
			newUser.address = request.getParameter("address");
			newUser.userName=request.getParameter("username");
			newUser.firstName=request.getParameter("firstname");
			newUser.lastName=request.getParameter("lastname");
			newUser.email=request.getParameter("email");
			newUser.city=request.getParameter("city");
			newUser.postalCode=request.getParameter("postalcode");
			newUser.province=request.getParameter("province");
			newUser.country=request.getParameter("country");
			newUser.setPassword(request.getParameter("password"));
			
			// Check validity
			if(newUser.isValid()){
				newUser.save();
				new authenticate().start(request, response);
			}
			else {
				request.setAttribute("flash","User does not validate.");
				render("/signup.jsp",request,response);
			}
		}
	}
}
