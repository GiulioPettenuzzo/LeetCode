<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<title>Create Hint Form</title>
	</head>

  <body>
	<h1>Create Hint Form</h1>
	
	<form method="POST" action="<c:url value="/create-hint"/>">
		<label for="id">Id:</label>
		<input name="id" type="text"/><br/>
		
		<label for="problem">Problem:</label>
		<input name="problem" type="text"/><br/>
		
		<label for="description">Description:</label>
		<input name="description" type="text"/><br/>
		
		<button type="submit">Submit</button><br/>
		<button type="reset">Reset the form</button>
	</form>
	</body>
</html>
