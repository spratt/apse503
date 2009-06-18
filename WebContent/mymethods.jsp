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
	User myUser = ((User)request.getSession().getAttribute("user"));
	
	ArrayList<Method> purchMethods = myUser.getMyPurchased();
	
	String review = null;
	
	if(purchMethods != null)
	{
		Iterator i = purchMethods.iterator();
		
		int ranking = 1;
	
		while(i.hasNext()){
			Method meth = (Method)i.next(); 
			Rating rating = new Rating();
			rating.method_id = meth.getId();
			rating.getMyReview(myUser.getId()); %>
			<form method="POST" action="<%=root%>/rating/save">
				<%=ranking %>
				<input type="hidden" name="methodid" value="<%=meth.getId()%>" />
				<a href="<%=root%>/method/get?method=<%=meth.getId()%>" id="<%=meth.getId()%>"><%= meth.name %></a>&nbsp;
				My rating:&nbsp;
				<%if(rating.rating < 0){%>
					<select name="rating">
						<option value="1">1</option>
						<option value="2">2</option>
						<option value="3">3</option>
						<option value="4">4</option>
						<option value="5">5</option>
					</select>
				<%}
				  else{%>
				  	<%=rating.rating %>
				  <%}%>
				  &nbsp;
				My comment:&nbsp; 
				<%if(rating.rating < 0){%><input type="text" id="comment" name="comment" />&nbsp;<input type="submit" value="add" /><%}
				  else{%><%=rating.comment %><%} %>
				<% ranking++; %>
			</form>
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
			<%=ranking %> <a href="<%=root%>/method/get?method=<%=meth.getId()%>" id="<%=meth.getId() %>"><%= meth.name %></a>&nbsp;&nbsp;Times Purchased: <%= meth.getPurchaseDetails()%>
			<% ranking++; %>
			<br />
			<br />
		<%}
	}%>

</div>


<%@ include file="/nav/footer.jsp" %>
</body>
</html>