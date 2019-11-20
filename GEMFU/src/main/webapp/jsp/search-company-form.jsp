<!--
 Author: GEMFU
 Version: 1.0
 Since: 1.0
-->

<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<title>Search Company Form</title>
	</head>

  <body>
	<h1>Search Company Form</h1>
	
	<form method="POST" action="<c:url value="/search-company-by-name"/>">
		<label for="name">Name:</label>
		<input name="name" type="text"/><br/><br/>
		
		<button type="submit">Submit</button><br/>
		<button type="reset">Reset the form</button>
	</form>
	</body>
</html>
