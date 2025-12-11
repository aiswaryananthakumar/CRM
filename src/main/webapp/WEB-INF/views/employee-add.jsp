<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Add Employee</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .error { color: red; font-size: 14px; }
    </style>
</head>
<body>
<div class="container mt-5">
    <h2>Add Employee</h2>
    <form:form action="save" method="POST" modelAttribute="employee">
        <form:hidden path="id"/>

        <div class="row">
            <div class="col-md-6 mb-3">
                <label>First Name</label>
                <form:input path="firstName" cssClass="form-control"/>
                <form:errors path="firstName" cssClass="error"/>
            </div>

            <div class="col-md-6 mb-3">
                <label>Last Name</label>
                <form:input path="lastName" cssClass="form-control"/>
                <form:errors path="lastName" cssClass="error"/>
            </div>
        </div>

        <div class="row">
            <div class="col-md-6 mb-3">
                <label>Email</label>
                <form:input path="email" cssClass="form-control"/>
                <form:errors path="email" cssClass="error"/>
            </div>

            <div class="col-md-6 mb-3">
                <label>Mobile</label>
                <form:input path="mobile" cssClass="form-control"/>
                <form:errors path="mobile" cssClass="error"/>
            </div>
        </div>

        <div class="row">
            <div class="col-md-6 mb-3">
                <label>Department</label>
                <form:input path="department" cssClass="form-control"/>
            </div>

            <div class="col-md-6 mb-3">
                <label>Designation</label>
                <form:input path="designation" cssClass="form-control"/>
            </div>
        </div>

        <div class="row">
            <div class="col-md-6 mb-3">
                <label>Salary</label>
                <form:input path="salary" cssClass="form-control"/>
            </div>

            <div class="col-md-6 mb-3">
                <label>DOB</label>
                <form:input path="dob" cssClass="form-control" placeholder="yyyy-mm-dd"/>
            </div>
        </div>

        <div class="mb-3">
            <label>Address</label>
            <form:input path="address" cssClass="form-control"/>
        </div>

        <div class="mb-3">
            <label>Role</label>
            <form:select path="role.roleId" items="${rolesList}" itemValue="roleId" itemLabel="roleName" cssClass="form-select"/>
        </div>

        <button type="submit" class="btn btn-primary">Save</button>
        <a href="list" class="btn btn-secondary">Cancel</a>
    </form:form>
</div>
</body>
</html>
