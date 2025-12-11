<%--
  Created by CryptoSingh1337 on 5/31/2021
--%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Home</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x" crossorigin="anonymous">
    <link href="${pageContext.request.contextPath}/assets/stylesheet/styles.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assets/images/favicon.png" rel="icon">
</head>
<body>
    <div id="main">
        <img id="favicon" src="${pageContext.request.contextPath}/assets/images/favicon.png" alt="favicon"/>
        <h1 class="index-heading">Welcome</h1>
        <h2>TO</h2>
        <h2>CRM - Customer Relationship Management</h2>
        <h2>Project</h2>
        <h3 class="stack">Created using <span class="badge bg-secondary">Spring MVC</span> and <span class="badge bg-secondary">Hibernate</span></h3>

   <!-- ======================= Role / Privilege / Access based Navigation ======================= -->

    <div class="menu mt-4">

        <!-- Example: Customers & Leads (both Admin and Employee can view) -->
        <sec:authorize access="hasAnyAuthority('CUSTOMER_VIEW','LEAD_VIEW')">
            <a href="list" class="btn btn-primary btn-lg mt-3">Manage Customers</a>
            <a href="lead-list" class="btn btn-primary btn-lg mt-3">Manage Leads</a>
        </sec:authorize>

        <!-- Example: Only Admin has Campaign Access -->
        <sec:authorize access="hasAuthority('CAMPAIGN_MANAGE')">
            <a href="campaign-list" class="btn btn-primary btn-lg mt-3">Manage Campaign</a>
        </sec:authorize>
        
        <!-- Example: Employee Management (Admin only) -->
        <sec:authorize access="hasAuthority('EMPLOYEE_MANAGE')">
            <a href="${pageContext.request.contextPath}/employee/list" class="btn btn-primary btn-lg mt-3">Manage Employees</a>
        </sec:authorize>

        <!-- Example: Role / Privilege Management (Admin only) -->
        <sec:authorize access="hasAuthority('ROLE_MANAGE')">
            <a href="${pageContext.request.contextPath}/roles/list" class="btn btn-primary btn-lg mt-3">Manage Roles</a>
        </sec:authorize>

        <sec:authorize access="hasAuthority('ACCESS_MANAGE')">
            <a href="${pageContext.request.contextPath}/access/list" class="btn btn-primary btn-lg mt-3">Manage Access</a>
        </sec:authorize>

        <sec:authorize access="hasAuthority('PRIVILEGE_MANAGE')">
            <a href="${pageContext.request.contextPath}/privileges/list" class="btn btn-primary btn-lg mt-3">Manage Privileges</a>
        </sec:authorize>
        
        <div class="text-center mt-5">
            <a href="${pageContext.request.contextPath}/logout" class="btn btn-primary btn-lg mt-3">Logout</a>
        </div>

    </div>
</div>
</body>
</html>
 