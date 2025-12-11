<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <title>${employee.id == 0 ? 'Add Employee' : 'Update Employee'}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
    <h2>${employee.id == 0 ? 'Add Employee' : 'Update Employee'}</h2>

    <!-- ✅ Dynamically switch between save and update -->
    <form:form action="${employee.id == 0 ? 'save' : 'update'}" method="POST" modelAttribute="employee">
        <form:hidden path="id"/>

        <div class="mb-3">
            <label>First Name</label>
            <form:input path="firstName" cssClass="form-control"/>
            <form:errors path="firstName" cssClass="text-danger"/>
        </div>

        <div class="mb-3">
            <label>Last Name</label>
            <form:input path="lastName" cssClass="form-control"/>
            <form:errors path="lastName" cssClass="text-danger"/>
        </div>

        <div class="mb-3">
            <label>Email</label>
            <form:input path="email" cssClass="form-control"/>
            <form:errors path="email" cssClass="text-danger"/>
        </div>

        <div class="mb-3">
            <label>Mobile</label>
            <form:input path="mobile" cssClass="form-control"/>
            <form:errors path="mobile" cssClass="text-danger"/>
        </div>

        <div class="mb-3">
            <label>Department</label>
            <form:input path="department" cssClass="form-control"/>
        </div>

        <div class="mb-3">
            <label>Designation</label>
            <form:input path="designation" cssClass="form-control"/>
        </div>

        <div class="mb-3">
            <label>Salary</label>
            <form:input path="salary" cssClass="form-control"/>
        </div>

        <div class="mb-3">
            <label>Address</label>
            <form:input path="address" cssClass="form-control"/>
        </div>

        <div class="mb-3">
            <label>DOB</label>
            <form:input path="dob" cssClass="form-control"/>
        </div>

        <div class="mb-3">
            <label>Role</label>
            <form:select path="role.roleId"
                         items="${rolesList}"
                         itemValue="roleId"
                         itemLabel="roleName"
                         cssClass="form-select"/>
        </div>

        <input type="submit" class="btn btn-primary" value="${employee.id == 0 ? 'Save' : 'Update'}"/>
        <a href="list" class="btn btn-secondary">Back</a>
    </form:form>
</div>
</body>
</html>
