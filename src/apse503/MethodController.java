package apse503;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.disk.*;
import java.util.*;
import java.io.*;
import org.apache.commons.io.FilenameUtils;

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
		addGetAction("/get", new get());
		addPostAction("/save", new save());
		addGetAction("/submit", new submit());
		addGetAction("/show", new show());
		addGetAction("/view", new view());
	}
	
	// Like a controller method in grails
	public class get extends Action{

		@Override
		public void start(HttpServletRequest request, HttpServletResponse response) {
			
			if(null == request.getSession().getAttribute("user")) // not logged in
				redirect(request.getContextPath() + "/user/login",request,response);
			
			else{
			
			Method method = new Method();
			
			// Ensure the parameter is in fact an Integer
			try{
				int methodID = Integer.parseInt(request.getParameter("method"));
				request.setAttribute("method", method.get(methodID));	
			}
			catch(NumberFormatException nfe){
				nfe.printStackTrace();
			}
						
			render("/method.jsp",request,response);
			}
		}
	}
	
	public class view extends Action{

		@Override
		public void start(HttpServletRequest request, HttpServletResponse response) {
			
			if(null == request.getSession().getAttribute("user")) // not logged in
				redirect(request.getContextPath() + "/user/login",request,response);
			
			else
				render("/method.jsp",request,response);
		}
	}
	
	public class submit extends Action{

		@Override
		public void start(HttpServletRequest request, HttpServletResponse response) {
			
			if(null == request.getSession().getAttribute("user")) // not logged in
				redirect(request.getContextPath() + "/user/login",request,response);
			
			else{

			request.setAttribute("categories", new Category().getAll());
						
			render("/contribute.jsp",request,response);
			}
		}
	}
	
	public class show extends Action{

		@Override
		public void start(HttpServletRequest request, HttpServletResponse response) {
			
			if(null == request.getSession().getAttribute("user")) // not logged in
				redirect(request.getContextPath() + "/user/login",request,response);
			
			else
				render("/mymethods.jsp",request,response);
		}
	}
	
	public class save extends Action{
		
		public void start(HttpServletRequest request, HttpServletResponse response){	
			
			if(null == request.getSession().getAttribute("user")) // not logged in
				redirect(request.getContextPath() + "/user/login",request,response);
			
			else{
			
			try{
				//Create the new method then set its attributes from request's parameters
				Method method = new Method();
				
				//Generate the 3 possible prices
				ArrayList<MethodPrice> methodPrices = new ArrayList<MethodPrice>();
				methodPrices.add(new MethodPrice());
				methodPrices.add(new MethodPrice());
				methodPrices.add(new MethodPrice());
				
				method.status_id = 2;
				method.user_id = ((User)request.getSession().getAttribute("user")).id;																		
				
				//Builds the method object and saves the file to disk
				method = this.HandleUpload(method, methodPrices, request);
				//System.out.println("Method:" + method.category_id + "," + method.description + "," + method.name + "," + method.status_id + "," + method.url + "," + method.user_id);
				if(method != null)
				{
					StringBuffer fullurl = request.getRequestURL();
					String uri = request.getRequestURI().substring(1);
					uri = uri.substring(uri.indexOf('/'),uri.length());
					fullurl.delete(fullurl.lastIndexOf(uri),fullurl.length());
					method.url = fullurl + "/wsdl/" + method.filePath + ".wsdl";
					if(method.save())
					{									
						//Saves the prices						
						for (MethodPrice methodPrice : methodPrices) {
							methodPrice.method_id = method.id;
							methodPrice.method_price_status_id = 1; //TODO remove hardcoding
							methodPrice.save();
						}

						request.setAttribute("flash", "Method Saved Successfully");
					}
					else
						request.setAttribute("flash", "Method Save was Unsucessful");
				}
			}
			catch(NumberFormatException nfe){
				nfe.printStackTrace();
				request.setAttribute("flash", "false");
			}
			catch(Exception e){
				e.printStackTrace();
				request.setAttribute("flash", "false");
			}
			
			render("/mymethods.jsp",request,response);
			}
		}
		
		private Method HandleUpload(Method method, ArrayList<MethodPrice> methodPrices, HttpServletRequest request)
		{
			//Upload method
			if (ServletFileUpload.isMultipartContent(request))
			{
				ServletFileUpload servletFileUpload = new ServletFileUpload(new DiskFileItemFactory());
				List fileItemsList;
				
				try
				{
					fileItemsList = servletFileUpload.parseRequest(request);
				}
				catch(Exception e)
				{
					System.out.println(e.getMessage());
					System.out.println(e.getStackTrace());
					return null;
				}
			
				Iterator it = fileItemsList.iterator();
				
				while (it.hasNext())
				{					
					FileItem fileItemTemp = (FileItem)it.next();
				
					//If the item is a field
					if (fileItemTemp.isFormField())
					{							
						// Build the method one element at a time
						this.BuildMethod(method, methodPrices, fileItemTemp.getFieldName(), fileItemTemp.getString());
					}
				
					if (fileItemTemp.getFieldName().equals("filename"))
					{
						// Can't munge the file name because of how java loads files
						//String fileName = FilenameUtils.getName(fileItemTemp.getName()) + "_" + System.currentTimeMillis() + "_" + ((User)request.getSession().getAttribute("user")).id + ".class";
						String fileName = FilenameUtils.getName(fileItemTemp.getName());
				
						/* Save the uploaded file if its size is greater than 0. */
						if (fileItemTemp.getSize() > 0)
						{
							String ps = "/";
							String dirName = System.getProperty("user.dir") + ps + "apse503" + ps + "src" + ps + "userMethods" + ps;

							File saveTo = new File(dirName + fileName);
							try 
							{
								fileItemTemp.write(saveTo);
								// Just want to chop off the file extension =)
								method.filePath = fileName.substring(0, fileName.lastIndexOf("."));
							}
							catch (Exception e)
							{
								System.out.println(e.getMessage());
								System.out.println(e.getStackTrace());
								return null;
							}
						}
					}
				}
			}

			return method;
		}
		
		private void BuildMethod(Method method, ArrayList<MethodPrice> methodPrices, String field, String value)
		{
			if(field.equals("name"))
				method.name = value;
			else if(field.equals("summary"))
				method.summary = value;
			else if(field.equals("description"))
				method.description = value;
			else if(field.equals("url"))
				method.url = value;
			else if(field.equals("categoryid"))
				method.category_id = Integer.parseInt(value);
			else if(field.equals("statusid"))
				method.status_id = Integer.parseInt(value);
			else if(field.equals("userid"))
				method.user_id = Integer.parseInt(value);
			else if(field.equals("rate_one"))
				methodPrices.get(0).price = Double.parseDouble(value);
			else if(field.equals("rate_one_uses"))
				methodPrices.get(0).quantity = Integer.parseInt(value);
			else if(field.equals("rate_two"))
				methodPrices.get(1).price = Double.parseDouble(value);
			else if(field.equals("rate_two_uses"))
				methodPrices.get(1).quantity = Integer.parseInt(value);
			else if(field.equals("rate_three"))
				methodPrices.get(2).price = Double.parseDouble(value);
			else if(field.equals("rate_three_uses"))
				methodPrices.get(2).quantity = Integer.parseInt(value);		
		}	
	}
}
