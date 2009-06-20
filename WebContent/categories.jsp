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



<div class="table_2">

<table width="400" cellpadding="3px" cellspacing="3px">
<tr>
<td bgcolor="#ECECDF"><strong>Categories</strong></td>
</tr>
	<%
	
	ArrayList<Category> category = new Category().getAll();
	
	if(category != null){
		Iterator<Category> catI = category.iterator();
		while(catI.hasNext()){
			Category cat = catI.next();
			%><tr><td><b><%=cat.category%></b><br /><%
			ArrayList<Method> methods = new Method().getAllByCategory(cat.categoryID);
			Iterator<Method> i = methods.iterator();
			while(i.hasNext()){
				Method meth = (Method)i.next();%>
				Method: <a href="<%=root%>/method/get?method=<%=meth.getId()%>" id="<%=meth.getId() %>"><%= meth.name %></a>&nbsp;<br />
				Method Summary:&nbsp;<%=meth.summary %><br /><br />
			<%}
		}%></td></tr><%
	}%>
	
</table>
</div>

</body>
</html>