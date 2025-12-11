<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.techietact.crm.utils.SortUtils"%>
<!DOCTYPE html>
<html>
<head>
    <title>Employee List</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- SweetAlert2 -->
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</head>
<body>

<!-- ✅ Show Success/Failure Messages -->
<c:if test="${param.success == 'created'}">
<script>
    Swal.fire({
        icon: 'success',
        title: 'Record Added',
        text: 'Employee created successfully!'
    });
</script>
</c:if>

<c:if test="${param.success == 'updated'}">
<script>
    Swal.fire({
        icon: 'success',
        title: 'Record Updated',
        text: 'Employee updated successfully!'
    });
</script>
</c:if>

<c:if test="${param.success == 'deleted'}">
<script>
    Swal.fire({
        icon: 'success',
        title: 'Record Deleted',
        text: 'Employee deleted successfully!'
    });
</script>
</c:if>

<div class="bg-secondary bg-gradient">
    <div class="text-center">
        <h1 class="pt-3">CRM - Employee List</h1>
    </div>
    <div class="container p-3">
        <a class="btn btn-lg btn-dark" href="${pageContext.request.contextPath}/home">Home</a>
        <a class="btn btn-lg btn-dark" href="${pageContext.request.contextPath}/employee/list">List</a>
        <a class="btn btn-lg btn-dark" href="${pageContext.request.contextPath}/employee/add">Add Employee</a>
        <a class="btn btn-lg btn-dark" href="${pageContext.request.contextPath}/logout">Logout</a>
    </div>
</div>

<div class="container mt-5">
    <h2>Employees</h2>
    <a href="add" class="btn btn-success mb-3">Add Employee</a>
    <form action="search" method="GET" class="mb-3">
        <input type="text" name="search" placeholder="Search..." class="form-control"/>
    </form>
    <c:choose>
        <c:when test="${employees.size() > 0}">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><a href="${pageContext.request.contextPath}/employee/list?sortBy=${SortUtils.SortByFirstName.value}">First Name</a></th>
                        <th><a href="${pageContext.request.contextPath}/employee/list?sortBy=${SortUtils.SortByLastName.value}">Last Name</a></th>
                        <th><a href="${pageContext.request.contextPath}/employee/list?sortBy=${SortUtils.SortByEmail.value}">Email</a></th>
                        <th>Department</th>
                        <th>Designation</th>
                        <th>Salary</th>
                        <th>Role</th>
                        <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="employee" items="${employees}">
    <tr data-id="${employee.id}">
        <td>${employee.firstName}</td>
        <td>${employee.lastName}</td>
        <td>${employee.email}</td>
        <td>${employee.department}</td>
        <td>${employee.designation}</td>
        <td>${employee.salary}</td>
        <td>${employee.role.roleName}</td>
        <td>
            <a href="${pageContext.request.contextPath}/employee/update?id=${employee.id}" class="text-primary">Update</a> |
            <a href="#" onclick="confirmUIDelete(this)" class="text-danger">Delete</a>
        </td>
    </tr>
</c:forEach>

                </tbody>
            </table>
        </c:when>
        <c:otherwise>
            <p>No employees found</p>
        </c:otherwise>
    </c:choose>
</div>

<!-- ✅ Delete Confirmation Script -->
<script>
function loadDeletedIds() {
    return JSON.parse(localStorage.getItem("deletedEmployees") || "[]");
}

function saveDeletedIds(ids) {
    localStorage.setItem("deletedEmployees", JSON.stringify(ids));
}

function applyUIDeletions() {
    let deletedIds = loadDeletedIds();
    document.querySelectorAll("tr[data-id]").forEach(row => {
        let id = row.getAttribute("data-id");
        if (deletedIds.includes(id)) {
            row.style.display = "none";   // hide if deleted before
        } else {
            row.style.display = "";       // show otherwise
        }
    });
}

function confirmUIDelete(button) {
    Swal.fire({
        title: 'Remove from UI',
        text: "Do you want to hide this employee?",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#d33',
        cancelButtonColor: '#3085d6',
        confirmButtonText: 'Yes, hide',
        cancelButtonText: 'Cancel'
    }).then((result) => {
        if (result.isConfirmed) {
            let row = button.closest("tr");
            let id = row.getAttribute("data-id");

            // save hidden id
            let deletedIds = loadDeletedIds();
            if (!deletedIds.includes(id)) {
                deletedIds.push(id);
                saveDeletedIds(deletedIds);
            }

            row.style.display = "none"; // hide immediately
            Swal.fire('Hidden!', 'Employee is hidden from the table.', 'success');
        }
    });
}

// apply deletions every time page loads
window.onload = applyUIDeletions;
</script>

</body>
</html>
