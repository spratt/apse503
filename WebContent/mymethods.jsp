<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.*,apse503.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>My Methods</title>
</head>
<body>
<%@ include file="/nav/main-nav.jsp" %>




<div id ="my_purchased">
	<label class="title">Purchased Web Methods</label><br /><br />
	<%
	
	
	ArrayList<Method> purchMethods = ((User)request.getSession().getAttribute("user")).getMyPurchased();
	
	if(purchMethods != null)
	{
		Iterator i = purchMethods.iterator();
		
		int ranking = 1;
	
		while(i.hasNext()){
			Method meth = (Method)i.next(); 
			Rating rating = new Rating();
			rating.method_id = meth.getId();%>
			<label>
			<%=ranking %> <a href="#" id="<%=meth.getId() %>"><%= meth.name %></a>&nbsp;</label><label>average:</label> <%= rating.getAverageRating()%>&nbsp;<label>count:</label> <%= rating.getRatingsCount()%><br />
			<%= meth.summary %><br /><br />
			<% ranking++; %>
			<br />
			<br />
		<%}
	}%>
	

</div>

<hr /> 

<div id="my_contrib">
	<label class="title">Contributed Web Methods</label><br /><br />
	<%
	
	
	ArrayList<Method> contribMethods = ((User)request.getSession().getAttribute("user")).getMyContributed();
	
	if(contribMethods != null)
	{
		Iterator i = contribMethods.iterator();
		
		int ranking = 1;
	
		while(i.hasNext()){
			Method meth = (Method)i.next();%>
			<label>
			<%=ranking %> <a href="#" id="<%=meth.getId() %>"><%= meth.name %></a>&nbsp;</label><label>Times Purchased:</label> <%= meth.getTotalPurchases()%>&nbsp;<label>Money earned:</label> <%= meth.getEarnedByMethod()%><br />
			<%= meth.summary %><br /><br />
			<% ranking++; %>
			<br />
			<br />
		<%}
	}%>
	
</div>


<%@ include file="/nav/footer.jsp" %>
</body>
</html>