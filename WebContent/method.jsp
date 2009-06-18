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

<%
	User myUser = ((User)request.getSession().getAttribute("user"));
	Method m = new Method(); 
	m.get(Integer.parseInt(request.getParameter("method")));
	Rating r = new Rating();
	r.method_id = m.getId();
%>
	
	Method name: <%=m.name %><br />
	URL: <%=m.url %><br />
	Summary: <%=m.summary %><br />
	Detailed description: <%=m.description %><br />
	Rating: <%=r.getAverageRating() %><br />
	Number of reviews: <%=r.getRatingsCount() %>&nbsp;<u>view ratings link to be added</u><br /><br />
	Created by: <%=myUser.userName %> <br /><br />
	

<a href="<%=root%>/purchase/approve?id=<%=m.getId() %>">Purchase Method</a>

</body>
</html>