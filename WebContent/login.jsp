<%@page import="apse503.User"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>
</head>
<body>
<form method="POST" action="/user/login">
	Username: <input type="text" id="username" name="username" />
	Password: <input type="password" id="password" name="password" />
	<button type="submit">Log In</button>
</form>
</body>
</html>