<%@ page   import="java.util.*,apse503.*" %>
    

<div class="table_1">

<table width="200" cellpadding="3px" cellspacing="3px">
<tr>
<td bgcolor="#ECECDF"><strong>Categories</strong></td>
</tr>

	<%
	ArrayList<Category> categories = new Category().getAll();
	
	if(categories != null)
	{
		Iterator i = categories.iterator();
	
		while(i.hasNext()){
			Category cat = (Category)i.next(); %>
			<tr><td>
			<a href="<%=root%>/category/get?category=<%=cat.categoryID %>" id="<%=cat.categoryID %>"><%= cat.category %></a></td></tr>
		<%}
	}%>
</table>
</div>