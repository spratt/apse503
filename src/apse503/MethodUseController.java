/**
 * 
 */
package apse503;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
			User thisUser;
			if(null == (thisUser = (User)request.getSession().getAttribute("user"))) {
				request.setAttribute("flash", "Need to be logged in before using a method.");
				redirect(request.getContextPath() + "/user/signin",request,response);
				return;
			}
			String methodString;
			if(null == (methodString = request.getParameter("method"))) {
				// Didn't pass a method id
				// TODO log this exception
				request.setAttribute("flash", "Must choose a method to use.");
				redirect(request.getContextPath() + "home.jsp",request,response);
			}
			int methodID = -1;
			try {
				methodID = Integer.parseInt(methodString);
			} catch(Exception e) {
				// Couldn't parse
				// TODO log this exception
				request.setAttribute("flash", "MethodID must be an integer.");
				redirect(request.getContextPath() + "home.jsp",request,response);
			}
			Method thisMethod = new Method();
			if(null == (thisMethod.get(methodID))) {
				// No such method
				// TODO log this exception
				request.setAttribute("flash", "Method does not exist.");
				redirect(request.getContextPath() + "home.jsp",request,response);
			}
			
			// TODO check if the user has enough uses left
			
			// See if we can find the class and create a new instance of it
			Object methodObject = null;
			try{
				methodObject = Class.forName(thisMethod.name).newInstance();
	 		}catch(Exception e) {
				// The method could not be found, abort!  Abort!
				request.setAttribute("flash", "There was an error loading class: '" + thisMethod.name + "'");
				redirect(request.getContextPath() + "home.jsp",request,response);
			}
			
			// Log this use
			MethodUse logThisUse = new MethodUse();
			logThisUse.userID = thisUser.id;
			logThisUse.methodID = thisMethod.id;
			
			if(false == logThisUse.isValid()) {
				// Invalid use, don't give up the goods!
				// TODO log this exception
				request.setAttribute("flash", "Either methodID or userID was invalid when trying to log this usage.");
				redirect(request.getContextPath() + "home.jsp",request,response);
			}
			
			if(false == logThisUse.hasUsesLeft()) {
				// Out of uses, uh oh!
				// TODO log this exception
				request.setAttribute("flash", "Out of uses.");
				redirect(request.getContextPath() + "home.jsp",request,response);
			}
			
			if(false == logThisUse.save()) {
				// Save failed
				// TODO log this exception
				request.setAttribute("flash", "Usage logging failed.");
				redirect(request.getContextPath() + "home.jsp",request,response);
			}
			
			// Finally, give the user what they want!
			request.setAttribute("methodOutput", methodObject.toString());
			render("/loader.jsp",request,response);
		}
	}
}