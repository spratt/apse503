package apse503;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RatingController
 */
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
		addGetAction("/getall", new getall());
		addPostAction("/get", new get());
		addPostAction("/save", new save());
		addGetAction("/submit", new submit());
		addGetAction("/authenticate", new authenticate());
	}

	// Like a controller method in grails
	public class getall extends Action {

		@Override
		public void start(HttpServletRequest request,
				HttpServletResponse response) {
			response.setContentType("application/json");
			String json = "{'error':'','ratings':[";
			PrintWriter out;
			try {
				out = response.getWriter();
				if(null == request.getParameter("id")) {
					out.print("{'error':'no id'}");
					return;
				}
				Rating rating = new Rating();
				rating.method_id = Integer.parseInt(request.getParameter("id"));
				// Get all ratings for this method
				ArrayList<Rating> ratings = rating.getAll();
				if(null == ratings) {
					out.print("{'error':'no ratings'}");
					return;
				}
				Iterator<Rating> i = ratings.iterator();
				while(i.hasNext()) {
					Rating next = i.next();
					json+= "{'comment':'" + next.comment + "'," +
							"'rating':'" + next.rating + "'," +
							"'user':'" + new User().get(next.user_id).userName + "'},";
				}
				json = json.substring(0,json.length()-1) + "]}";  // Remove the extra comma at the end
				out.print(json);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NumberFormatException nfe) {
				nfe.printStackTrace();
			}
		}
	}
	
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

			render("/mymethods.jsp", request, response);
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
				render("/mymethods.jsp", request, response);
			} else {
				// Authenticated!
				redirect(request.getContextPath() + "/method/show",
						request, response);
			}
		}
	}

	public class save extends Action {

		public void start(HttpServletRequest request,
				HttpServletResponse response) {
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

			redirect(request.getContextPath() + "/method/show", request, response);
		}
	}

}
