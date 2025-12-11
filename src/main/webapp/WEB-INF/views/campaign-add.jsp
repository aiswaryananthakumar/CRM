<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%-- Created by CryptoSingh1337 on 6/1/2021 --%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add Campaign</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" 
          rel="stylesheet" 
          integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x" 
          crossorigin="anonymous">
    <link href="${pageContext.request.contextPath}/assets/stylesheet/styles.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assets/images/favicon.png" rel="icon">
</head>
<body>
    <div id="addForm">
        <div class="bg-secondary bg-gradient">
            <div class="text-center">
                <h1 class="pt-3 pb-3">Add Campaign</h1>
            </div>
        </div>
        <form:form class="form-container" action="campaign-save" method="POST" modelAttribute="campaign">
            <div class="input-group mb-3">
                <span class="input-group-text" id="basic-addon1">Campaign Name</span>
                <form:input type="text" cssClass="form-control" name="name" path="name"/>
                <form:errors cssClass="error ps-5" path="name"/>
            </div>
            <div class="input-group mb-3">
                <span class="input-group-text" id="basic-addon2">Start Date</span>
                <form:input type="date" cssClass="form-control" name="startDate" path="startDate"/>
                <form:errors cssClass="error ps-5" path="startDate"/>
            </div>
            <div class="input-group mb-3">
                <span class="input-group-text" id="basic-addon3">End Date</span>
                <form:input type="date" cssClass="form-control" name="endDate" path="endDate"/>
                <form:errors cssClass="error ps-5" path="endDate"/>
            </div>
            <div class="input-group mb-3">
                <span class="input-group-text" id="basic-addon4">Status</span>
                <form:select cssClass="form-control" name="status" path="status">
                    <form:option value="Planned" label="Planned"/>
                    <form:option value="Active" label="Active"/>
                    <form:option value="Completed" label="Completed"/>
                </form:select>
                <form:errors cssClass="error ps-5" path="status"/>
            </div>
            <div class="input-group mb-3">
                <span class="input-group-text" id="basic-addon5">Description</span>
                <form:textarea cssClass="form-control" name="description" path="description" rows="4"/>
                <form:errors cssClass="error ps-5" path="description"/>
            </div>
            <input type="submit" class="btn btn-primary" value="Submit">
        </form:form>
    </div>
</body>
</html>
 