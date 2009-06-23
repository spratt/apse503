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





<div class="cats">

<%@ include file="/nav/cat_nav.jsp" %>
</div>



<div class="table_2">

<table width="400" cellpadding="3px" cellspacing="3px">
<tr>
<td bgcolor="#ECECDF"><strong>Category</strong></td>
</tr>
	
	<%
	
	Category c = new Category();
	c.get(Integer.parseInt(request.getParameter("category")));

    %>

	<tr><td><b><%=c.category%></b><br />
	<%
			ArrayList<Method> methods = new Method().getAllByCategory(c.id);
			Iterator<Method> i = methods.iterator();
			while(i.hasNext()){
				Method meth = (Method)i.next();%>
				Method: <a href="<%=root%>/method/get?method=<%=meth.getId()%>" id="<%=meth.getId() %>"><%= meth.name %></a>&nbsp;<br />
				Method Summary:&nbsp;<%=meth.summary %><br />
				Description:&nbsp;<%= meth.description %><br /><br />

	<%}%></td></tr>
	
</table>
</div>
<%@ include file="/nav/footer.jsp" %>
</body>
</html>