<%-- allows tags such as c:if --%>
<%@ page   import="apse503.User" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	String root = request.getContextPath();
%>
<img src="<%=root%>/images/ravenbay_logo.jpg" style="position:absolute;top:10px;right:475px;" width="174" height="89" border="1" />

<link rel="stylesheet" href="../css/web.css" />

<% 
	User user = (User)session.getAttribute("user");
%>

<div class="main_nav">
<div align="center">
<div class="main_nav_links">
<c:choose>
	<c:when test="${null == user}"> 
		<%-- Not signed in --%>
	</c:when>
	<c:otherwise>
		  <p class="nav"><a href="<%=root%>/user/home">Home</a></p>
		| <p class="nav"><a href="<%=root%>/method/show">My Methods</a></p>
		| <p class="nav"><a href="<%=root%>/method/submit">Contribute</a></p>
		| <p class="nav"><a href="<%=root%>/user/logout">Sign out</a></p>
	</c:otherwise>
</c:choose> 
</div>
</div>









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