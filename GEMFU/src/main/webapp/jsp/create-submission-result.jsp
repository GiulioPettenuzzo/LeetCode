<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
	<head>
		<title>Create Submission</title>
	</head>

	<body>
		<h1>Create Submission</h1>
		<hr/>
		
		<!-- display the message -->
		<c:import url="/jsp/include/show-message.jsp"/>

		<!-- display the just created employee, if any and no errors -->
		<c:if test='${not empty submission && !message.error}'>
			<ul>
				<li>problem: <c:out value="${submission.problem}"/></li>
				<li>title: <c:out value="${submission.title}"/></li>
				<li>date: <c:out value="${submission.date}"/></li>
				<li>text: <c:out value="${submission.text}"/></li>
				<li>username: <c:out value="${submission.username}"/></li>
			</ul>
		</c:if>
	</body>
</html>