<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
	<head>
		<title>Create Hint</title>
	</head>

	<body>
		<h1>Create Hint</h1>
		<hr/>
		
		<!-- display the message -->
		<c:import url="/jsp/include/show-message.jsp"/>

		<!-- display the just created employee, if any and no errors -->
		<c:if test='${not empty hint && !message.error}'>
			<ul>
				<li>id: <c:out value="${hint.id}"/></li>
				<li>problem: <c:out value="${hint.problem}"/></li>
				<li>description: <c:out value="${hint.description}"/></li>
				
			</ul>
		</c:if>
	</body>
</html>
