<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<title>Create Submission Form</title>
	</head>

  <body>
	<h1>Create Submission Form</h1>
	
	<form method="POST" action="<c:url value="/create-submission"/>">
		<label for="username">User:</label>
		<input name="username" type="text"/><br/>
		
		<label for="problem">Problem:</label>
		<input name="problem" type="text"/><br/>

		<label for="title">Title:</label>
		<input name="title" type="text"/><br/>
		
		
		<label for="text">Text:</label>
		<input name="text" type="text"/><br/><br/>

		<button type="submit">Submit</button><br/>
		<button type="reset">Reset the form</button>
	</form>
	</body>
</html>