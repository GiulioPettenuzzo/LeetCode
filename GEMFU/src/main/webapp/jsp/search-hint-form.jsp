<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<title>Search Hint Form</title>
	</head>

  <body>
	<h1>Search Hint Form</h1>
	
	<form method="POST" action="<c:url value="/search-hint-by-problem"/>">
		<label for="problem">Problem:</label>
		<input name="problem" type="text"/><br/><br/>
		
		<button type="submit">Submit</button><br/>
		<button type="reset">Reset the form</button>
	</form>
	</body>
</html>
