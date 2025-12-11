<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Access Form</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/assets/stylesheet/styles.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/assets/images/favicon.png" rel="icon">
</head>
<body>
<div id="accessForm">
    <div class="bg-secondary bg-gradient">
        <div class="text-center">
            <h1 class="pt-3 pb-3">CRM - Save Access</h1>
        </div>
    </div>

    <form:form class="form-container" modelAttribute="access" method="post" action="${pageContext.request.contextPath}/access/save">
        <form:hidden path="accessId"/>

        <!-- Access Name -->
        <div class="input-group mb-3 me-5">
            <span class="input-group-text">Access Name</span>
            <form:input path="accessName" cssClass="form-control" placeholder="Enter Access Name"/>
            <form:errors path="accessName" cssClass="error ps-5"/>
        </div>

        <!-- Buttons -->
        <button type="submit" class="btn btn-primary">Save</button>
        <a href="${pageContext.request.contextPath}/access/list" class="btn btn-secondary ms-2">Back to List</a>
    </form:form>
</div>
</body>
</html>

 