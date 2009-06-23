<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.*,apse503.Category"%>
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

//-->
</script>



</head>
<body>
<%@ include file="/nav/main-nav.jsp" %>

<div class="table_1">


<form name="contribute" ENCTYPE='multipart/form-data' method="POST" action="<%=root%>/method/save" > 
<table width="600" cellpadding="3px" cellspacing="3px">
<tr>
<td bgcolor="#ECECDF" colspan="4"><strong>Method information</strong></td>
</tr>
<tr>
	<td class="label">Method name:</td><td><input type="text" id="name" name="name" /></td>
</tr>
<tr>
	<td class="label">Summary:</td><td><input type="text" id="summary" name="summary" /></td>
</tr>
<tr>
	<td class="label">Category:</td>
	<td>
	<select id="categoryid" name="categoryid" />
	<option>-- Select Category --</option>
	<%
	ArrayList<Category> categories = (ArrayList<Category>)request.getAttribute("categories");
	
	if(categories != null)
	{
		Iterator i = categories.iterator();
	
		while(i.hasNext()){
			Category cat = (Category)i.next(); %>
			<option value="<%=cat.categoryID %>"><%= cat.category %></option>
		<%}
	}%>
	</select>
	</td>
</tr>
<tr>
	<td class="label">Detailed description:</td><td><textarea id ="description" name="description" onkeypress="return imposeMaxLength(this, 200);" ></textarea></td>
</tr>
<tr>
	<td class="label">Method file:</td><td><input type="file" id="filename" name="filename" size="30"></td>
</tr>
<tr>
	<td colspan="4"><b>Enter your three rate options below ($ per # of uses):</b></td>
</tr>
<tr>
	<td class="label">Rate 1:</td><td><input type="text" id="rate_one" name="rate_one" maxsize="8" length="8" /> for <input type="text" id="rate_one_uses" name="rate_one_uses" maxsize="8" length="8" /> uses.</td>
</tr>
<tr>
	<td class="label">Rate 2:</td><td><input type="text" id="rate_two" name="rate_two" maxsize="8" length="8" /> for <input type="text" id="rate_two_uses" name="rate_two_uses" maxsize="8" length="8" /> uses.</td>
</tr>
<tr>
	<td class="label">Rate 3:</td><td><input type="text" id="rate_three" name="rate_three" maxsize="8" length="8" /> for <input type="text" id="rate_three_uses" name="rate_three_uses" maxsize="8" length="8" /> uses.</td>
</tr>
<tr><td>&nbsp;</td><td><input type="checkbox" name="terms" onClick="document.contribute['submit'].disabled =(document.contribute['submit'].disabled)? false : true"> I agree to the <a href="#">Terms and Conditions</a></td></tr>

<tr>
	<td>&nbsp;</td><td><input type="submit" name="submit" id="submit" value="Submit Method" disabled /></td>
</tr>
</table>
</form>
</div>
<%@ include file="/nav/footer.jsp" %>
</body>
</html>