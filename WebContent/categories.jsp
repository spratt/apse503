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

<div id ="top_methods">
	<label class="title">Categories</label><br /><br />
	<%
	
	ArrayList<Category> category = new Category().getAll();
	
	if(category != null){
		Iterator<Category> catI = category.iterator();
		while(catI.hasNext()){
			Category cat = catI.next();
			%><b><%=cat.category%></b><br /><%
			ArrayList<Method> methods = new Method().getAllByCategory(cat.categoryID);
			Iterator<Method> i = methods.iterator();
			while(i.hasNext()){
				Method meth = (Method)i.next();%>
				<a href="<%=root%>/method/get?method=<%=meth.getId()%>" id="<%=meth.getId() %>"><%= meth.name %></a>&nbsp;<br />
				Summary:&nbsp;<%=meth.summary %><br />
				Description:&nbsp;<%= meth.description %><br /><br />
			<%}
		}
	}%>
	

</div>

</body>
</html>