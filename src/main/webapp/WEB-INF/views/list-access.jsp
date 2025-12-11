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

<c:if test="${not empty msg}">
    <script>
        alert("${msg}");
    </script>
</c:if>

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
  
    <!-- 🔍 Search Form -->
    <form action="${pageContext.request.contextPath}/access/search" method="get" class="mb-3 d-flex">
        <input type="text" name="keyword" class="form-control me-2"
               placeholder="Search Access by Name" value="${keyword}">
        <button type="submit" class="btn btn-primary">Search</button>
        <a href="${pageContext.request.contextPath}/access/list" class="btn btn-secondary ms-2">Reset</a>
    </form>

    <c:choose>
        <c:when test="${not empty accessList}">
            <table class="table table-striped table-bordered">
                <thead class="table-dark">
                    <tr>
                        <th>ID</th>
                        <th>Access Name</th>
                        <th>Actions</th>
                       <!--  <th>Privileges</th> -->
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="a" items="${accessList}">
                        <tr>
                            <td>${a.accessId}</td>
                            <td>${a.accessName}</td>
                            <td>
                                    <a href="${pageContext.request.contextPath}/access/updateForm?accessId=${a.accessId}">Edit</a> | 
                                    <a href="${pageContext.request.contextPath}/access/softDelete?accessId=${a.accessId}"
   onclick="return confirm('Do you really want to delete this?')">Delete</a>

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
