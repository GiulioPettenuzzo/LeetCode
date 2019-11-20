<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<link href="bootstrap-4.0.0-dist/css/bootstrap.min.css" rel="stylesheet">
<link href="fontawesome-free-5.0.13/web-fonts-with-css/css/fontawesome-all.min.css" rel="stylesheet">
<script src="js/scriptFileMichele.js"></script>
<link href="css/stylesheetMichele.css" rel="stylesheet">
<meta name="viewport" content="width=device-width, initial-scale=1, height=device-height, initial-scale=1">

<title>Register</title>
</head>
<body >
	<div class="all-screen-lv4" onclick="closeRegisterWindow()">
  
 	</div>
	<div class="internal-div">
		<form method="POST" action="<c:url value="/create-user"/>">
		<nav class="navbar navbar-expand navbar-dark bg-dark">
			<h1 id="reg">Register</h1>
		</nav>
		
		
		<div class="row">
			<div class="col-md-2 reg-col ">
				<div id="profile" >
					<i class="fas fa-user-circle fa-3x" alt="Choice Image"></i>
				</div>
				<input type="text" name="profileImage" id="profileImage" style="display: none"/>
			</div>
			
	    </div>

		<div class="row">
			<div class="col-md-4 reg-col">
				<h5 class="myh5"><label for="username">Username:</label></h5>
			</div>
			<div class="col-md-4 reg-col">
				<p><input type="text" class="ins-reg form-control" name="username" maxlength="40" placeholder="Username" id="username" required /></p>
			</div>
		</div>

		<div class="row">
			<div class="col-md-4 reg-col">
				<h5 class="myh5"><label for="name">Name:</label></h5>
			</div>
			<div class="col-md-4 reg-col">
				<p><input type="text" class="ins-reg form-control" name="name" maxlength="40" placeholder="Name" id="name" required /></p>
			</div>
		</div>
		
		<div class="row">
			<div class="col-md-4 reg-col">
				<h5 class="myh5">Surname:</h5>
			</div>
			<div class="col-md-4 reg-col">
				<p><input type="text" class="ins-reg form-control" name="surname" maxlength="40" placeholder="Surname" id="surname" required /></p>
			</div>
		</div>

		<div class="row">
			<div class="col-md-4 reg-col">
				<h5 class="myh5">Email:</h5>
			</div>
			<div class="col-md-4 reg-col">
				<p><input type="email" class="ins-reg form-control" name="email" maxlength="40" placeholder="Email" id="email" required/></p>
			</div>
		</div>

		<div class="row">
			<div class="col-md-4 reg-col">
				<h5 class="myh5">Password:</h5>
			</div>
			<div class="col-md-4 reg-col">
				<p><input type="password" class="ins-reg form-control" name="password" maxlength="40" placeholder="Password" id="password" required/></p>
			</div>
		</div>

		<div class="row">
			<div class="col-md-4 reg-col">
				<h5 class="myh5">Birthday:</h5>
			</div>
			<div class="col-md-4 reg-col">
				<p><input type="date" class="ins-reg form-control" name="date" maxlength="40" required/></p>
			</div>
		</div>

		<div class="row">
			<div class="col-md-4 reg-col">
				<h5 class="myh5">Gender:</h5>
			</div>
			<div class="col-md-4 reg-col">
				<p>
				<select type="text" list="comps"  class="ins-reg form-control" name="gender" maxlength="40" placeholder="none" id="comps" style="margin-right: 3vw" required>
					<option value="1">Male</option>
					<option value="2">Female</option>
					<option value="3">Not specified</option>
				</select>
				</p>
			</div>
		</div>
		<div class="row">
			<div class="col-md-4 reg-col"><br>
				<p><h5 class="myh5"><label for="img">Profile Image:</label></h5></p>
		    </div>
		    <div class="col-md-4 reg-col"><br>
		    	<p><input type="file"  accept="image/*" name="img" id="img"  onchange="handleFiles(this.files)" style="margin-left: 3vw;width: 15px;"> </p>
		    </div>
		</div>

		<nav id="bottom-reg" class="navbar navbar-expand navbar-dark bg-dark justify-content-end">
				<button class="reg-btn" type="button" class="btn btn-light" onclick="closeRegisterWindow()"><b>Cancel</b></button>
				<button class="reg-btn" type="submit" class="btn btn-light" onclick="RegisterUser()"><b>Register</b></button>
		</nav>
		</form>
	</div>
	
</body>

</html>