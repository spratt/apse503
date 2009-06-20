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

<p class="main_title">Welcome, <%= ((User)session.getAttribute("user")).userName %>!</p>
<p class="text1">Some text info hereSome text info hereSome text info hereSome text info here</p>
<p class="text1">Some text info hereSome text info hereSome text info hereSome text info here</p>
</div>

<div class="input">
<p>Search <input type="text" name="search_input"><input type="submit" name="search" value="Search"></p> 

</div>

<div id="home_content">

<div class="table_1">

<table width="200" cellpadding="3px" cellspacing="3px">
<tr>
<td bgcolor="#ECECDF"><strong>Categories</strong></td>
</tr>

	<%
	ArrayList<Category> categories = new Category().getAll();
	
	if(categories != null)
	{
		Iterator i = categories.iterator();
	
		while(i.hasNext()){
			Category cat = (Category)i.next(); %>
			<tr><td>
			<a href="<%=root%>/category/get?category=<%=cat.categoryID %>" id="<%=cat.categoryID %>"><%= cat.category %></a></td></tr>
		<%}
	}%>
</table>
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
				%><img src="<%=root%>/images/star.png" height="10" /><%
			} if(avg >= 0.5) { // Half star
				%><img src="<%=root%>/images/half_star.png" height="10" /><%
			}%>|<%=avg %>|
			<%= rating.getRatingsCount()%>&nbsp;reviews<br />
			Method Summary: <%= meth.summary %></td></tr>
			<% ranking++; %>
		<%}
	}%>
	
</table>
</div>
</div>
<%@ include file="/nav/footer.jsp" %>

</body>
</html>