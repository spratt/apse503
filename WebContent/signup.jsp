<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.Map"%>
<%
	Map<String,Object> Params = request.getParameterMap();
	String userName = "",
		   firstName = "",
		   lastName = "",
		   email = "",
		   address = "",
		   city = "",
		   postalCode = "",
		   province = "",
		   country = "";
	if(null != Params.get("username")) userName=request.getParameter("username");
	if(null != Params.get("firstname")) firstName=request.getParameter("firstname");
	if(null != Params.get("lastname")) lastName=request.getParameter("lastname");
	if(null != Params.get("email")) email=request.getParameter("email");
	if(null != Params.get("address")) address=request.getParameter("address");
	if(null != Params.get("city")) city=request.getParameter("city");
	if(null != Params.get("postalcode")) postalCode=request.getParameter("postalcode");
	if(null != Params.get("province")) province=request.getParameter("province");
	if(null != Params.get("country")) country=request.getParameter("country");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Register</title>
</head>
<body>
	<%@ include file="/nav/main-nav.jsp" %>
	<script type="text/javascript" src="<%=root%>/js/jquery-1.3.2.js"></script>
	<form id="registration" method="POST" action="<%=root%>/user/signup">
		<table>
			<tr>
				<td class="label">Username:</td>
				<td><input type="text" id="username" name="username" value="<%=userName%>" /></td>
			</tr>
			<tr>
				<td class="label">Password:</td>
				<td><input type="password" id="password" name="password" /></td>
			</tr>
			<tr>
				<td class="label">Confirm Password:</td>
				<td><input type="password" id="confirm_password" name="confirm_password" /></td>
			</tr>
			<tr>
				<td class="label">First Name:</td>
				<td><input type="text" id="firstname" name="firstname" value="<%=firstName%>" /></td>
			</tr>
			<tr>
				<td class="label">Last Name:</td>
				<td><input type="text" id="lastname" name="lastname" value="<%=lastName%>" /></td>
			</tr>
			<tr>	
				<td class="label">Email:</td>
				<td><input type="text" id="email" name="email" value="<%=email%>" /></td>
			</tr>
			<tr>
				<td class="label">Address:</td>
				<td><input type="text" id="address" name="address" value="<%=address%>" /></td>
			</tr>
			<tr>
				<td class="label">City:</td>
				<td><input type="text" id="city" name="city" value="<%=city%>" /></td>
			</tr>
			<tr>
				<td class="label">Province/State:</td>
				<td><input type="text" id="province" name="province" value="<%=province%>" /></td>
			</tr>
			<tr>
				<td class="label">Country:</td>
				<td>
					<select id="country" name="country" />
						<option>-- Select Country --</option>
						<option id="Canada">Canada</option>
						<option id="USA">USA</option>	
					</select>
					<c:if test="${null != country}">
						<script type="text/javascript">$("#<%=country%>").attr("selected","true")</script>
					</c:if>
				</td>
			</tr>
			<tr>
				<td class="label">Postal/Zip Code:</td>
				<td><input type="text" id="postalcode" name="postalcode" value="<%=postalCode%>" /></td>
			</tr>
			<tr>
				<td class="label">&nbsp;</td><td><button type="submit">Register!</button></td>
			</tr>
		</table>
	</form>
	<%@ include file="/nav/footer.jsp" %>
</body>
</html>