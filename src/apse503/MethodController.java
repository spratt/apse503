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
		addGetAction("/submit", new submit());
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
	
	public class submit extends Action{

		@Override
		public void start(HttpServletRequest request, HttpServletResponse response) {

			request.setAttribute("categories", new Category().getAll());
						
			render("/contribute.jsp",request,response);
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
				method.category_id = 1;//Integer.parseInt(request.getParameter("categoryid"));
				method.status_id = 1;//Integer.parseInt(request.getParameter("statusid"));
				method.user_id = 1;//((User)request.getSession().getAttribute("user")).id;								
				
				boolean saveResult = method.save();
				System.out.println("status:" + saveResult);
				if(saveResult)
				{
					MethodPrice methodPrice = new MethodPrice();
					
					methodPrice.method_id = method.id;
					methodPrice.method_price_status_id = 1; //TODO remove hardcoding
					
					if(request.getParameter("rate_one") != "" && request.getParameter("rate_one_uses") != "")
					{
						methodPrice.price = Double.parseDouble(request.getParameter("rate_one"));
						methodPrice.quantity = Integer.parseInt(request.getParameter("rate_one_uses"));
						methodPrice.save();
					}
					
					if(request.getParameter("rate_two") != "" && request.getParameter("rate_two_uses") != "")
					{
						methodPrice.price = Double.parseDouble(request.getParameter("rate_two"));
						methodPrice.quantity = Integer.parseInt(request.getParameter("rate_two_uses"));
						methodPrice.save();
					}
					
					if(request.getParameter("rate_three") != "" && request.getParameter("rate_three_uses") != "")
					{
						methodPrice.price = Double.parseDouble(request.getParameter("rate_three"));
						methodPrice.quantity = Integer.parseInt(request.getParameter("rate_three_uses"));
						methodPrice.save();
					}
					
					request.setAttribute("flash", "Method Saved Successfully");
				}
				else
					request.setAttribute("flash", "Method Save was Unsucessful");
				
			}
			catch(NumberFormatException nfe){
				nfe.printStackTrace();
				request.setAttribute("flash", "false");
			}
			catch(Exception e){
				e.printStackTrace();
				request.setAttribute("flash", "false");
			}
			
			render("/home.jsp",request,response);
		}
	}
}
