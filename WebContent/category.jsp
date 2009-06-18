<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.*,apse503.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Category view</title>
</head>
<body>
<%@ include file="/nav/main-nav.jsp" %>

<div id ="top_methods">
	<label class="title">Category</label><br /><br />
	
	<%
	
	Category c = new Category();
	c.get(Integer.parseInt(request.getParameter("category")));

    %>

	<b><%=c.category%></b><br />
	<%
			ArrayList<Method> methods = new Method().getAllByCategory(c.id);
			Iterator<Method> i = methods.iterator();
			while(i.hasNext()){
				Method meth = (Method)i.next();%>
				<a href="<%=root%>/method/get?method=<%=meth.getId()%>" id="<%=meth.getId() %>"><%= meth.name %></a>&nbsp;<br />
				Summary:&nbsp;<%=meth.summary %><br />
				Description:&nbsp;<%= meth.description %><br /><br />

	<%}%>
	

</div>

</body>
</html>