<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.*,apse503.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Web Method Categories</title>
</head>
<body>
<%@ include file="/nav/main-nav.jsp" %>
<p class="text1">Some text info hereSome text info hereSome text info hereSome text info here</p>
<p class="text1">Some text info hereSome text info hereSome text info hereSome text info here</p>
</div>

<div class="input">
<p>Search <input type="text" name="search_input"><input type="submit" name="search" value="Search"></p> 

</div>



<div class="cats">

<%@ include file="/nav/cat_nav.jsp" %>
</div>

<%
	User myUser = ((User)request.getSession().getAttribute("user"));
	Method m = new Method(); 
	m.get(Integer.parseInt(request.getParameter("method")));
	Rating rating = new Rating();
	rating.method_id = m.getId();
	double avg = Rating.roundNearestHalf(rating.getAverageRating());
	User creator = new User().get(m.user_id);
	MethodPrice price = new MethodPrice();
	ArrayList<MethodPrice> prices = price.get(m.getId());
%>

<div class="table_2">
<table width="400" cellpadding="3px" cellspacing="3px">
<tr><td><td>
<td bgcolor="#ECECDF"><strong>Method: <%=m.name %></strong></td>
</tr>
<tr><td><td><td><td>
	Created by: <%=creator.userName %> <br /><br />
	Summary: <%=m.summary %><br />
	Detailed description: <%=m.description %><br />
	URL: <%=m.url %><br /><br />
	
	Pricing options:<br />
	
	<%
if(prices != null)
{
	Iterator i = prices.iterator();

	while(i.hasNext()){
		MethodPrice p = (MethodPrice)i.next(); %>
		<%= new java.text.DecimalFormat("$0.00").format(p.price) %> for <%=p.quantity %> uses  <br />
	<%}
}
%><br />
	
	<% for(; avg > 0.5; avg = avg-1.0){// Whole stars
				%><img src="<%=root%>/images/whole.JPG" height="12" /><%
			} if(avg >= 0.5) { // Half star
				%><img src="<%=root%>/images/half.JPG" height="12" /><%
			}%>
	&nbsp;<%=rating.getRatingsCount() %>&nbsp;reviews&nbsp;<u>read all reviews</u><br /><br />
	
	

<a href="<%=root%>/purchase/approve?id=<%=m.getId() %>">Purchase Method</a></td></tr>
</table>
</div>



</body>
</html>