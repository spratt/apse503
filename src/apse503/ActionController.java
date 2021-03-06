package apse503;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ActionController
 */
public abstract class ActionController extends HttpServlet {
	private static final long serialVersionUID = 7218983358906997040L;
	
	/*
	 *   So if you request the base url pattern (for example "/user")
	 *   getPathInfo() returns null.  Thus, this represents the default action.
	 */
	protected static final String DEFAULT = null;
	
	// This is just a sensible default, the child class can over-ride this
	public String errorJSP = "/error.jsp";
	
	// Simple maps to store the actions
	protected Map<String,Action> getActions, postActions;

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public ActionController(){
		getActions = new DispatchMap("get");
		postActions = new DispatchMap("post");
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		dispatch(getActions,request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		dispatch(postActions,request,response);
	}
	
	// This takes care of the dispatch to the various actions
	private void dispatch(Map<String,Action> toDispatch, HttpServletRequest request, HttpServletResponse response){
		String pathInfo = request.getPathInfo();
		if(!toDispatch.containsKey(pathInfo)) {
			System.err.println("Dispatch Error");
			System.err.println("Method was: '" + toDispatch + "'");
			System.err.println("RequestURI was: '" + request.getRequestURI() + "'");
			System.err.println("PathInfo was:   '" + pathInfo                + "'");
			render(errorJSP, request, response);
			return;  // Just in case
		}
		toDispatch.get(pathInfo).start(request,response);
	}
	
	// Getters and setters so that we don't have to use the tiresome map.put every time
	public void addGetAction(String path,Action toAdd){
		this.getActions.put(path,toAdd);
	}
	
	public void addDefaultGetAction(Action toAdd){
		this.getActions.put(DEFAULT,toAdd);
		this.getActions.put("/",toAdd);
	}
	
	public void addPostAction(String path,Action toAdd){
		this.postActions.put(path,toAdd);
	}
	
	public void addDefaultPostAction(Action toAdd){
		this.postActions.put(DEFAULT,toAdd);
		this.postActions.put("/",toAdd);
	}
	
	protected void redirect(String toJSP, HttpServletRequest request, HttpServletResponse response){
		try {
			// Redirect kills all request attributes, so let's temporarily save the flash
			String tempFlash = null;
			if(null != (tempFlash = (String)request.getAttribute("flash"))) {
				HttpSession session = request.getSession();
				session.setAttribute("sessionFlash", tempFlash);
			}
			response.sendRedirect(toJSP);
		} catch (Exception e) {
			try {
				// TODO log this exception
				System.err.println("Redirect Error");
				System.err.println("RequestURI was: '" + request.getRequestURI() + "'");
				System.err.println("PathInfo was:   '" + request.getPathInfo()   + "'");
				System.err.println("toJSP was:   '"    + toJSP                   + "'");
				render(errorJSP,request,response);
			} catch (Exception doubleException) {
				// TODO log this exception...very bad, since we couldn't even find the error page!
				try {
					PrintWriter out =response.getWriter(); 
					out.println("Couldn't find the error page!");
					System.err.println("Couldn't find errorJSP: '" + errorJSP + "'");
					doubleException.printStackTrace(out);
				} catch (IOException tripleException) {
					// Okay, something must be insane...couldn't even write to output!
					tripleException.printStackTrace();
				}
			}
		}
	}
	
	// Renders a JSP and handles up to three exceptions deep!
	protected void render(String toJSP, HttpServletRequest request, HttpServletResponse response){
		ServletContext context = getServletContext();
		try {
			context.getRequestDispatcher(toJSP).forward(request,response);
		} catch (Exception e) {
			try {
				// TODO log this exception
				System.err.println("Render Error");
				System.err.println("RequestURI was: '" + request.getRequestURI() + "'");
				System.err.println("PathInfo was:   '" + request.getPathInfo()   + "'");
				System.err.println("toJSP was:   '"    + toJSP                   + "'");
				context.getRequestDispatcher(errorJSP).forward(request,response);
			} catch (Exception doubleException) {
				// TODO log this exception...very bad, since we couldn't even find the error page!
				try {
					PrintWriter out =response.getWriter(); 
					out.println("Couldn't find the error page!");
					System.err.println("Couldn't find errorJSP: '" + errorJSP + "'");
					doubleException.printStackTrace(out);
				} catch (IOException tripleException) {
					// Okay, something must be insane...couldn't even write to output!
					tripleException.printStackTrace();
				}
			}
		}
	}

	/*
	 *   This is how to get around a lack of first-class functions in Java.
	 *   Instead of making a map of functions, make an internal class with a method you can over-ride.
	 */
	public abstract class Action {
		
		public abstract void start(HttpServletRequest request, HttpServletResponse response);
	}
	
	public class DispatchMap extends HashMap<String,Action>{
		private static final long serialVersionUID = -5757194562376737591L;
		
		String name;
		
		public DispatchMap(String name){
			this.name = name;
		}
		
		public String toString() {
			return name;
		}
	}
}
