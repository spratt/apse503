<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Signup</title>
</head>
<body>
<form method="POST" action="signup.jsp">
	Username: <input type="text" id="username" name="username" /><br />
	Password: <input type="password" id="password" name="password" /><br />
	Password Confirmation: <input type="password" id="confirm_password" name="confirm_password" /><br />
	First Name: <input type="text" id="firstname" name="firstname" /><br />
	Last Name: <input type="text" id="lastname" name="lastname" /><br />
	Email: <input type="text" id="email" name="email" /><br />
	Address: <input type="text" id="address" name="address" /><br />
	City: <input type="text" id="city" name="city" /><br />
	Province/State: <input type="text" id="province" name="province" /><br />
	Country: <input type="text" id="country" name="country" /><br />
	Postal/Zip Code: <input type="text" id="postalcode" name="postalcode" /><br />
	<button type="submit">Sign Up</button>
</form>
</body>
</html>