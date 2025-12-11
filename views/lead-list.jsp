<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.techietact.crm.utils.SortUtils"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>


<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Lead List</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x"
	crossorigin="anonymous">
<link
	href="${pageContext.request.contextPath}/assets/stylesheet/styles.css"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath}/assets/images/favicon.png"
	rel="icon">
</head>
<body>
	<div class="bg-secondary bg-gradient">
		<div class="text-center">
			<h1 class="pt-3">CRM - Leads List</h1>
		</div>
		<div class="container p-3">
		<a class="btn btn-lg btn-dark" href="home">Home</a>
			<a class="btn btn-lg btn-dark" href="lead-list">List</a>
			<sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_LEAD_CREATE')">
				<a class="btn btn-lg btn-dark" href="lead-add">Add Lead</a>
			</sec:authorize>
			<a class="btn btn-lg btn-dark" href="logout">Logout</a>

		</div>
	</div>
	<div class="container mt-5">
		<c:choose>
			<c:when test="${leads.size() > 0}">
				<form class="form-container d-flex mb-5" action="lead-search"
					method="GET">
					<input type="text" class="form-control" name="search"
						placeholder="Search" value="${search}" required> <input
						type="submit" class="btn btn-primary ms-5" value="Search">
				</form>
				<table class="table table-striped">
					<thead>
						<tr>
							<th scope="col"><a
								href="list?sortBy=${SortUtils.SortByFirstName.value}">First
									Name</a></th>
							<th scope="col"><a
								href="list?sortBy=${SortUtils.SortByLastName.value}">Last
									Name</a></th>
							<th scope="col"><a
								href="list?sortBy=${SortUtils.SortByEmail.value}">Email</a></th>
							<sec:authorize access="hasAnyRole('ROLE_ADMIN')">
							<th scope="col">Update / Delete</th>
							</sec:authorize>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="lead" items="${leads}">
							<tr>
								<td>${lead.firstName}</td>
								<td>${lead.lastName}</td>
								<td>${lead.email}</td>
								<sec:authorize access="hasAnyRole('ROLE_ADMIN')">
									<td><a href="lead-update?id=${lead.id}">Update</a> | <a
										class="delete" href="lead-delete?id=${lead.id}"
										onclick="return confirm('Do you really want to delete this?')">Delete</a></td>
								</sec:authorize>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:when>
			<c:otherwise>
				<h3 class="text-center">No record exists!</h3>
			</c:otherwise>
		</c:choose>
	</div>
</body>
</html>
