package apse503;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UserController
 */
public class CategoryController extends ActionController {
	/*
	 *  Just let eclipse generate one of these for your class.
	 *  HOW??
	 */
	private static final long serialVersionUID = -5850400225608661058L;

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public CategoryController() {
		// Like UrlMappings in grails, only you don't have to worry about which order they're in!
		addDefaultGetAction(new get());
		addGetAction("/get", new get());
		addPostAction("/list", new list());
		addPostAction("/save", new save());
		addGetAction("/show", new show());
	}
	
	// Like a controller method in grails
	public class get extends Action{

		@Override
		public void start(HttpServletRequest request, HttpServletResponse response) {
			if(null == request.getSession().getAttribute("user")) // not logged in
				redirect(request.getContextPath() + "/user/login",request,response);
			
			else{
				Category category = new Category();
				try{
					int categoryID = Integer.parseInt(request.getParameter("category"));
					request.setAttribute("category", category.get(categoryID));	
				}
				catch(NumberFormatException nfe){
					nfe.printStackTrace();
				}
				render("/category.jsp",request,response);
			}
		}
	}
	
	// Like a controller method in grails
	public class list extends Action{

		@Override
		public void start(HttpServletRequest request, HttpServletResponse response) {
			
			if(null == request.getSession().getAttribute("user")) // not logged in
				redirect(request.getContextPath() + "/user/login",request,response);
			
			else{
				Category category = new Category();
				request.setAttribute("method", category.getAll());
				
				render("/method.jsp",request,response);
			}
		}
	}
	
	public class show extends Action{

		@Override
		public void start(HttpServletRequest request, HttpServletResponse response) {
			
			if(null == request.getSession().getAttribute("user")) // not logged in
				redirect(request.getContextPath() + "/user/login",request,response);
			
			else {
				request.setAttribute("name",(this.getClass().getSimpleName()).substring(0, this.getClass().getSimpleName().length()) );
				render("/categories.jsp",request,response);
			}
		}
	}
	
	public class save extends Action{
		
		public void start(HttpServletRequest request, HttpServletResponse response){
			
			if(null == request.getSession().getAttribute("user")) // not logged in
				redirect(request.getContextPath() + "/user/login",request,response);
			
			else{
			
				Category category = new Category();
				category.category = request.getParameter("category");
				
				if(category.save())
					request.setAttribute("flash", "Category Save was Sucessful");
				else
					request.setAttribute("flash", "Category Save was Unsucessful");
				
				render("/method.jsp",request,response);
			}
		}
	}
}
