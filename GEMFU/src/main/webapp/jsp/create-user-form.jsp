<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<title>Create Employee Form</title>
	</head>

  <body>
	<h1>Create Employee Form</h1>
	
	<form method="POST" action="<c:url value="/create-user"/>">
		<label for="username">Username:</label>
		<input name="username" type="text"/><br/>
		
		<label for="password">Password:</label>
		<input name="password" type="text"/><br/>

		<label for="name">Name:</label>
		<input name="name" type="text"/><br/>

		<label for="surname">Surname:</label>
		<input name="surname" type="text"/><br/>
		
		<label for="date">Date:</label>
		<input name="date" type="date"/><br/>
		
		<label for="gender">Gender:</label>
		<input name="gender" type="text"/><br/><br/>

		<label for="email">Email:</label>
		<input name="email" type="text"/><br/>

		<label for="profileImage">Image Profile:</label>
		<input name="profileImage" type="text"/><br/>

		<button type="submit">Submit</button><br/>
		<button type="reset">Reset the form</button>
	</form>
	</body>
</html>