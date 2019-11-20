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
		<title>Create company</title>
	</head>

	<body>
		<h1>Create company</h1>
		<hr/>
		
		<!-- display the message -->
		<c:import url="/jsp/include/show-message.jsp"/>

		<!-- display the just created company, if any and no errors -->
		<c:if test='${not empty company && !message.error}'>
			<ul>
				<li>name: <c:out value="${company.name}"/></li>
			</ul>
		</c:if>
	</body>
</html>
