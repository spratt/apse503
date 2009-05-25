<%-- allows tags such as c:if --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<img src="/apse503/images/main_logo.gif" style="float: right; margin: 4px" />
<style type="text/css">
<%@ include file="../css/web.css" %>
</style>
<%-- 
	These links will break during deployment unless the app is served from the apse503 folder,
	as it is on the development machines.
--%>
<a href="/apse503/">Home</a> 
| future navigation item
| future navigation item
<% 
	Integer userid = (Integer)session.getAttribute("userid");
%>
<c:if test="">
</c:if>
<c:choose>
	<c:when test="${null == userid}"> <%-- Not signed in --%>
		| <a href="/apse503/user/login">Sign in</a>
	</c:when>
	<c:otherwise>
		| <a href="/apse503/user/logout">Sign out</a>
	</c:otherwise>
</c:choose>
<br clear="all" />