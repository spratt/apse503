<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" import="java.util.*,apse503.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Purchase form</title>

</head>

<body>
<%@ include file="/nav/main-nav.jsp" %>
<form method="POST" action="<%=root%>/purchase/buy">
<%
	Method method = new Method();
	MethodPrice price = new MethodPrice();
	method.get(Integer.parseInt(request.getParameter("id")));
	ArrayList<MethodPrice> prices = price.get(method.id);
	
%>
<p>Some texts to explain the purchasing details</p>

<h3>Method Details</h3>
Name: <%=method.name %>
<input type="hidden" name="methodid" id="methodid" value="<%=method.id %>"></input>
<br>
Summary:<%=method.summary %>
<br>
<br>
Rate option:<br /> 
<%
if(prices != null)
{
	Iterator i = prices.iterator();

	while(i.hasNext()){
		MethodPrice p = (MethodPrice)i.next(); %>
		<input type="radio" checked="checked" name="rate" id="rate" value="<%=p.method_price_id %>"> <%=p.quantity %> - $<%=p.price %> <br>
	<%}
}
%>

<p>
------------------------------------------------------------------</p>
<br>
<h3>Payment Details</h3>
<br>
Card Type: <select name="Card types">
	<option value="Card">-- Select Card Type --</option>
	<option value="Card">Visa</option>
	<option value="Card">Master</option>
</select> <br>
<br>
Card Number: <input type="text" name="Cardnumber"> <br>
	
Expiry: <select name="Expiry">
<option value="month">mm</option>
</select>
<select name="Expiry1">
<option value="year">yyyy</option>
</select>
<br>
Cardholder Name: <input type="text" name="Cardname"> <br>
Code: <input type="text" name="Code"> <br>
<br>
<input type="submit" value="Purchase Now" />
<br>
</form>
<%@ include file="/nav/footer.jsp" %>
</body>

</html>