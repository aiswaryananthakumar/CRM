<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Role Form</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/assets/stylesheet/styles.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/assets/images/favicon.png" rel="icon">
</head>
<body>
    <div id="addForm">
        <div class="bg-secondary bg-gradient">
            <div class="text-center">
                <h1 class="pt-3 pb-3">CRM - Role Management</h1>
            </div>
        </div>

        <div class="container mt-5">
            <form:form class="form-container" action="${pageContext.request.contextPath}/roles/save" method="post" modelAttribute="role">
                <form:hidden path="roleId"/>
                
                <div class="input-group mb-3 me-5">
                    <span class="input-group-text" id="basic-addon1">Role Name</span>
                    <form:input type="text" cssClass="form-control" name="roleName" path="roleName" placeholder="Enter Role Name"/>
                    <form:errors cssClass="error ps-5" path="roleName"/>
                </div>

                <input type="submit" class="btn btn-primary" value="Save">
                <a class="ms-3 btn btn-secondary" href="${pageContext.request.contextPath}/roles/list">Back to list</a>
            </form:form>
        </div>
    </div>
</body>
</html>
