<%-- allows tags such as c:if --%>
<%@ page   import="apse503.User" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	String root = request.getContextPath();
%>
<img src="<%=root%>/images/main_logo.gif" style="float: right; margin: 4px" />
<style type="text/css">
<%@ include file="../css/web.css" %>
</style>



<% 
	User user = (User)session.getAttribute("user");
%>

<c:choose>
	<c:when test="${null == user}"> <%-- Not signed in --%>
		  <a href="<%=root%>/user/login">Sign in</a>
	</c:when>
	<c:otherwise>
		  <a href="<%=root%>/user/home">Home</a> 
		| <a href="<%=root%>/method/show">My Methods</a>
		| <a href="<%=root%>/method/submit">Contribute</a>
		| <a href="<%=root%>/category/show">Categories</a>
		| <a href="<%=root%>/user/logout">Sign out</a>
	</c:otherwise>
</c:choose> <br />


<%-- FLASH MESSAGE --%>
<% 
	String flash = (String)request.getAttribute("flash");
	String sessionFlash = (String)session.getAttribute("sessionFlash");
%>

<c:choose>
	<c:when test="${null != sessionFlash}"> <%-- Flash is not empty! --%>
		<div class="flash">
			<%= sessionFlash%>
		</div>
		<% session.removeAttribute("sessionFlash"); %>
	</c:when>
	<c:when test="${null != flash}">
		<div class="flash">
			<%= flash%>
		</div>
		<% request.removeAttribute("flash"); %>
	</c:when>
</c:choose>



<%-- Clear so that whatever appears after the nav begins below the floating logo --%>
<br clear="all" />