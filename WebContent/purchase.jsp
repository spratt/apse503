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
	ArrayList<MethodPrice> prices = price.get(method.getId());
	
%>

<div class="table_1">
<table width="600" cellpadding="3px" cellspacing="3px">
<tr><td bgcolor="#ECECDF" colspan="2"><strong>Method Details</strong></td></tr>
<tr><td width="150">Name: </td><td align="left"><%=method.name %><input type="hidden" name="methodid" id="methodid" value="<%=method.getId() %>"></input></td></tr>
<tr><td width="150">Summary:</td><td align="left"><%=method.summary %></td></tr>
<tr><td colspan="2">Choose rate option:</td></tr>
<%
if(prices != null)
{
	Iterator i = prices.iterator();

	while(i.hasNext()){
		MethodPrice p = (MethodPrice)i.next(); %>
		<tr><td colspan="2"><input type="radio" name="rate" id="rate" value="<%=p.method_price_id %>"> <%= new java.text.DecimalFormat("$0.00").format(p.price) %> for <%=p.quantity %> uses</td></tr>
	<%}
}
%>
</table>
</div>
<div class="table_3">

<table width="600" cellpadding="3px" cellspacing="3px">
<tr>
<td bgcolor="#ECECDF" colspan="2"><strong>Purchase Details</strong></td>
</tr>
<tr><td width="150">Card Type: </td><td align="left"><select name="Card types">
	<option value="Card">-- Select Card Type --</option>
	<option value="Card">Visa</option>
	<option value="Card">MasterCard</option>
</select></td></tr>
<tr><td width="150">Card Number: </td><td align="left"><input type="text" name="Cardnumber"></td></tr>
<tr><td width="150">Expiry: </td><td align="left"><select name="ExpiryMonth">
<option value="1">01</option>
<option value="2">02</option>
<option value="3">03</option>
<option value="4">04</option>
<option value="5">05</option>
<option value="6">06</option>
<option value="7">07</option>
<option value="8">08</option>
<option value="9">09</option>
<option value="10">10</option>
<option value="11">11</option>
<option value="12">12</option>
</select>
<select name="ExpiryYear">
<option value="2009">2009</option>
<option value="2010">2010</option>
<option value="2011">2011</option>
<option value="2012">2012</option>
<option value="2013">2013</option>
<option value="2014">2014</option>
<option value="2015">2015</option>
</select>
</td></tr>
<tr><td width="150">Cardholder Name: </td><td align="left"><input type="text" name="Cardname"></td></tr>
<tr><td width="150">Code: </td><td align="left"><input type="text" name="code"></td></tr>
<tr><td width="150">&nbsp;</td><td><input type="submit" value="Purchase Now" /></td></tr>
</table>

</div>

<%@ include file="/nav/footer.jsp" %>

</form>
</body>

</html>