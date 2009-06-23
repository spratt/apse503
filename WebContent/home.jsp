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

<div class="cats">
<%@ include file="/nav/cat_nav.jsp" %>
</div>

<div class="table_2">
<table width="400" cellpadding="3px" cellspacing="3px">
<tr>
<td bgcolor="#ECECDF"><strong>Top Methods</strong></td>
</tr>
	<%
	ArrayList<Method> methods = new Method().getTopTen();
	
	if(methods != null)
	{
		Iterator i = methods.iterator();
		
		int ranking = 1;
	
		while(i.hasNext()){
			Method meth = (Method)i.next(); 
			Rating rating = new Rating();
			rating.method_id = meth.getId();
			double avg = Rating.roundNearestHalf(rating.getAverageRating());
			%>
			<tr><td>
			<%=ranking %>.&nbsp;Method: 
			<a href="<%=root%>/method/get?method=<%=meth.getId()%>" id="<%=meth.getId() %>"><%= meth.name %></a>&nbsp;
			<% for(; avg > 0.5; avg = avg-1.0){// Whole stars
				%><img src="<%=root%>/images/whole.JPG" height="12" /><%
			} if(avg >= 0.5) { // Half star
				%><img src="<%=root%>/images/half.JPG" height="12" /><%
			}%>
			<%= rating.getRatingsCount()%>&nbsp;reviews<br />
			Summary: <%= meth.summary %></td></tr>
			<% ranking++; %>
		<%}
	}%>
	
</table>
</div>

<%@ include file="/nav/footer.jsp" %>

</body>
</html>