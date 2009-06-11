/**
 * 
 */
package apse503;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import userMethods.UserMethod;

/**
 * @author spratt
 *
 */
public class MethodUseController extends ActionController {
	private static final long serialVersionUID = -2998590494587440159L;

	public MethodUseController() {
		addDefaultGetAction(new use());
		addGetAction("/use", new use());
		addPostAction("/use", new use());
	}
	
	public class use extends Action{

		@Override
		public void start(HttpServletRequest request, HttpServletResponse response) {
			// Check if the user is logged in
			// We'll use this for authorization for now, switch to token-based authorization later
			User thisUser;
			if(null == (thisUser = (User)request.getSession().getAttribute("user"))) {
				request.setAttribute("flash", "Need to be logged in before using a method.");
				redirect(request.getContextPath() + "/user/login",request,response);
				return;
			}
			
			// Check if the user passed in a method id
			String methodString;
			if(null == (methodString = request.getParameter("method"))) {
				// Didn't pass a method id
				// TODO log this exception
				request.setAttribute("flash", "Must choose a method to use.");
				redirect(request.getContextPath() + "/user/home",request,response);
				return;
			}
			
			// Parse it into an integer
			int methodID = -1;
			try {
				methodID = Integer.parseInt(methodString);
			} catch(Exception e) {
				// Couldn't parse
				// TODO log this exception
				request.setAttribute("flash", "MethodID must be an integer.");
				redirect(request.getContextPath() + "/user/home",request,response);
				return;
			}
			
			// Check to see if that method exists in the DB
			Method thisMethod = new Method();
			if(null == (thisMethod.get(methodID))) {
				// No such method
				// TODO log this exception
				request.setAttribute("flash", "Method does not exist.");
				redirect(request.getContextPath() + "/user/home",request,response);
				return;
			}
			
			// See if we can find the class referenced by the method information 
			// in the DB, then create a new instance of it
			UserMethod userMethod = null;
			try{
				userMethod = (UserMethod)Class.forName("userMethods." + thisMethod.filePath).newInstance();
	 		}catch(Exception e) {
				// The method could not be found, abort!  Abort!
	 			e.printStackTrace();
				request.setAttribute("flash", "There was an error loading class: '" + "userMethods." + thisMethod.filePath + "'");
				redirect(request.getContextPath() + "/user/home",request,response);
				return;
			}
			
			// Log this use
			MethodUse logThisUse = new MethodUse();
			logThisUse.userID = thisUser.id;
			logThisUse.methodID = thisMethod.id;
			
			// Check to see if this use is valid
			// if not, must mean either the user id or the method id is invalid
			if(false == logThisUse.isValid()) {
				// Invalid use, don't give up the goods!
				// TODO log this exception
				request.setAttribute("flash", "Either methodID or userID was invalid when trying to log this usage.");
				redirect(request.getContextPath() + "/user/home",request,response);
				return;
			}
			
			// Does the user have any purchased uses of this method left?
			if(false == logThisUse.hasUsesLeft()) {
				// Out of uses, uh oh!
				// TODO log this exception
				request.setAttribute("flash", "Out of uses.");
				redirect(request.getContextPath() + "/user/home",request,response);
				return;
			}
			
			// Log the use in the DB
			if(false == logThisUse.save()) {
				// Save failed
				// TODO log this exception
				request.setAttribute("flash", "Usage logging failed.");
				redirect(request.getContextPath() + "/user/home",request,response);
				return;
			}
			
			// TODO Parse arguments
			
			// Finally, give the user what they want!
			request.setAttribute("methodOutput", userMethod.run(request.getParameterMap()));  // Replace null with parsed args
			render("/loader.jsp",request,response);
		}
	}
}