<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<title>Create new Discussion</title>
	</head>

  <body>
	<h1>Create Discussion Form</h1>
	<form method="POST" action="<c:url value="/create-thread"/>">
		<label for="Enter a topic title">Title:</label>
		<input name="title" type="text"/><br/>

		<label for="Enter a topic text">Text:</label>
		<textarea name="text" cols="200" rows="35"></textarea><br/>

		<button type="submit">Submit</button>
		<button type="reset">Cancel</button>
	</form>
	</body>
</html>