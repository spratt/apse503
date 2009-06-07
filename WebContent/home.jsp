<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.*,apse503.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome</title>

</head>
<body>
<%@ include file="/nav/main-nav.jsp" %>


<h2>Welcome, <%= ((User)session.getAttribute("user")).userName %>!</h2>



<%@ include file="/nav/footer.jsp" %>

<div id="categories">

	<%
	ArrayList<Category> categories = new Category().getAll();
	
	if(categories != null)
	{
		Iterator i = categories.iterator();
	
		while(i.hasNext()){
			Category cat = (Category)i.next(); %>
			<label>
			<a href="#" id="<%=cat.categoryID %>"><%= cat.category %></a></label><br /><br />
		<%}
	}%>
	
</div>

<div id ="top_methods">

	<%
	ArrayList<Method> methods = new Method().getAll();
	
	if(methods != null)
	{
		Iterator i = methods.iterator();
		
		int ranking = 1;
	
		while(i.hasNext()){
			Method meth = (Method)i.next(); %>
			<label>
			<%=ranking %> <a href="#" id="<%=meth.getId() %>"><%= meth.name %></a></label><br />
			<%= meth.summary %><br /><br />
			<% ranking++; %>
			
			<%
			Rating rating = new Rating();
			rating.method_id = meth.getId();%>
			<label>average:</label> <%= rating.getAverageRating()%><br />
			<label>count:</label> <%= rating.getRatingsCount()%><br />
			<br />
		<%}
	}%>
	

</div>

</body>
</html>