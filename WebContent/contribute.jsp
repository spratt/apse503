<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Contribute Web Method</title>

<script language="javascript" type="text/javascript">
<!--
function imposeMaxLength(Object, MaxLen)
{
  return (Object.value.length <= MaxLen);
}
-->
</script>

<%@ include file="/nav/main-nav.jsp" %>

</head>
<body>

<div id="submit_intro" class="intro">introduction text to go here</div>

<div id="contribute_form" class="form">

<form method="POST" action="contribute.jsp">
	Method name: <input type="text" id="method_name" name="method_name" /><br />
	Summary: <input type="text" id="summary" name="summary" /><br />
	Category: <select id="category" name="category" />
	<option>-- Select Category --</option>
	<option>Category 1</option>
	</select>
	<br />
	Detailed description: <textarea name="description" onkeypress="return imposeMaxLength(this, 200);" ></textarea> <br />
	Method file: <input type="file" name="datafile" size="30"><br />
	Some details about the payment options or rates etc. <br />
	Rate 1: <input type="text" id="rate_one" name="rate_one" maxsize="8" length="8" /> for <input type="text" id="rate_one_uses" name="rate_one_uses" maxsize="8" length="8" /> uses.<br />
	Rate 2: <input type="text" id="rate_two" name="rate_two" maxsize="8" length="8" /> for <input type="text" id="rate_two_uses" name="rate_two_uses" maxsize="8" length="8" /> uses.<br />
	Rate 3: <input type="text" id="rate_three" name="rate_three" maxsize="8" length="8" /> for <input type="text" id="rate_three_uses" name="rate_three_uses" maxsize="8" length="8" /> uses.<br />
	<button type="submit">Submit</button>
</form>

</div>
</body>
</html>