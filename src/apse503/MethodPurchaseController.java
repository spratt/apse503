package apse503;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import apse503.UserController.authenticate;

/**
 * Servlet implementation class UserController
 */
public class MethodPurchaseController extends ActionController {
	/*
	 * Just let eclipse generate one of these for your class. HOW??
	 */
	private static final long serialVersionUID = -5850400225608661058L;

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public MethodPurchaseController() {
		// Like UrlMappings in grails, only you don't have to worry about which
		// order they're in!
		addDefaultGetAction(new get());
		addPostAction("/get", new get());
		addPostAction("/save", new save());
		addGetAction("/submit", new submit());
	}

	// Like a controller method in grails
	public class get extends Action {

		@Override
		public void start(HttpServletRequest request,
				HttpServletResponse response) {

			MethodPurchase mp = new MethodPurchase();

			// Ensure the parameter is in fact an Integer
			try {
				int methodpurchaseID = Integer.parseInt(request
						.getParameter("id"));
				request
						.setAttribute("methodpurchase", mp
								.get(methodpurchaseID));
			} catch (NumberFormatException nfe) {
				nfe.printStackTrace();
			}

			render("/method_purchase.jsp", request, response);
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

	public class save extends Action {

		public void start(HttpServletRequest request,
				HttpServletResponse response) {

			try {
				// Create the new method then set its attributes from request's
				// parameters
				MethodPurchase mp = new MethodPurchase();

				mp.method_purchase_id = 1; // Integer.parseInt(request.getParameter("methodpurchase")).id;
				mp.user_id = 1; // ((User)request.getSession().getAttribute("user")).id;
				mp.method_id = 1; // Integer.parseInt(request.getParameter("methodid");
				mp.method_price_id = 1; // Integer.parseInt(request.getParameter("methodpriceid");
				mp.paid_developer = 1; // Integer.parseInt(request.getParameter("paid_developer");

				boolean saveResult = mp.save();
				System.out.println("status:" + saveResult);

				if (mp.save())
					request.setAttribute("flash",
							"MethodPurchase Save was Sucessful");
				else
					request.setAttribute("flash",
							"MethodPurchase Save was Unsucessful");

			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("flash", "false");
			}

			render("/method_purchase.jsp", request, response);
		}
	}

}