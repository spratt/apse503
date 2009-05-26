<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Register</title>
</head>
<body>
	<%@ include file="/nav/main-nav.jsp" %>
	<form method="POST" action="<%=root%>/user/signup">
		<table>
			<tr>
				<td class="label">Username:</td><td><input type="text" id="username" name="username" /></td>
			</tr>
			<tr>
				<td class="label">Password:</td><td><input type="password" id="password" name="password" /></td>
			</tr>
			<tr>
				<td class="label">Confirm Password:</td><td><input type="password" id="confirm_password" name="confirm_password" /></td>
			</tr>
			<tr>
				<td class="label">First Name:</td><td><input type="text" id="firstname" name="firstname" /></td>
			</tr>
			<tr>
				<td class="label">Last Name:</td><td><input type="text" id="lastname" name="lastname" /></td>
			</tr>
			<tr>	
				<td class="label">Email:</td><td><input type="text" id="email" name="email" /></td>
			</tr>
			<tr>
				<td class="label">Address:</td><td><input type="text" id="address" name="address" /></td>
			</tr>
			<tr>
				<td class="label">City:</td><td><input type="text" id="city" name="city" /></td>
			</tr>
			<tr>
				<td class="label">Province/State:</td><td><input type="text" id="province" name="province" /></td>
			</tr>
			<tr>
				<td class="label">Country:</td>
				<td>
					<select id="country" name="country" />
						<option>-- Select Country --</option>
						<option>Canada</option>
						<option>United States</option>	
					</select>
				</td>
			</tr>
			<tr>
				<td class="label">Postal/Zip Code:</td><td><input type="text" id="postalcode" name="postalcode" /></td>
			</tr>
			<tr>
				<td class="label">&nbsp;</td><td><button type="submit">Register!</button></td>
			</tr>
		</table>
	</form>
	<%@ include file="/nav/footer.jsp" %>
</body>
</html>