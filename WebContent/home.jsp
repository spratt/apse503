<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.*,apse503.Category"%>
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

</body>
</html>