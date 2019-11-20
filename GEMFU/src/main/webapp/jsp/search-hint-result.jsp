<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
	<head>
		<title>Search Hint</title>
	</head>

	<body>
		<h1>Search Hint</h1>
		<hr/>
		
		<!-- display the message -->
		<c:import url="/jsp/include/show-message.jsp"/>

		<!-- display the list of found employees, if any -->
		<c:if test='${not empty hintList && !message.error}'>
			<table>
				<thead>
					<tr>
						<th>Id</th><th>Problem</th><th>Description</th>
					</tr>
				</thead>

				<tbody>
					<c:forEach var="hi" items="${hintList}">
						<tr>
							<td><c:out value="${hi.id}"/></td>
							<td><c:out value="${hi.problem}"/></td>
							<td><c:out value="${hi.description}"/></td>
							
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:if>
	</body>
</html>
