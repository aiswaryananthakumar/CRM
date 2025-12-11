
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Access List</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/assets/stylesheet/styles.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/assets/images/favicon.png" rel="icon">
</head>
<body>
<div class="bg-secondary bg-gradient">
    <div class="text-center">
        <h1 class="pt-3 pb-3">CRM - Access List</h1>
    </div>
    <div class="container p-3">
        <a class="btn btn-lg btn-dark" href="${pageContext.request.contextPath}/home">Home</a>
         <a class="btn btn-lg btn-dark" href="list">List</a>
        <%-- <sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_ACCESS_CREATE')">
            <a class="btn btn-lg btn-dark" href="${pageContext.request.contextPath}/access/showForm">Add New Access</a>
        </sec:authorize> --%>
         <a class="btn btn-lg btn-dark" href="${pageContext.request.contextPath}/access/showForm">Add New Access</a>
        <a class="btn btn-lg btn-dark" href="${pageContext.request.contextPath}/logout">Logout</a>
    </div>
</div>

<div class="container mt-5">
    <!-- Search Form -->
   <%--  <form class="form-container d-flex mb-5" action="${pageContext.request.contextPath}/access/list" method="GET">
        <input type="text" class="form-control" name="search" placeholder="Search Access" value="${search}">
        <input type="submit" class="btn btn-primary ms-3" value="Search">
    </form> --%>

    <c:choose>
        <c:when test="${not empty accessList}">
            <table class="table table-striped table-bordered">
                <thead class="table-dark">
                    <tr>
                        <th>ID</th>
                        <th>Access Name</th>
                        <th>Actions</th>
                       <!--  <th>Privileges</th> -->
                        <%-- <sec:authorize access="hasRole('ROLE_ADMIN')">
                            <th>Actions</th>
                        </sec:authorize> --%>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="a" items="${accessList}">
                        <tr>
                            <td>${a.accessId}</td>
                            <td>${a.accessName}</td>
                            <td>
                                    <a href="${pageContext.request.contextPath}/access/updateForm?accessId=${a.accessId}">Edit</a> | 
                                    <a href="${pageContext.request.contextPath}/access/delete?accessId=${a.accessId}" 
                                       onclick="return confirm('Do you really want to delete this?')">Delete</a>
                                </td>
                           <%--  <td>
                                <c:choose>
                                    <c:when test="${not empty a.privilege}">
                                        <c:forEach var="p" items="${a.privilege}">
                                            ${p.privilegeName}<br/>
                                        </c:forEach>
                                    </c:when>
                                    <c:otherwise>
                                        <i>No Privileges Assigned</i>
                                    </c:otherwise>
                                </c:choose>
                            </td> --%>
                           <%--  <sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_ACCESS_UPDATE','ROLE_ACCESS_DELETE')">
                                <td>
                                    <a href="${pageContext.request.contextPath}/access/updateForm?accessId=${a.accessId}">Edit</a> | 
                                    <a href="${pageContext.request.contextPath}/access/delete?accessId=${a.accessId}" 
                                       onclick="return confirm('Do you really want to delete this?')">Delete</a>
                                </td>
                            </sec:authorize>
                          --%>
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
