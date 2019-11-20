<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
	<head>
		<title>Create Thread</title>
	</head>

	<body>
		<h1>Create Thread</h1>
		<hr/>

		<!-- display the message -->
		<c:import url="/jsp/include/show-message.jsp"/>

		<!-- display the just created employee, if any and no errors -->
		<c:if test='${not empty thread && !message.error}'>
			<ul>
				<li>title: <c:out value="${thread.title}"/></li>
				<li>text: <c:out value="${thread.text}"/></li>
				<li>date: <c:out value="${thread.date}"/></li>
				<li>username: <c:out value="${thread.user}"/></li>
			</ul>
		</c:if>
	</body>
</html>