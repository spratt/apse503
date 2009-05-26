<%-- allows tags such as c:if --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<img src="<%=request.getContextPath()%>/images/main_logo.gif" style="float: right; margin: 4px" />
<style type="text/css">
<%@ include file="../css/web.css" %>
</style>

<a href="/apse503/">Home</a> 
| <a href="<%=request.getContextPath()%>/contribute.jsp">Contribute</a>
| future navigation item
<% 
	Integer userid = (Integer)session.getAttribute("userid");
%>

<c:choose>
	<c:when test="${null == userid}"> <%-- Not signed in --%>
		| <a href="<%=request.getContextPath()%>/login">Sign in</a>
	</c:when>
	<c:otherwise>
		| <a href="<%=request.getContextPath()%>/logout">Sign out</a>
	</c:otherwise>
</c:choose>
<br clear="all" />