<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>${privilege.privilegeId == 0 ? 'Add New Privilege' : 'Update Privilege'}</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/assets/stylesheet/styles.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/assets/images/favicon.png" rel="icon">
</head>
<body>
    <div id="addForm">
        <div class="bg-secondary bg-gradient">
            <div class="text-center">
                <h1 class="pt-3 pb-3">CRM - Privilege Management</h1>
            </div>
        </div>

        <div class="container mt-5">
            <form:form class="form-container" action="${pageContext.request.contextPath}/privileges/save" method="POST" modelAttribute="privilege">
                <form:hidden path="privilegeId"/>
                
                <div class="input-group mb-3 me-5">
                    <span class="input-group-text">Privilege Name</span>
                    <form:input type="text" cssClass="form-control" path="privilegeName" placeholder="Enter Privilege Name"/>
                    <form:errors cssClass="error ps-5" path="privilegeName"/>
                </div>

                <div class="input-group mb-3 me-5">
                    <span class="input-group-text">Role</span>
                    <form:select cssClass="form-select" path="role.roleId" items="${roles}" itemValue="roleId" itemLabel="roleName"/>
                </div>

                <div class="input-group mb-3 me-5">
                    <span class="input-group-text">Accesses</span>
                    <form:select cssClass="form-select" multiple="true" path="accessIds" items="${allAccesses}" itemValue="accessId" itemLabel="accessName"/>
                </div>

                <input type="submit" class="btn btn-primary" value="Save">
                <a class="ms-3 btn btn-secondary" href="${pageContext.request.contextPath}/privileges/list">Back to list</a>
            </form:form>
        </div>
    </div>
</body>
</html>
