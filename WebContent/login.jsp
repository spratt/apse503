<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>

</head>
<body>

<%@ include file="/nav/main-basic.jsp" %>


<div class="table_1">

<form method="POST" action="<%=root%>/user/login">
<table width="600" cellpadding="3px" cellspacing="3px">
<tr>
<td bgcolor="#ECECDF" colspan="2"><strong>Returning user?</strong></td>
</tr>
<tr>
	<td>Username:</td><td><input type="text" id="username" name="username" /></td>
</tr>
<tr>
	<td>Password:</td><td><input type="password" id="password" name="password" /></td>
</tr>
<tr>
	<td>&nbsp;</td><td><button type="submit">Sign In</button></td>
</tr>
<tr>
<td colspan="2"><hr></td>
</tr>
<tr>
<td><strong>New user?</strong></td><td><button type="button" onclick="location.href='<%=root%>/user/signup'">Register now</button></td>
</tr>

</table>
</form>

</div>

<%@ include file="/nav/footer.jsp" %>
</body>
</html>