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

<p class="text1">Some text info hereSome text info hereSome text info hereSome text info here</p>
<p class="text1">Some text info hereSome text info hereSome text info hereSome text info here</p>
</div>

<div class="input">
<p>Search <input type="text" name="search_input"><input type="submit" name="search" value="Search"></p> 

</div>



<div class="table_1">

<table width="600" cellpadding="3px" cellspacing="3px">
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

</body>
</html>