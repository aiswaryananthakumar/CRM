<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.techietact.crm.utils.SortUtils"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Campaign List</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet" 
      integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x" crossorigin="anonymous">
<link href="${pageContext.request.contextPath}/assets/stylesheet/styles.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/assets/images/favicon.png" rel="icon">
</head>
<body>

<c:if test="${not empty msg}">
    <script>
        alert("${msg}");
    </script>
</c:if>

    <div class="bg-secondary bg-gradient">
        <div class="text-center">
            <h1 class="pt-3">CRM - Campaigns List</h1>
        </div>
        <div class="container p-3">
            <a class="btn btn-lg btn-dark" href="home">Home</a>
            <a class="btn btn-lg btn-dark" href="campaign-list">List</a>
            <a class="btn btn-lg btn-dark" href="campaign-add">Add Campaign</a> <!-- Controller handles access -->
            <a class="btn btn-lg btn-dark" href="logout">Logout</a>
        </div>
    </div>
    <div class="container mt-5">
        <c:choose>
            <c:when test="${campaigns.size() > 0}">
                <form class="form-container d-flex mb-5" action="campaign-search" method="GET">
                    <input type="text" class="form-control" name="search" placeholder="Search" value="${search}" required>
                    <input type="submit" class="btn btn-primary ms-5" value="Search">
                </form>
                <table class="table table-striped">
                    <thead>
                        <tr>
                            <th scope="col"><a href="list?sortBy=${SortUtils.SortByName.value}">Campaign Name</a></th>
                            <th scope="col"><a href="list?sortBy=${SortUtils.SortByStartDate.value}">Start Date</a></th>
                            <th scope="col"><a href="list?sortBy=${SortUtils.SortByEndDate.value}">End Date</a></th>
                            <th scope="col"><a href="list?sortBy=${SortUtils.SortByStatus.value}">Status</a></th>
                            <th scope="col">Actions</th> <!-- Access controlled by controller/service -->
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="campaign" items="${campaigns}">
                            <tr>
                                <td>${campaign.name}</td>
                                <td>${campaign.startDate}</td>
                                <td>${campaign.endDate}</td>
                                <td>${campaign.status}</td>
                                <td>
                                    <a href="campaign-update?id=${campaign.id}">Update</a> |
                                    <a class="delete" href="campaign-softDelete?id=${campaign.id}" 
   onclick="return confirm('Do you really want to delete this?')">Delete</a>

                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:when>
            <c:otherwise>
                <h3 class="text-center">No record exists!</h3>
            </c:otherwise>
        </c:choose>
    </div>
</body>
</html>
 