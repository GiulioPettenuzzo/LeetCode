<!--
 Copyright 2018 University of Padua, Italy
 
 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at
 
     http://www.apache.org/licenses/LICENSE-2.0
 
 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 
 Author: Nicola Ferro (ferro@dei.unipd.it)
 Version: 1.0
 Since: 1.0
-->

<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
	<head>
		<title>Search User</title>
	</head>

	<body>
		<h1>Search User</h1>
		<hr/>
		
		<!-- display the message -->
		<c:import url="/jsp/include/show-message.jsp"/>

		<!-- display the list of found employees, if any -->
		<c:if test='${not empty users}'>
			<table>
				<thead>
					<tr>
						<th>Username</th><th>Name</th><th>Surname</th><th>Age</th><th>Gender</th><th>Email</th><th>ImageProfile</th><th>Password</th>
					</tr>
				</thead>

				<tbody>
					<c:forEach var="us" items="${users}">
						<tr>
							<td><c:out value="${us.username}"/></td>
							<td><c:out value="${us.name}"/></td>
							<td><c:out value="${us.surname}"/></td>
							<td><c:out value="${us.date}"/></td>
							<td><c:out value="${us.gender}"/></td>
							<td><c:out value="${us.email}"/></td>
							<td><c:out value="${us.profileImage}"/></td>
							<td><c:out value="${us.password}"/></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:if>
	</body>
</html>
