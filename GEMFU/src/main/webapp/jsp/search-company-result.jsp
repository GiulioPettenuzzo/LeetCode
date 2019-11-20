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
		<title>Search Company</title>
	</head>

	<body>
		<h1>Search Company</h1>
		<hr/>
		
		<!-- display the message -->
		<c:import url="/jsp/include/show-message.jsp"/>

		<!-- display the list of found companies, if any -->
		<c:if test='${not empty companyList}'>
			<table>
				<thead>
					<tr>
						<th>Name</th>
					</tr>
				</thead>

				<tbody>
					<c:forEach var="company" items="${companyList}">
						<tr>
							<td><c:out value="${company.name}"/></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:if>
	</body>
</html>
