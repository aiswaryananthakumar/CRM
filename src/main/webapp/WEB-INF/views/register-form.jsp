<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register User</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assets/stylesheet/styles.css" rel="stylesheet">
</head>
<body>
<div id="addForm">
    <div class="bg-secondary bg-gradient">
        <div class="text-center">
            <h1 class="pt-3 pb-3">CRM - Register User</h1>
        </div>
    </div>

    <form:form class="form-container" action="${pageContext.request.contextPath}/register/save" method="POST" modelAttribute="register">
        <div class="input-group mb-3 me-5">
            <span class="input-group-text">User Name</span>
            <form:input path="userName" cssClass="form-control"/>
            <form:errors path="userName" cssClass="error ps-5"/>
        </div>

        <div class="input-group mb-3 me-5">
            <span class="input-group-text">Email</span>
            <form:input path="email" cssClass="form-control"/>
            <form:errors path="email" cssClass="error ps-5"/>
        </div>

        <div class="input-group mb-3 me-5">
            <span class="input-group-text">Password</span>
            <form:password path="password" cssClass="form-control"/>
            <form:errors path="password" cssClass="error ps-5"/>
        </div>

        <input type="submit" class="btn btn-primary" value="Register">
        <a class="ms-5" href="${pageContext.request.contextPath}/register/list">Back to List</a>
    </form:form>
</div>
</body>
</html>
