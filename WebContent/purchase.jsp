<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Purchase form</title>
</head>
<body>
<p>Some texts to explain the purchasing details</p>

<h3>Method Details</h3>
<form action="">Method name: <input type="text" name="Mname"
	value="Method name"> <br>
<br>
Summary: <input type="text" name="Msummary" value="Method summary">
<br>
<br>
Rate option: <input type="radio" checked="checked" name="rate"
	value="Option1"> Option 1 <br>
<input type="radio" name="rate" value="Option2"> Option 2 <br>
<input type="radio" name="rate" value="Option3"> Option 3 <br>
<p>
------------------------------------------------------------------</p>
<br>
<h3>Payment Details</h3>
<br>
Card Type: <select name="Card types">
	<option value="Card">-- Select Card Type --</option>
	<option value="Card">Visa</option>
	<option value="Card">Master</option>
	<option value="Card">Paypal</option>
</select> <br>
<br>
Cardholder Number: <input type="text" name="Cardnumber"> <br>
	
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
</body>
</html>