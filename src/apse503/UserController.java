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
		// Like UrlMappings in groovy, only you don't have to worry about which order they're in!
		addDefaultGetAction(new login());
		addGetAction("/login", new login());
		addGetAction("/signup", new signup());
	}
	
	// Like a controller method in groovy
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
}
