package apse503;

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
	}
	
	// Like a controller method in grails
	public class login extends Action{

		@Override
		public void start(HttpServletRequest request, HttpServletResponse response) {
			render("/login.jsp",request,response);
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
			if(null == someone || !someone.authenticate(request.getParameter("password"))) {
				// Bad username/password
				request.setAttribute("flash","Invalid username or password, please try again.");
				render("/login.jsp",request,response);
			} else {
				// Authenticated!
				request.setAttribute("flash","Welcome back, " + someone.firstName);
				request.getSession().setAttribute("user",(Object)someone.id);
				// TODO render to the home page
			}
		}
	}
	
	public class register extends Action{

		@Override
		public void start(HttpServletRequest request, HttpServletResponse response) {
			// TODO Create a user from the request object
			// TODO Validate user valid->success; invalid->signup
		}
	}
}
