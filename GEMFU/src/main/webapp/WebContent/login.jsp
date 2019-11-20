<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page session = "true" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<link href="css/style.css" rel="stylesheet">
    <link href="bootstrap-4.0.0-dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="css/botticss.css" rel="stylesheet">
    
    <link href="fontawesome-free-5.0.13/web-fonts-with-css/css/fontawesome-all.min.css" rel="stylesheet">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script type="text/javascript" src="bootstrap-4.0.0-dist/js/bootstrap.min.js"></script>
    <script src="js/scriptFileMichele.js"></script>
    <link href="css/stylesheetMichele.css" rel="stylesheet">
	<meta name="viewport" content="width=device-width, initial-scale=1, height=device-height, initial-scale=1">
	<title>Login</title>
</head>
<body >
	<form class="px-4 py-3" method="POST" action="<c:url value="/login-user"/>">
		<div class="form-group">
	    	<label for="username">Username</label>
	    	<input type="text" class="form-control" name="username" placeholder="username" required>
	  	</div>
	  	<div class="form-group">
	    	<label for="password">Password</label>
	    	<input type="password" class="form-control" name="password" placeholder="Password" required>
	  	</div>
	  	<button type="submit" class="btn btn-primary">Sign in</button>
	</form>

<script src="js/scriptFileMichele-end.js"></script>
</body>
</html>