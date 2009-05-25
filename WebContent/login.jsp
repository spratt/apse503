<%-- Should probably include a generic header here in the future --%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>
</head>
<body>
<%@ include file="/nav/main-nav.jsp" %>
	<% String flash = (String)request.getAttribute("flash");%>
	<c:if test="${null != flash}">
		<div class="error">
			<%=flash%>
		</div>
	</c:if>
	<form method="POST" action="login">
		<table>
			<tr>
				<td class="label">Username:</td><td><input type="text" id="username" name="username" /></td>
			</tr>
			<tr>
				<td class="label">Password:</td><td><input type="password" id="password" name="password" /></td>
			</tr>
			<tr>
				<td>&nbsp;</td><td><button type="submit">Sign In</button></td>
			</tr>
		</table>
	</form>
	<hr />
	<table>
		<tr>
			<td class="label">New User?</td>
			<td><button type="button" onclick="location.href='signup'">Register now</button></td>
		</tr>
	</table>
<%@ include file="/nav/footer.jsp" %>
</body>
</html>