package apse503;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UserController
 */
public class MethodController extends ActionController {
	/*
	 *  Just let eclipse generate one of these for your class.
	 *  HOW??
	 */
	private static final long serialVersionUID = -5850400225608661058L;

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public MethodController() {
		// Like UrlMappings in grails, only you don't have to worry about which order they're in!
		addDefaultGetAction(new get());
		addPostAction("/get", new get());
		addPostAction("/save", new save());
	}
	
	// Like a controller method in grails
	public class get extends Action{

		@Override
		public void start(HttpServletRequest request, HttpServletResponse response) {
			
			Method method = new Method();
			
			// Ensure the parameter is in fact an Integer
			try{
				int methodID = Integer.parseInt(request.getParameter("id"));
				request.setAttribute("method", method.get(methodID));	
			}
			catch(NumberFormatException nfe){
				nfe.printStackTrace();
			}
						
			render("/method.jsp",request,response);
		}
	}
	
	public class save extends Action{
		
		public void start(HttpServletRequest request, HttpServletResponse response){			
			
			try{
				//Create the new method then set its attributes from request's parameters
				Method method = new Method();
				
				method.name = request.getParameter("name");
				method.summary = request.getParameter("summary");			
				method.description = request.getParameter("description");				
				method.url = request.getParameter("url"); //probably won't pass this in
				method.category_id = Integer.parseInt(request.getParameter("categoryid"));
				method.status_id = Integer.parseInt(request.getParameter("statusid"));
				method.user_id = Integer.parseInt(request.getParameter("userid"));				
				
				if(method.save())
				{
					System.out.println("save");
					request.setAttribute("flash", "true");
					System.out.println("attribute");
				}
				else
					request.setAttribute("flash", "false");
				
			}
			catch(NumberFormatException nfe){
				nfe.printStackTrace();
				request.setAttribute("flash", "false");
			}
			catch(Exception e){
				e.printStackTrace();
				request.setAttribute("flash", "false");
			}
			
			render("/method2.jsp",request,response);
		}
	}
}
