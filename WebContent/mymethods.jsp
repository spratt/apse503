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


<div class="table_1">

<table width="600" cellpadding="3px" cellspacing="3px">
<tr>
<td bgcolor="#ECECDF" colspan="4"><strong>Purchased Web Methods</strong></td>
</tr>
<tr><td class="title">Name</td><td class="title">used/total</td><td class="title">My rating</td><td class="title">My comment</td></tr>
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
			MethodUse mu = new MethodUse();
			mu.methodID = meth.id;
			mu.userID = myUser.id;
			int totalPurch = mu.totalPurchased();
			int totalUsed = mu.totalUsed();
			
			if(totalPurch == -1){
				totalPurch = 0;
			}
			
			if(totalUsed == -1){
				totalUsed = 0;
			}
			
			
			
			Rating rating = new Rating();
			rating.method_id = meth.getId();
			rating.getMyReview(myUser.getId());%>
			<form method="POST" action="<%=root%>/rating/save">
				<tr><td><%=ranking %>
				<input type="hidden" name="methodid" value="<%=meth.getId()%>" />
				<a href="<%=root%>/method/get?method=<%=meth.getId()%>" id="<%=meth.getId()%>"><%= meth.name %></a></td>
				<td><%=totalUsed %>/<%=totalPurch %></td>
				<td>
				<%if(rating.rating < 0){%>
					<select name="rating">
						<option value="0">0</option>
						<option value="1">1</option>
						<option value="2">2</option>
						<option value="3">3</option>
						<option value="4">4</option>
						<option value="5">5</option>
					</select>
				<%}
				  else{
				  double avg = rating.rating; %><% for(; avg > 0.5; avg = avg-1.0){
				%><img src="<%=root%>/images/whole.JPG" height="12" /><%
			}%>
				  <%}%>
				  </td>
				<td> 
				<%if(rating.rating < 0){%><input type="text" id="comment" name="comment" />&nbsp;<input type="submit" value="add" /><%}
				  else{%><%=rating.comment %><%} %>
				<% ranking++; %></td></tr>
				</form>
		<%}
	}%>
	
</table>

</div>

<div class="table_3">
<table width="600" cellpadding="3px" cellspacing="3px">
<tr>
<td bgcolor="#ECECDF" colspan="2"><strong>Contributed Web Methods</strong></td>
</tr>
<tr><td class="title">Name</td><td class="title"># purchases for $ earned</td></tr>
	<%
	
	
	ArrayList<Method> contribMethods = ((User)request.getSession().getAttribute("user")).getMyContributed();
	
	if(contribMethods != null)
	{
		Iterator i = contribMethods.iterator();
		
		int ranking = 1;
	    
		while(i.hasNext()){
			Method meth = (Method)i.next();%>
			<tr><td><%=ranking %> <a href="<%=root%>/method/get?method=<%=meth.getId()%>" id="<%=meth.getId() %>"><%= meth.name %></a></td><td><%= meth.getPurchaseDetails()%></td></tr>
			<% ranking++; %>
		<%}
	}%>
</table>
</div>


<%@ include file="/nav/footer.jsp" %>
</body>
</html>