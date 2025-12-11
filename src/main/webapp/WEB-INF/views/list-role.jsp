<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Role List</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/assets/stylesheet/styles.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/assets/images/favicon.png" rel="icon">
</head>
<body>

<c:if test="${not empty msg}">
    <script>
        alert("${msg}");
    </script>
</c:if>

    <div class="bg-secondary bg-gradient">
        <div class="text-center">
            <h1 class="pt-3">CRM - Roles List</h1>
        </div>
        <div class="container p-3">
               <a class="btn btn-lg btn-dark" href="${pageContext.request.contextPath}/home">Home</a>
            <a class="btn btn-lg btn-dark" href="list">List</a>
            <a class="btn btn-lg btn-dark" href="showForm">Add Role</a>
            <a class="btn btn-lg btn-dark" href="${pageContext.request.contextPath}/logout">Logout</a>
        </div>
    </div>

    <div class="container mt-5">
    
     <!-- 🔍 Search Form -->
    <form action="${pageContext.request.contextPath}/roles/search" method="get" class="mb-3 d-flex">
        <input type="text" name="keyword" class="form-control me-2" placeholder="Search Role by Name">
        <button type="submit" class="btn btn-primary">Search</button>
        <a href="${pageContext.request.contextPath}/roles/list" class="btn btn-secondary ms-2">Reset</a>
    </form>

        <c:choose>
            <c:when test="${roles.size() > 0}">
                <table class="table table-striped">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Role Name</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="role" items="${roles}">
                            <tr>
                                <td>${role.roleId}</td>
                                <td>${role.roleName}</td>
                                <td>
                                    <a href="updateForm?roleId=${role.roleId}">Update</a> |
                                    <a class="delete" href="${pageContext.request.contextPath}/roles/delete/${role.roleId}" onclick="return confirm('Are you sure you want to delete this role?');"> Delete </a>

                                </td>
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
