package apse503;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import apse503.ActionController.Action;
import apse503.UserController.authenticate;

/**
 * Servlet implementation class RatingController
 */
@SuppressWarnings("unused")
public class RatingController extends ActionController {
	/*
	 * Just let eclipse generate one of these for your class. HOW??
	 */
	private static final long serialVersionUID = -5850400225608661058L;

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public RatingController() {
		// Like UrlMappings in grails, only you don't have to worry about which
		// order they're in!
		addDefaultGetAction(new get());
		addPostAction("/get", new get());
		addPostAction("/save", new save());
		addGetAction("/submit", new submit());
		addGetAction("/authenticate", new authenticate());
	}

	// Like a controller method in grails
	public class get extends Action {

		@Override
		public void start(HttpServletRequest request,
				HttpServletResponse response) {

			Rating rating = new Rating();

			// Ensure the parameter is in fact an Integer
			try {
				int ratingID = Integer.parseInt(request
						.getParameter("id"));
				request.setAttribute("rating", rating.getId());
				
			} catch (NumberFormatException nfe) {
				nfe.printStackTrace();
			}

			render("/rating.jsp", request, response);
		}
	}

	public class submit extends Action {

		@Override
		public void start(HttpServletRequest request,
				HttpServletResponse response) {

			if (null == request.getSession().getAttribute("method"))
				;

			render("/method.jsp", request, response);
		}
	}
	
	public class authenticate extends Action {

		@Override
		public void start(HttpServletRequest request,
				HttpServletResponse response) {

			Rating rating = new Rating();
			request.getParameter("comment");
			request.getParameter("rating");
			request.getParameter("ratingId");
			request.getParameter("userId");
			request.getParameter("methodId");
			request.getParameter("dateTime");

			if (!rating.isValid()) {
				request.setAttribute("flash",
						"Invalid credit card number, please try again.");
				render("/rating.jsp", request, response);
			} else {
				// Authenticated!
				redirect(request.getContextPath() + "/rating.jsp",
						request, response);
			}
		}
	}

	public class save extends Action {

		public void start(HttpServletRequest request,
				HttpServletResponse response) {
			System.out.println("save");
			try {
				// Create the new method then set its attributes from request's
				// parameters
				Rating rating = new Rating();

				rating.comment = request.getParameter("comment");
				rating.rating = Integer.parseInt(request.getParameter("rating"));
				rating.user_id = ((User)request.getSession().getAttribute("user")).id; 
				rating.method_id = Integer.parseInt(request.getParameter("methodid")); 			

				if (rating.save())
					request.setAttribute("flash","Rating Save was Sucessful");
				else
					request.setAttribute("flash","Rating Save was Unsucessful");

			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("flash", "false");
			}

			render("/mymethods.jsp", request, response);
		}
	}

}
