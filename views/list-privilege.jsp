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
<title>Privilege List</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/assets/stylesheet/styles.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/assets/images/favicon.png" rel="icon">
</head>
<body>
    <div class="bg-secondary bg-gradient">
        <div class="text-center">
            <h1 class="pt-3">CRM - Privilege List</h1>
        </div>
        <div class="container p-3">
          <a class="btn btn-lg btn-dark" href="${pageContext.request.contextPath}/home">Home</a>
            <a class="btn btn-lg btn-dark" href="list">List</a>
            <%-- <sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_PRIVILEGE_CREATE')">
                <a class="btn btn-lg btn-dark" href="showForm">Add New Privilege</a>
            </sec:authorize> --%>
             <a class="btn btn-lg btn-dark" href="showForm">Add New Privilege</a>
                    <a class="btn btn-lg btn-dark" href="${pageContext.request.contextPath}/logout">Logout</a>
        </div>
    </div>

    <div class="container mt-5">
        <!-- Search -->
        <%-- <form class="form-container d-flex mb-5" action="${pageContext.request.contextPath}/privileges/list" method="GET">
            <input type="text" class="form-control" name="search" placeholder="Search" value="${search}">
            <input type="submit" class="btn btn-primary ms-3" value="Search">
        </form>
 --%>
        <c:choose>
            <c:when test="${privileges.size() > 0}">
                <table class="table table-striped">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Privilege Name</th>
                            <th>Role</th>
                            <th>Accesses</th>
                             <th>Actions</th>
                            <%-- <sec:authorize access="hasAnyRole('ROLE_ADMIN')">
                                <th>Actions</th>
                            </sec:authorize> --%>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="priv" items="${privileges}">
                            <tr>
                                <td>${priv.privilegeId}</td>
                                <td>${priv.privilegeName}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${priv.role != null}">
                                            ${priv.role.roleName}
                                        </c:when>
                                        <c:otherwise>
                                            <i>No Role Assigned</i>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <c:if test="${not empty priv.accesses}">
                                        <c:forEach var="acc" items="${priv.accesses}">
                                            ${acc.accessName}<br/>
                                        </c:forEach>
                                    </c:if>
                                    <c:if test="${empty priv.accesses}">
                                        <i>No Access Assigned</i>
                                    </c:if>
                                </td>
                                <td>
                                    <a href="showFormForUpdate?privilegeId=${priv.privilegeId}">Edit</a> | 
                                    <a class="delete" href="delete?privilegeId=${priv.privilegeId}" 
                                       onclick="return confirm('Are you sure you want to delete this privilege?');">Delete</a>
                                </td>
                                <%-- <sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_PRIVILEGE_UPDATE','ROLE_PRIVILEGE_DELETE')">
                                <td>
                                    <a href="showFormForUpdate?privilegeId=${priv.privilegeId}">Edit</a> | 
                                    <a class="delete" href="delete?privilegeId=${priv.privilegeId}" 
                                       onclick="return confirm('Are you sure you want to delete this privilege?');">Delete</a>
                                </td>
                                </sec:authorize> --%>
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
s