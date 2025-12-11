<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>
        <c:choose>
            <c:when test="${privilege.privillegeId == 0}">Add New Privilege</c:when>
            <c:otherwise>Update Privilege</c:otherwise>
        </c:choose>
    </title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
    <h2>Privilege Form</h2>

    <form:form action="${pageContext.request.contextPath}/privileges/save" modelAttribute="privilege" method="POST">
        <form:hidden path="privillegeId"/>

        <div class="mb-3">
            <form:label path="privillegeName">Privilege Name</form:label>
            <form:input path="privillegeName" cssClass="form-control"/>
            <form:errors path="privillegeName" cssClass="text-danger"/>
        </div>

        <div class="mb-3">
            <form:label path="role.roleId">Role</form:label>
            <form:select path="role.roleId" cssClass="form-select" items="${roles}" itemValue="roleId" itemLabel="roleName"/>
        </div>

        <div class="mb-3">
            <form:label path="accessIds">Access</form:label>
            <form:select path="accessIds" cssClass="form-select" items="${allAccess}" itemValue="accessId" itemLabel="accessName" multiple="true"/>          
        </div>

        <input type="submit" value="Save" class="btn btn-primary"/>
        <a href="${pageContext.request.contextPath}/privileges/list" class="btn btn-secondary">Back to List</a>
    </form:form>
</div>
</body>
</html>
