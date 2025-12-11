<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Privilege List</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assets/stylesheet/styles.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assets/images/favicon.png" rel="icon">
</head>
<body>

<c:if test="${not empty successMessage}">
    <div class="alert alert-success alert-dismissible fade show" role="alert">
        ${successMessage}
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>
</c:if>

    <div class="bg-secondary bg-gradient">
        <div class="text-center">
            <h1 class="pt-3">CRM - Privilege List</h1>
        </div>
        <div class="container p-3">
            <a class="btn btn-lg btn-dark" href="${pageContext.request.contextPath}/home">Home</a>
            <a class="btn btn-lg btn-dark" href="${pageContext.request.contextPath}/privileges/list">List</a>
            <a class="btn btn-lg btn-dark" href="${pageContext.request.contextPath}/privileges/showForm">Add New Privilege</a>
            <a class="btn btn-lg btn-dark" href="${pageContext.request.contextPath}/logout">Logout</a>
        </div>
    </div>

    <div class="container mt-5">
        <h2 class="mb-4">Privilege List</h2>
        
        <!-- 🔍 Search Form -->
<form action="${pageContext.request.contextPath}/privileges/search" method="get" class="mb-3 d-flex">
    <input type="text" name="keyword" class="form-control me-2" 
           placeholder="Search Privilege by Name" 
           value="${keyword}">
    <button type="submit" class="btn btn-primary">Search</button>
    <a href="${pageContext.request.contextPath}/privileges/list" class="btn btn-secondary ms-2">Reset</a>
</form>
        

        <c:choose>
            <c:when test="${not empty privileges}">
                <table class="table table-striped table-bordered">
                    <thead class="table-dark">
                        <tr>
                            <th>ID</th>
                            <th>Privilege Name</th>
                            <th>Role</th>
                            <th>Access</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="priv" items="${privileges}">
                            <tr>
                                <td>${priv.privillegeId}</td>                           
                                <td>${priv.privillegeName}</td>
                                <td>${priv.role.roleName}</td>
                                <%-- <td>
                                    <c:choose>
                                        <c:when test="${not empty priv.role}">
                                            ${priv.role.roleName}
                                        </c:when>
                                        <c:otherwise>
                                            <i>No Role Assigned</i>
                                        </c:otherwise>
                                    </c:choose>
                                </td> --%>
                                <td>
                                    <c:choose>
                                        <c:when test="${not empty priv.access}">
                                            <c:forEach var="acc" items="${priv.access}">
                                                ${acc.accessName}<br/>
                                            </c:forEach>
                                        </c:when>

                                    </c:choose>
                                </td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/privileges/updateForm?privilegeId=${priv.privillegeId}" class="btn btn-warning btn-sm">Edit</a>
                                    <a href="${pageContext.request.contextPath}/privileges/delete?privilegeId=${priv.privillegeId}" class="btn btn-danger btn-sm" onclick="return confirm('Are you sure?')">Delete</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:when>
            <c:otherwise>
                <p class="text-danger">No privileges found!</p>
            </c:otherwise>
        </c:choose>
    </div>
</body>
</html>
