<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>CRM - Login</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assets/stylesheet/styles.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assets/images/favicon.png" rel="icon">
</head>
<body>
    <div id="addForm">
        <div class="bg-secondary bg-gradient">
            <div class="text-center">
                <h1 class="pt-3 pb-3">CRM - Login</h1>
            </div>
        </div>

        <form:form class="form-container" action="${pageContext.request.contextPath}/login" method="POST" modelAttribute="login">
            <div class="input-group mb-3 me-5">
                <span class="input-group-text">User Name</span>
                <form:input type="text" path="userName" cssClass="form-control"/>
                <form:errors cssClass="error ps-5" path="userName"/>
            </div>

            <div class="input-group mb-3 me-5">
                <span class="input-group-text">Password</span>
                <form:password path="password" cssClass="form-control"/>
                <form:errors cssClass="error ps-5" path="password"/>
            </div>

            <div class="text-center">
                <input type="submit" class="btn btn-primary" value="Login">
                <a href="${pageContext.request.contextPath}/register/add" class="btn btn-success ms-3">Register</a>
            </div>

            <div class="text-center mt-3">
                <a href="${pageContext.request.contextPath}/list" class="text-secondary">Back to List</a>
            </div>
        </form:form>
    </div>
</body>
</html>
