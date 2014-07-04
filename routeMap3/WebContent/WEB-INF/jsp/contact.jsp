<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<h2>Contact Form</h2>
	<sf:form method="post" action="addContact.html">
		<table>
			<tr>
				<td><sf:label path="firstname">First Name</sf:label></td>
				<td><sf:input path="firstname" /></td>
			</tr>
			<tr>
				<td><sf:label path="lastname">Last Name</sf:label></td>
				<td><sf:input path="lastname" /></td>
			</tr>
			<tr>
				<td><sf:label path="lastname">Email</sf:label></td>
				<td><sf:input path="email" /></td>
			</tr>
			<tr>
				<td><sf:label path="lastname">Telephone</sf:label></td>
				<td><sf:input path="telephone" /></td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" value="Submit" /></td>
			</tr>
		</table>
	</sf:form>


</body>
</html>